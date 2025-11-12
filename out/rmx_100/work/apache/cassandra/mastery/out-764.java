package org.apache.cassandra.cql3.statements;

import java.nio.ByteBuffer;
import java.util.*;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.github.jamm.MemoryMeter;
import org.apache.cassandra.auth.Permission;
import org.apache.cassandra.cql3.*;
import org.apache.cassandra.transport.messages.ResultMessage;
import org.apache.cassandra.config.CFMetaData;
import org.apache.cassandra.db.*;
import org.apache.cassandra.db.filter.*;
import org.apache.cassandra.db.marshal.*;
import org.apache.cassandra.dht.*;
import org.apache.cassandra.exceptions.*;
import org.apache.cassandra.service.ClientState;
import org.apache.cassandra.service.QueryState;
import org.apache.cassandra.service.StorageProxy;
import org.apache.cassandra.service.StorageService;
import org.apache.cassandra.service.pager.*;
import org.apache.cassandra.db.ConsistencyLevel;
import org.apache.cassandra.thrift.ThriftValidation;
import org.apache.cassandra.serializers.MarshalException;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.cassandra.utils.FBUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import org.apache.cassandra.db.composites.*;
import org.apache.cassandra.config.ColumnDefinition;

public class SelectStatement implements CQLStatement, MeasurableForPreparedCache {

    private static final Logger logger = LoggerFactory.getLogger(SelectStatement.class);

    private static final int DEFAULT_COUNT_PAGE_SIZE = 10000;

    private final int boundTerms;

    final public CFMetaData cfm;

    final public Parameters parameters;

    private final Selection selection;

    private final Term limit;

    private final Restriction[] keyRestrictions;

    private final Restriction[] columnRestrictions;

    private Restriction.Slice sliceRestriction;

    private boolean isReversed;

    private boolean onToken;

    private boolean isKeyRange;

    private boolean keyIsInRelation;

    private boolean usesSecondaryIndexing;

    private Map<ColumnIdentifier, Integer> orderingIndexes;

    private boolean selectsStaticColumns;

    private boolean selectsOnlyStaticColumns;

    private static final Parameters defaultParameters = new Parameters(Collections.<ColumnIdentifier, Boolean>emptyMap(), false, false, null, false);

    public SelectStatement(CFMetaData cfm, int boundTerms, Parameters parameters, Selection selection, Term limit) {
        this.cfm = cfm;
        this.boundTerms = boundTerms;
        this.selection = selection;
        this.keyRestrictions = new Restriction[cfm.partitionKeyColumns().size()];
        this.columnRestrictions = new Restriction[cfm.clusteringColumns().size()];
        this.parameters = parameters;
        this.limit = limit;
        initStaticColumnsInfo();
    }

    private void initStaticColumnsInfo() {
        if (!cfm.hasStaticColumns())
            return;
        if (selection.isWildcard()) {
            selectsStaticColumns = true;
            return;
        }
        selectsStaticColumns = !Iterables.isEmpty(Iterables.filter(selection.getColumns(), isStaticFilter));
        selectsOnlyStaticColumns = true;
        for (ColumnDefinition def : selection.getColumns()) {
            if (def.kind != ColumnDefinition.Kind.PARTITION_KEY && def.kind != ColumnDefinition.Kind.STATIC) {
                selectsOnlyStaticColumns = false;
                break;
            }
        }
    }

    static SelectStatement forSelection(CFMetaData cfm, Selection selection) {
        return new SelectStatement(cfm, 0, defaultParameters, selection, null);
    }

    public ResultSet.Metadata getResultMetadata() {
        return parameters.isCount ? ResultSet.makeCountMetadata(keyspace(), columnFamily(), parameters.countAlias) : selection.getResultMetadata();
    }

    public long measureForPreparedCache(MemoryMeter meter) {
        return meter.measure(this) + meter.measureDeep(parameters) + meter.measureDeep(selection) + (limit == null ? 0 : meter.measureDeep(limit)) + meter.measureDeep(keyRestrictions) + meter.measureDeep(columnRestrictions) + meter.measureDeep(metadataRestrictions) + meter.measureDeep(restrictedColumns) + (sliceRestriction == null ? 0 : meter.measureDeep(sliceRestriction)) + (orderingIndexes == null ? 0 : meter.measureDeep(orderingIndexes));
    }

    public int getBoundTerms() {
        return boundTerms;
    }

    public void checkAccess(ClientState state) throws InvalidRequestException, UnauthorizedException {
        state.hasColumnFamilyAccess(keyspace(), columnFamily(), Permission.SELECT);
    }

    public void validate(ClientState state) throws InvalidRequestException {
    }

    private ResultMessage.Rows execute(Pageable command, QueryOptions options, int limit, long now) throws RequestValidationException, RequestExecutionException {
        List<Row> rows;
        if (command == null) {
            rows = Collections.<Row>emptyList();
        } else {
            rows = command instanceof Pageable.ReadCommands ? StorageProxy.read(((Pageable.ReadCommands) command).commands, options.getConsistency()) : StorageProxy.getRangeSlice((RangeSliceCommand) command, options.getConsistency());
        }
        return processResults(rows, options, limit, now);
    }

    private ResultMessage.Rows pageCountQuery(QueryPager pager, QueryOptions options, int pageSize, long now, int limit) throws RequestValidationException, RequestExecutionException {
        int count = 0;
        while (!pager.isExhausted()) {
            int maxLimit = pager.maxRemaining();
            logger.debug("New maxLimit for paged count query is {}", maxLimit);
            ResultSet rset = process(pager.fetchPage(pageSize), options, maxLimit, now);
            count += rset.rows.size();
        }
        ResultSet result = ResultSet.makeCountResult(keyspace(), columnFamily(), Math.min(count, limit), parameters.countAlias);
        return new ResultMessage.Rows(result);
    }

    public ResultMessage.Rows processResults(List<Row> rows, QueryOptions options, int limit, long now) throws RequestValidationException {
        ResultSet rset = process(rows, options, limit, now);
        rset = parameters.isCount ? rset.makeCountResult(parameters.countAlias) : rset;
        return new ResultMessage.Rows(rset);
    }

    static List<Row> readLocally(String keyspaceName, List<ReadCommand> cmds) {
        Keyspace keyspace = Keyspace.open(keyspaceName);
        List<Row> rows = new ArrayList<Row>(cmds.size());
        for (ReadCommand cmd : cmds) rows.add(cmd.getRow(keyspace));
        return rows;
    }

    public ResultMessage.Rows executeInternal(QueryState state, QueryOptions options) throws RequestExecutionException, RequestValidationException {
        int limit = getLimit(options);
        long now = System.currentTimeMillis();
        Pageable command = getPageableCommand(options, limit, now);
        List<Row> rows = command == null ? Collections.<Row>emptyList() : (command instanceof Pageable.ReadCommands ? readLocally(keyspace(), ((Pageable.ReadCommands) command).commands) : ((RangeSliceCommand) command).executeLocally());
        return processResults(rows, options, limit, now);
    }

    public ResultSet process(List<Row> rows) throws InvalidRequestException {
        assert !parameters.isCount;
        QueryOptions options = QueryOptions.DEFAULT;
        return process(rows, options, getLimit(options), System.currentTimeMillis());
    }

    public String keyspace() {
        return cfm.ksName;
    }

    public String columnFamily() {
        return cfm.cfName;
    }

    private List<ReadCommand> getSliceCommands(QueryOptions options, int limit, long now) throws RequestValidationException {
        Collection<ByteBuffer> keys = getKeys(options);
        if (keys.isEmpty())
            return null;
        List<ReadCommand> commands = new ArrayList<>(keys.size());
        IDiskAtomFilter filter = makeFilter(options, limit);
        if (filter == null)
            return null;
        for (ByteBuffer key : keys) {
            QueryProcessor.validateKey(key);
            commands.add(ReadCommand.create(keyspace(), key, columnFamily(), now, filter.cloneShallow()));
        }
        return commands;
    }

    private RangeSliceCommand getRangeCommand(QueryOptions options, int limit, long now) throws RequestValidationException {
        IDiskAtomFilter filter = makeFilter(options, limit);
        if (filter == null)
            return null;
        List<IndexExpression> expressions = getIndexExpressions(options);
        AbstractBounds<RowPosition> keyBounds = getKeyBounds(options);
        return keyBounds == null ? null : new RangeSliceCommand(keyspace(), columnFamily(), now, filter, keyBounds, expressions, limit, !parameters.isDistinct, false);
    }

    private AbstractBounds<RowPosition> getKeyBounds(QueryOptions options) throws InvalidRequestException {
        IPartitioner<?> p = StorageService.getPartitioner();
        if (onToken) {
            Token startToken = getTokenBound(Bound.START, options, p);
            Token endToken = getTokenBound(Bound.END, options, p);
            boolean includeStart = includeKeyBound(Bound.START);
            boolean includeEnd = includeKeyBound(Bound.END);
            int cmp = startToken.compareTo(endToken);
            if (!startToken.isMinimum() && !endToken.isMinimum() && (cmp > 0 || (cmp == 0 && (!includeStart || !includeEnd))))
                return null;
            RowPosition start = includeStart ? startToken.minKeyBound() : startToken.maxKeyBound();
            RowPosition end = includeEnd ? endToken.maxKeyBound() : endToken.minKeyBound();
            return new Range<RowPosition>(start, end);
        } else {
            ByteBuffer startKeyBytes = getKeyBound(Bound.START, options);
            ByteBuffer finishKeyBytes = getKeyBound(Bound.END, options);
            RowPosition startKey = RowPosition.ForKey.get(startKeyBytes, p);
            RowPosition finishKey = RowPosition.ForKey.get(finishKeyBytes, p);
            if (startKey.compareTo(finishKey) > 0 && !finishKey.isMinimum(p))
                return null;
            if (includeKeyBound(Bound.START)) {
                return includeKeyBound(Bound.END) ? new Bounds<RowPosition>(startKey, finishKey) : new IncludingExcludingBounds<RowPosition>(startKey, finishKey);
            } else {
                return includeKeyBound(Bound.END) ? new Range<RowPosition>(startKey, finishKey) : new ExcludingBounds<RowPosition>(startKey, finishKey);
            }
        }
    }

    private IDiskAtomFilter makeFilter(QueryOptions options, int limit) throws InvalidRequestException {
        if (parameters.isDistinct) {
            return new SliceQueryFilter(ColumnSlice.ALL_COLUMNS_ARRAY, false, 1, -1);
        } else if (isColumnRange()) {
            int toGroup = cfm.comparator.isDense() ? -1 : cfm.clusteringColumns().size();
            List<Composite> startBounds = getRequestedBound(Bound.START, options);
            List<Composite> endBounds = getRequestedBound(Bound.END, options);
            assert startBounds.size() == endBounds.size();
            ColumnSlice staticSlice = null;
            if (selectsStaticColumns && !usesSecondaryIndexing) {
                staticSlice = isReversed ? new ColumnSlice(cfm.comparator.staticPrefix().end(), Composites.EMPTY) : new ColumnSlice(Composites.EMPTY, cfm.comparator.staticPrefix().end());
                if (selectsOnlyStaticColumns)
                    return sliceFilter(staticSlice, limit, toGroup);
            }
            if (startBounds.size() == 1) {
                ColumnSlice slice = new ColumnSlice(startBounds.get(0), endBounds.get(0));
                if (slice.isAlwaysEmpty(cfm.comparator, isReversed))
                    return staticSlice == null ? null : sliceFilter(staticSlice, limit, toGroup);
                return staticSlice == null ? sliceFilter(slice, limit, toGroup) : (slice.includes(cfm.comparator, staticSlice.finish) ? sliceFilter(new ColumnSlice(staticSlice.start, slice.finish), limit, toGroup) : sliceFilter(new ColumnSlice[] { staticSlice, slice }, limit, toGroup));
            }
            List<ColumnSlice> l = new ArrayList<ColumnSlice>(startBounds.size());
            for (int i = 0; i < startBounds.size(); i++) {
                ColumnSlice slice = new ColumnSlice(startBounds.get(i), endBounds.get(i));
                if (!slice.isAlwaysEmpty(cfm.comparator, isReversed))
                    l.add(slice);
            }
            if (l.isEmpty())
                return staticSlice == null ? null : sliceFilter(staticSlice, limit, toGroup);
            if (staticSlice == null)
                return sliceFilter(l.toArray(new ColumnSlice[l.size()]), limit, toGroup);
            ColumnSlice[] slices;
            if (isReversed) {
                if (l.get(l.size() - 1).includes(cfm.comparator, staticSlice.start)) {
                    slices = l.toArray(new ColumnSlice[l.size()]);
                    slices[slices.length - 1] = new ColumnSlice(slices[slices.length - 1].start, Composites.EMPTY);
                } else {
                    slices = l.toArray(new ColumnSlice[l.size() + 1]);
                    slices[slices.length - 1] = staticSlice;
                }
            } else {
                if (l.get(0).includes(cfm.comparator, staticSlice.finish)) {
                    slices = new ColumnSlice[l.size()];
                    slices[0] = new ColumnSlice(Composites.EMPTY, l.get(0).finish);
                    for (int i = 1; i < l.size(); i++) slices[i] = l.get(i);
                } else {
                    slices = new ColumnSlice[l.size() + 1];
                    slices[0] = staticSlice;
                    for (int i = 0; i < l.size(); i++) slices[i + 1] = l.get(i);
                }
            }
            return sliceFilter(slices, limit, toGroup);
        } else {
            SortedSet<CellName> cellNames = getRequestedColumns(options);
            if (cellNames == null)
                return null;
            QueryProcessor.validateCellNames(cellNames, cfm.comparator);
            return new NamesQueryFilter(cellNames, true);
        }
    }

    private SliceQueryFilter sliceFilter(ColumnSlice slice, int limit, int toGroup) {
        return sliceFilter(new ColumnSlice[] { slice }, limit, toGroup);
    }

    private SliceQueryFilter sliceFilter(ColumnSlice[] slices, int limit, int toGroup) {
        assert ColumnSlice.validateSlices(slices, cfm.comparator, isReversed) : String.format("Invalid slices: " + Arrays.toString(slices) + (isReversed ? " (reversed)" : ""));
        return new SliceQueryFilter(slices, isReversed, limit, toGroup);
    }

    private int getLimit(QueryOptions options) throws InvalidRequestException {
        int l = Integer.MAX_VALUE;
        if (limit != null) {
            ByteBuffer b = limit.bindAndGet(options);
            if (b == null)
                throw new InvalidRequestException("Invalid null value of limit");
            try {
                Int32Type.instance.validate(b);
                l = Int32Type.instance.compose(b);
            } catch (MarshalException e) {
                throw new InvalidRequestException("Invalid limit value");
            }
        }
        if (l <= 0)
            throw new InvalidRequestException("LIMIT must be strictly positive");
        return l;
    }

    private int updateLimitForQuery(int limit) {
        return sliceRestriction != null && (!sliceRestriction.isInclusive(Bound.START) || !sliceRestriction.isInclusive(Bound.END)) && limit != Integer.MAX_VALUE ? limit + 1 : limit;
    }

    private Collection<ByteBuffer> getKeys(final QueryOptions options) throws InvalidRequestException {
        List<ByteBuffer> keys = new ArrayList<ByteBuffer>();
        CBuilder builder = cfm.getKeyValidatorAsCType().builder();
        for (ColumnDefinition def : cfm.partitionKeyColumns()) {
            Restriction r = keyRestrictions[def.position()];
            assert r != null && !r.isSlice();
            List<ByteBuffer> values = r.values(options);
            if (builder.remainingCount() == 1) {
                for (ByteBuffer val : values) {
                    if (val == null)
                        throw new InvalidRequestException(String.format("Invalid null value for partition key part %s", def.name));
                    keys.add(builder.buildWith(val).toByteBuffer());
                }
            } else {
                if (values.size() != 1)
                    throw new InvalidRequestException("IN is only supported on the last column of the partition key");
                ByteBuffer val = values.get(0);
                if (val == null)
                    throw new InvalidRequestException(String.format("Invalid null value for partition key part %s", def.name));
                builder.add(val);
            }
        }
        return keys;
    }

    private ByteBuffer getKeyBound(Bound b, QueryOptions options) throws InvalidRequestException {
        for (int i = 0; i < keyRestrictions.length; i++) if (keyRestrictions[i] == null)
            return ByteBufferUtil.EMPTY_BYTE_BUFFER;
        return buildBound(b, cfm.partitionKeyColumns(), keyRestrictions, false, cfm.getKeyValidatorAsCType(), options).get(0).toByteBuffer();
    }

    private Token getTokenBound(Bound b, QueryOptions options, IPartitioner<?> p) throws InvalidRequestException {
        assert onToken;
        Restriction restriction = keyRestrictions[0];
        assert !restriction.isMultiColumn() : "Unexpectedly got a multi-column restriction on a partition key for a range query";
        SingleColumnRestriction keyRestriction = (SingleColumnRestriction) restriction;
        ByteBuffer value;
        if (keyRestriction.isEQ()) {
            value = keyRestriction.values(options).get(0);
        } else {
            SingleColumnRestriction.Slice slice = (SingleColumnRestriction.Slice) keyRestriction;
            if (!slice.hasBound(b))
                return p.getMinimumToken();
            value = slice.bound(b, options);
        }
        if (value == null)
            throw new InvalidRequestException("Invalid null token value");
        return p.getTokenFactory().fromByteArray(value);
    }

    private boolean includeKeyBound(Bound b) {
        for (Restriction r : keyRestrictions) {
            if (r == null)
                return true;
            else if (r.isSlice()) {
                assert !r.isMultiColumn() : "Unexpectedly got multi-column restriction on partition key";
                return ((SingleColumnRestriction.Slice) r).isInclusive(b);
            }
        }
        return true;
    }

    private boolean isColumnRange() {
        if (!cfm.comparator.isDense())
            return cfm.comparator.isCompound();
        for (Restriction r : columnRestrictions) {
            if (r == null || r.isSlice())
                return true;
        }
        return false;
    }

    private SortedSet<CellName> getRequestedColumns(QueryOptions options) throws InvalidRequestException {
        assert !isColumnRange();
        CBuilder builder = cfm.comparator.prefixBuilder();
        Iterator<ColumnDefinition> idIter = cfm.clusteringColumns().iterator();
        for (Restriction r : columnRestrictions) {
            ColumnDefinition def = idIter.next();
            assert r != null && !r.isSlice();
            List<ByteBuffer> values = r.values(options);
            if (values.size() == 1) {
                ByteBuffer val = values.get(0);
                if (val == null)
                    throw new InvalidRequestException(String.format("Invalid null value for clustering key part %s", def.name));
                builder.add(val);
            } else {
                if (values.isEmpty())
                    return null;
                SortedSet<CellName> columns = new TreeSet<CellName>(cfm.comparator);
                Iterator<ByteBuffer> iter = values.iterator();
                while (iter.hasNext()) {
                    ByteBuffer val = iter.next();
                    if (val == null)
                        throw new InvalidRequestException(String.format("Invalid null value for clustering key part %s", def.name));
                    Composite prefix = builder.buildWith(val);
                    columns.addAll(addSelectedColumns(prefix));
                }
                return columns;
            }
        }
        return addSelectedColumns(builder.build());
    }

    private SortedSet<CellName> addSelectedColumns(Composite prefix) {
        if (cfm.comparator.isDense()) {
            return FBUtilities.singleton(cfm.comparator.create(prefix, null), cfm.comparator);
        } else {
            assert !selectACollection();
            SortedSet<CellName> columns = new TreeSet<CellName>(cfm.comparator);
            if (cfm.comparator.isCompound() && !cfm.isSuper()) {
                columns.add(cfm.comparator.rowMarker(prefix));
                for (ColumnDefinition def : selection.getColumns()) if (def.kind == ColumnDefinition.Kind.REGULAR || def.kind == ColumnDefinition.Kind.STATIC)
                    columns.add(cfm.comparator.create(prefix, def));
            } else {
                for (ColumnDefinition def : cfm.regularColumns()) columns.add(cfm.comparator.create(prefix, def));
            }
            return columns;
        }
    }

    private boolean selectACollection() {
        if (!cfm.comparator.hasCollections())
            return false;
        for (ColumnDefinition def : selection.getColumns()) {
            if (def.type instanceof CollectionType)
                return true;
        }
        return false;
    }

    private static List<Composite> buildBound(Bound bound, List<ColumnDefinition> defs, Restriction[] restrictions, boolean isReversed, CType type, QueryOptions options) throws InvalidRequestException {
        CBuilder builder = type.builder();
        if (!defs.isEmpty()) {
            Restriction firstRestriction = restrictions[0];
            if (firstRestriction != null && firstRestriction.isMultiColumn()) {
                if (firstRestriction.isSlice())
                    return buildMultiColumnSliceBound(bound, defs, (MultiColumnRestriction.Slice) firstRestriction, isReversed, builder, options);
                else if (firstRestriction.isIN())
                    return buildMultiColumnInBound(bound, defs, (MultiColumnRestriction.IN) firstRestriction, isReversed, builder, type, options);
                else
                    return buildMultiColumnEQBound(bound, defs, (MultiColumnRestriction.EQ) firstRestriction, isReversed, builder, options);
            }
        }
        Bound eocBound = isReversed ? Bound.reverse(bound) : bound;
        for (Iterator<ColumnDefinition> iter = defs.iterator(); iter.hasNext(); ) {
            ColumnDefinition def = iter.next();
            Bound b = isReversed == isReversedType(def) ? bound : Bound.reverse(bound);
            Restriction r = restrictions[def.position()];
            if (isNullRestriction(r, b)) {
                Composite prefix = builder.build();
                return Collections.singletonList(!prefix.isEmpty() && eocBound == Bound.END ? prefix.end() : prefix);
            }
            if (r.isSlice()) {
                builder.add(getSliceValue(r, b, options));
                Relation.Type relType = ((Restriction.Slice) r).getRelation(eocBound, b);
                return Collections.singletonList(builder.build().withEOC(eocForRelation(relType)));
            } else {
                List<ByteBuffer> values = r.values(options);
                if (values.size() != 1) {
                    assert def.position() == defs.size() - 1;
                    TreeSet<Composite> s = new TreeSet<>(isReversed ? type.reverseComparator() : type);
                    for (ByteBuffer val : values) {
                        if (val == null)
                            throw new InvalidRequestException(String.format("Invalid null clustering key part %s", def.name));
                        Composite prefix = builder.buildWith(val);
                        s.add((eocBound == Bound.END && builder.remainingCount() > 0) ? prefix.end() : prefix);
                    }
                    return new ArrayList<>(s);
                }
                ByteBuffer val = values.get(0);
                if (val == null)
                    throw new InvalidRequestException(String.format("Invalid null clustering key part %s", def.name));
                builder.add(val);
            }
        }
        Composite prefix = builder.build();
        return Collections.singletonList(
<<<<<<< commits-rmx_100/apache/cassandra/0932ed670c66ca2f8c5dc1450b85590738b773c9/SelectStatement-9d63389.java
bound == Bound.END && builder.remainingCount() > 0
=======
(eocBound == Bound.END && builder.remainingCount() > 0)
>>>>>>> commits-rmx_100/apache/cassandra/975145c2779a5d99c9f5f192f0693bfb40a9001c/SelectStatement-2710f78.java
 ? prefix.end() : prefix);
    }

    private static List<Composite> buildMultiColumnSliceBound(Bound bound, List<ColumnDefinition> defs, MultiColumnRestriction.Slice slice, boolean isReversed, CBuilder builder, QueryOptions options) throws InvalidRequestException {
        Bound eocBound = isReversed ? Bound.reverse(bound) : bound;
        Iterator<ColumnDefinition> iter = defs.iterator();
        ColumnDefinition firstName = iter.next();
        Bound firstComponentBound = isReversed == isReversedType(firstName) ? bound : Bound.reverse(bound);
        if (!slice.hasBound(firstComponentBound)) {
            Composite prefix = builder.build();
            return Collections.singletonList(builder.remainingCount() > 0 && eocBound == Bound.END ? prefix.end() : prefix);
        }
        List<ByteBuffer> vals = slice.componentBounds(firstComponentBound, options);
        ByteBuffer v = vals.get(firstName.position());
        if (v == null)
            throw new InvalidRequestException("Invalid null value in condition for column " + firstName.name);
        builder.add(v);
        while (iter.hasNext()) {
            ColumnDefinition def = iter.next();
            if (def.position() >= vals.size())
                break;
            v = vals.get(def.position());
            if (v == null)
                throw new InvalidRequestException("Invalid null value in condition for column " + def.name);
            builder.add(v);
        }
        Relation.Type relType = slice.getRelation(eocBound, firstComponentBound);
        return Collections.singletonList(builder.build().withEOC(eocForRelation(relType)));
    }

    private static List<Composite> buildMultiColumnInBound(Bound bound, List<ColumnDefinition> defs, MultiColumnRestriction.IN restriction, boolean isReversed, CBuilder builder, CType type, QueryOptions options) throws InvalidRequestException {
        List<List<ByteBuffer>> splitInValues = restriction.splitValues(options);
        TreeSet<Composite> inValues = new TreeSet<>(isReversed ? type.reverseComparator() : type);
        Iterator<ColumnDefinition> iter = defs.iterator();
        for (List<ByteBuffer> components : splitInValues) {
            for (int i = 0; i < components.size(); i++) if (components.get(i) == null)
                throw new InvalidRequestException("Invalid null value in condition for column " + defs.get(i));
            Composite prefix = builder.buildWith(components);
            Bound b = isReversed == isReversedType(iter.next()) ? bound : Bound.reverse(bound);
            inValues.add(b == Bound.END && builder.remainingCount() - components.size() > 0 ? prefix.end() : prefix);
        }
        return new ArrayList<>(inValues);
    }

    private static List<Composite> buildMultiColumnEQBound(Bound bound, List<ColumnDefinition> defs, MultiColumnRestriction.EQ restriction, boolean isReversed, CBuilder builder, QueryOptions options) throws InvalidRequestException {
        Bound eocBound = isReversed ? Bound.reverse(bound) : bound;
        List<ByteBuffer> values = restriction.values(options);
        for (int i = 0; i < values.size(); i++) {
            ByteBuffer component = values.get(i);
            if (component == null)
                throw new InvalidRequestException("Invalid null value in condition for column " + defs.get(i));
            builder.add(component);
        }
        Composite prefix = builder.build();
        return Collections.singletonList(builder.remainingCount() > 0 && eocBound == Bound.END ? prefix.end() : prefix);
    }

    private static boolean isNullRestriction(Restriction r, Bound b) {
        return r == null || (r.isSlice() && !((Restriction.Slice) r).hasBound(b));
    }

    private static ByteBuffer getSliceValue(Restriction r, Bound b, QueryOptions options) throws InvalidRequestException {
        Restriction.Slice slice = (Restriction.Slice) r;
        assert slice.hasBound(b);
        ByteBuffer val = slice.bound(b, options);
        if (val == null)
            throw new InvalidRequestException(String.format("Invalid null clustering key part %s", r));
        return val;
    }

    private List<Composite> getRequestedBound(Bound b, QueryOptions options) throws InvalidRequestException {
        assert isColumnRange();
        return buildBound(b, cfm.clusteringColumns(), columnRestrictions, isReversed, cfm.comparator, options);
    }

    public List<IndexExpression> getIndexExpressions(QueryOptions options) throws InvalidRequestException {
        if (!usesSecondaryIndexing || restrictedColumns.isEmpty())
            return Collections.emptyList();
        List<IndexExpression> expressions = new ArrayList<IndexExpression>();
        for (ColumnDefinition def : restrictedColumns) {
            Restriction restriction;
            switch(def.kind) {
                case PARTITION_KEY:
                    restriction = keyRestrictions[def.position()];
                    break;
                case CLUSTERING_COLUMN:
                    restriction = columnRestrictions[def.position()];
                    break;
                case REGULAR:
                case STATIC:
                    restriction = metadataRestrictions.get(def.name);
                    break;
                default:
                    throw new AssertionError();
            }
            if (restriction.isSlice()) {
                Restriction.Slice slice = (Restriction.Slice) restriction;
                for (Bound b : Bound.values()) {
                    if (slice.hasBound(b)) {
                        ByteBuffer value = validateIndexedValue(def, slice.bound(b, options));
                        IndexExpression.Operator op = slice.getIndexOperator(b);
                        if (def.type instanceof ReversedType)
                            op = reverse(op);
                        expressions.add(new IndexExpression(def.name.bytes, op, value));
                    }
                }
            } else if (restriction.isContains()) {
                SingleColumnRestriction.Contains contains = (SingleColumnRestriction.Contains) restriction;
                for (ByteBuffer value : contains.values(options)) {
                    validateIndexedValue(def, value);
                    expressions.add(new IndexExpression(def.name.bytes, IndexExpression.Operator.CONTAINS, value));
                }
                for (ByteBuffer key : contains.keys(options)) {
                    validateIndexedValue(def, key);
                    expressions.add(new IndexExpression(def.name.bytes, IndexExpression.Operator.CONTAINS_KEY, key));
                }
            } else {
                List<ByteBuffer> values = restriction.values(options);
                if (values.size() != 1)
                    throw new InvalidRequestException("IN restrictions are not supported on indexed columns");
                ByteBuffer value = validateIndexedValue(def, values.get(0));
                expressions.add(new IndexExpression(def.name.bytes, IndexExpression.Operator.EQ, value));
            }
        }
        return expressions;
    }

    private static ByteBuffer validateIndexedValue(ColumnDefinition def, ByteBuffer value) throws InvalidRequestException {
        if (value == null)
            throw new InvalidRequestException(String.format("Unsupported null value for indexed column %s", def.name));
        if (value.remaining() > 0xFFFF)
            throw new InvalidRequestException("Index expression values may not be larger than 64K");
        return value;
    }

    private static IndexExpression.Operator reverse(IndexExpression.Operator op) {
        switch(op) {
            case LT:
                return IndexExpression.Operator.GT;
            case LTE:
                return IndexExpression.Operator.GTE;
            case GT:
                return IndexExpression.Operator.LT;
            case GTE:
                return IndexExpression.Operator.LTE;
            default:
                return op;
        }
    }

    private ResultSet process(List<Row> rows, QueryOptions options, int limit, long now) throws InvalidRequestException {
        Selection.ResultSetBuilder result = selection.resultSetBuilder(now);
        for (org.apache.cassandra.db.Row row : rows) {
            if (row.cf == null)
                continue;
            processColumnFamily(row.key.getKey(), row.cf, options, now, result);
        }
        ResultSet cqlRows = result.build();
        orderResults(cqlRows);
        if (isReversed)
            cqlRows.reverse();
        cqlRows.trim(limit);
        return cqlRows;
    }

    void processColumnFamily(ByteBuffer key, ColumnFamily cf, QueryOptions options, long now, Selection.ResultSetBuilder result) throws InvalidRequestException {
        CFMetaData cfm = cf.metadata();
        ByteBuffer[] keyComponents = null;
        if (cfm.getKeyValidator() instanceof CompositeType) {
            keyComponents = ((CompositeType) cfm.getKeyValidator()).split(key);
        } else {
            keyComponents = new ByteBuffer[] { key };
        }
        Iterator<Cell> cells = cf.getSortedColumns().iterator();
        if (sliceRestriction != null)
            cells = applySliceRestriction(cells, options);
        CQL3Row.RowIterator iter = cfm.comparator.CQL3RowBuilder(cfm, now).group(cells);
        CQL3Row staticRow = iter.getStaticRow();
        if (staticRow != null && !iter.hasNext() && !usesSecondaryIndexing && hasNoClusteringColumnsRestriction()) {
            result.newRow();
            for (ColumnDefinition def : selection.getColumns()) {
                switch(def.kind) {
                    case PARTITION_KEY:
                        result.add(keyComponents[def.position()]);
                        break;
                    case STATIC:
                        addValue(result, def, staticRow, options);
                        break;
                    default:
                        result.add((ByteBuffer) null);
                }
            }
            return;
        }
        while (iter.hasNext()) {
            CQL3Row cql3Row = iter.next();
            result.newRow();
            for (ColumnDefinition def : selection.getColumns()) {
                switch(def.kind) {
                    case PARTITION_KEY:
                        result.add(keyComponents[def.position()]);
                        break;
                    case CLUSTERING_COLUMN:
                        result.add(cql3Row.getClusteringColumn(def.position()));
                        break;
                    case COMPACT_VALUE:
                        result.add(cql3Row.getColumn(null));
                        break;
                    case REGULAR:
                        addValue(result, def, cql3Row, options);
                        break;
                    case STATIC:
                        addValue(result, def, staticRow, options);
                        break;
                }
            }
        }
    }

    private boolean hasNoClusteringColumnsRestriction() {
        for (int i = 0; i < columnRestrictions.length; i++) if (columnRestrictions[i] != null)
            return false;
        return true;
    }

    private boolean needsPostQueryOrdering() {
        return keyIsInRelation && !parameters.orderings.isEmpty();
    }

    private void orderResults(ResultSet cqlRows) throws InvalidRequestException {
        if (cqlRows.size() == 0 || !needsPostQueryOrdering())
            return;
        assert orderingIndexes != null;
        List<Integer> idToSort = new ArrayList<Integer>();
        List<Comparator<ByteBuffer>> sorters = new ArrayList<Comparator<ByteBuffer>>();
        for (ColumnIdentifier identifier : parameters.orderings.keySet()) {
            ColumnDefinition orderingColumn = cfm.getColumnDefinition(identifier);
            idToSort.add(orderingIndexes.get(orderingColumn.name));
            sorters.add(orderingColumn.type);
        }
        Comparator<List<ByteBuffer>> comparator = idToSort.size() == 1 ? new SingleColumnComparator(idToSort.get(0), sorters.get(0)) : new CompositeComparator(sorters, idToSort);
        Collections.sort(cqlRows.rows, comparator);
    }

    private static void addValue(Selection.ResultSetBuilder result, ColumnDefinition def, CQL3Row row, QueryOptions options) {
        if (row == null) {
            result.add((ByteBuffer) null);
            return;
        }
        if (def.type.isCollection()) {
            List<Cell> collection = row.getCollection(def.name);
            ByteBuffer value = collection == null ? null : ((CollectionType) def.type).serializeForNativeProtocol(collection, options.getProtocolVersion());
            result.add(value);
            return;
        }
        result.add(row.getColumn(def.name));
    }

    private static boolean isReversedType(ColumnDefinition def) {
        return def.type instanceof ReversedType;
    }

    private boolean columnFilterIsIdentity() {
        for (Restriction r : columnRestrictions) {
            if (r != null)
                return false;
        }
        return true;
    }

    private boolean hasClusteringColumnsRestriction() {
        for (int i = 0; i < columnRestrictions.length; i++) if (columnRestrictions[i] != null)
            return true;
        return false;
    }

    static public class RawStatement extends CFStatement {

        private final Parameters parameters;

        private final List<RawSelector> selectClause;

        private final List<Relation> whereClause;

        private final Term.Raw limit;

        public RawStatement(CFName cfName, Parameters parameters, List<RawSelector> selectClause, List<Relation> whereClause, Term.Raw limit) {
            super(cfName);
            this.parameters = parameters;
            this.selectClause = selectClause;
            this.whereClause = whereClause == null ? Collections.<Relation>emptyList() : whereClause;
            this.limit = limit;
        }

        private void handleUnrecognizedEntity(ColumnIdentifier entity, Relation relation) throws InvalidRequestException {
            if (containsAlias(entity))
                throw new InvalidRequestException(String.format("Aliases aren't allowed in the where clause ('%s')", relation));
            else
                throw new InvalidRequestException(String.format("Undefined name %s in where clause ('%s')", entity, relation));
        }

        private void validateSecondaryIndexSelections(SelectStatement stmt) throws InvalidRequestException {
            if (stmt.keyIsInRelation)
                throw new InvalidRequestException("Select on indexed columns and with IN clause for the PRIMARY KEY are not supported");
            if (stmt.selectsOnlyStaticColumns)
                throw new InvalidRequestException("Queries using 2ndary indexes don't support selecting only static columns");
        }

        private void verifyOrderingIsAllowed(SelectStatement stmt) throws InvalidRequestException {
            if (stmt.usesSecondaryIndexing)
                throw new InvalidRequestException("ORDER BY with 2ndary indexes is not supported.");
            if (stmt.isKeyRange)
                throw new InvalidRequestException("ORDER BY is only supported when the partition key is restricted by an EQ or an IN.");
        }

        private void handleUnrecognizedOrderingColumn(ColumnIdentifier column) throws InvalidRequestException {
            if (containsAlias(column))
                throw new InvalidRequestException(String.format("Aliases are not allowed in order by clause ('%s')", column));
            else
                throw new InvalidRequestException(String.format("Order by on unknown column %s", column));
        }

        private boolean containsAlias(final ColumnIdentifier name) {
            return Iterables.any(selectClause, new Predicate<RawSelector>() {

                public boolean apply(RawSelector raw) {
                    return name.equals(raw.alias);
                }
            });
        }

        private ColumnSpecification limitReceiver() {
            return new ColumnSpecification(keyspace(), columnFamily(), new ColumnIdentifier("[limit]", true), Int32Type.instance);
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this).add("name", cfName).add("selectClause", selectClause).add("whereClause", whereClause).add("isDistinct", parameters.isDistinct).add("isCount", parameters.isCount).toString();
        }

        public ParsedStatement.Prepared prepare() throws InvalidRequestException {
            CFMetaData cfm = ThriftValidation.validateColumnFamily(keyspace(), columnFamily());
            VariableSpecifications boundNames = getBoundVariables();
            if (parameters.isCount && !selectClause.isEmpty())
                throw new InvalidRequestException("Only COUNT(*) and COUNT(1) operations are currently supported.");
            Selection selection = selectClause.isEmpty() ? Selection.wildcard(cfm) : Selection.fromSelectors(cfm, selectClause);
            if (parameters.isDistinct)
                validateDistinctSelection(selection.getColumns(), cfm.partitionKeyColumns());
            SelectStatement stmt = new SelectStatement(cfm, boundNames.size(), parameters, selection, prepareLimit(boundNames));
            boolean hasQueriableIndex = false;
            boolean hasQueriableClusteringColumnIndex = false;
            for (Relation relation : whereClause) {
                if (relation.isMultiColumn()) {
                    MultiColumnRelation rel = (MultiColumnRelation) relation;
                    List<ColumnDefinition> names = new ArrayList<>(rel.getEntities().size());
                    for (ColumnIdentifier entity : rel.getEntities()) {
                        ColumnDefinition def = cfm.getColumnDefinition(entity);
                        boolean[] queriable = processRelationEntity(stmt, relation, entity, def);
                        hasQueriableIndex |= queriable[0];
                        hasQueriableClusteringColumnIndex |= queriable[1];
                        names.add(def);
                    }
                    updateRestrictionsForRelation(stmt, names, rel, boundNames);
                } else {
                    SingleColumnRelation rel = (SingleColumnRelation) relation;
                    ColumnIdentifier entity = rel.getEntity();
                    ColumnDefinition def = cfm.getColumnDefinition(entity);
                    boolean[] queriable = processRelationEntity(stmt, relation, entity, def);
                    hasQueriableIndex |= queriable[0];
                    hasQueriableClusteringColumnIndex |= queriable[1];
                    updateRestrictionsForRelation(stmt, def, rel, boundNames);
                }
            }
            processPartitionKeyRestrictions(stmt, hasQueriableIndex, cfm);
            if (!stmt.usesSecondaryIndexing)
                stmt.restrictedColumns.removeAll(cfm.partitionKeyColumns());
            if (stmt.selectsOnlyStaticColumns && stmt.hasClusteringColumnsRestriction())
                throw new InvalidRequestException("Cannot restrict clustering columns when selecting only static columns");
            processColumnRestrictions(stmt, hasQueriableIndex, cfm);
            if (stmt.isKeyRange && hasQueriableClusteringColumnIndex)
                stmt.usesSecondaryIndexing = true;
            if (!stmt.usesSecondaryIndexing)
                stmt.restrictedColumns.removeAll(cfm.clusteringColumns());
            if (!stmt.metadataRestrictions.isEmpty()) {
                if (!hasQueriableIndex)
                    throw new InvalidRequestException("No indexed columns present in by-columns clause with Equal operator");
                stmt.usesSecondaryIndexing = true;
            }
            if (stmt.usesSecondaryIndexing)
                validateSecondaryIndexSelections(stmt);
            if (!stmt.parameters.orderings.isEmpty())
                processOrderingClause(stmt, cfm);
            checkNeedsFiltering(stmt);
            return new ParsedStatement.Prepared(stmt, boundNames);
        }

        private boolean[] processRelationEntity(SelectStatement stmt, Relation relation, ColumnIdentifier entity, ColumnDefinition def) throws InvalidRequestException {
            if (def == null)
                handleUnrecognizedEntity(entity, relation);
            stmt.restrictedColumns.add(def);
            if (def.isIndexed() && relation.operator().allowsIndexQuery())
                return new boolean[] { true, def.kind == ColumnDefinition.Kind.CLUSTERING_COLUMN };
            return new boolean[] { false, false };
        }

        private Term prepareLimit(VariableSpecifications boundNames) throws InvalidRequestException {
            if (limit == null)
                return null;
            Term prepLimit = limit.prepare(keyspace(), limitReceiver());
            prepLimit.collectMarkerSpecification(boundNames);
            return prepLimit;
        }

        private void updateRestrictionsForRelation(SelectStatement stmt, List<ColumnDefinition> defs, MultiColumnRelation relation, VariableSpecifications boundNames) throws InvalidRequestException {
            List<ColumnDefinition> restrictedColumns = new ArrayList<>();
            Set<ColumnDefinition> seen = new HashSet<>();
            int previousPosition = -1;
            for (ColumnDefinition def : defs) {
                if (def.kind != ColumnDefinition.Kind.CLUSTERING_COLUMN)
                    throw new InvalidRequestException(String.format("Multi-column relations can only be applied to clustering columns: %s", def));
                if (seen.contains(def))
                    throw new InvalidRequestException(String.format("Column \"%s\" appeared twice in a relation: %s", def, relation));
                seen.add(def);
                if (def.position() != previousPosition + 1) {
                    if (previousPosition == -1)
                        throw new InvalidRequestException(String.format("Clustering columns may not be skipped in multi-column relations. " + "They should appear in the PRIMARY KEY order. Got %s", relation));
                    else
                        throw new InvalidRequestException(String.format("Clustering columns must appear in the PRIMARY KEY order in multi-column relations: %s", relation));
                }
                previousPosition++;
                Restriction existing = getExistingRestriction(stmt, def);
                Relation.Type operator = relation.operator();
                if (existing != null) {
                    if (operator == Relation.Type.EQ || operator == Relation.Type.IN)
                        throw new InvalidRequestException(String.format("Column \"%s\" cannot be restricted by more than one relation if it is in an %s relation", def, relation.operator()));
                    else if (!existing.isSlice())
                        throw new InvalidRequestException(String.format("Column \"%s\" cannot be restricted by an equality relation and an inequality relation", def));
                }
                restrictedColumns.add(def);
            }
            switch(relation.operator()) {
                case EQ:
                    {
                        Term t = relation.getValue().prepare(keyspace(), defs);
                        t.collectMarkerSpecification(boundNames);
                        Restriction restriction = new MultiColumnRestriction.EQ(t, false);
                        for (ColumnDefinition def : restrictedColumns) stmt.columnRestrictions[def.position()] = restriction;
                        break;
                    }
                case IN:
                    {
                        Restriction restriction;
                        List<? extends Term.MultiColumnRaw> inValues = relation.getInValues();
                        if (inValues != null) {
                            List<Term> terms = new ArrayList<>(inValues.size());
                            for (Term.MultiColumnRaw tuple : inValues) {
                                Term t = tuple.prepare(keyspace(), defs);
                                t.collectMarkerSpecification(boundNames);
                                terms.add(t);
                            }
                            restriction = new MultiColumnRestriction.InWithValues(terms);
                        } else {
                            Tuples.INRaw rawMarker = relation.getInMarker();
                            AbstractMarker t = rawMarker.prepare(keyspace(), defs);
                            t.collectMarkerSpecification(boundNames);
                            restriction = new MultiColumnRestriction.InWithMarker(t);
                        }
                        for (ColumnDefinition def : restrictedColumns) stmt.columnRestrictions[def.position()] = restriction;
                        break;
                    }
                case LT:
                case LTE:
                case GT:
                case GTE:
                    {
                        Term t = relation.getValue().prepare(keyspace(), defs);
                        t.collectMarkerSpecification(boundNames);
                        for (ColumnDefinition def : defs) {
                            Restriction.Slice restriction = (Restriction.Slice) getExistingRestriction(stmt, def);
                            if (restriction == null)
                                restriction = new MultiColumnRestriction.Slice(false);
                            else if (!restriction.isMultiColumn())
                                throw new InvalidRequestException(String.format("Column \"%s\" cannot have both tuple-notation inequalities and single-column inequalities: %s", def.name, relation));
                            restriction.setBound(def.name, relation.operator(), t);
                            stmt.columnRestrictions[def.position()] = restriction;
                        }
                    }
            }
        }

        private Restriction getExistingRestriction(SelectStatement stmt, ColumnDefinition def) {
            switch(def.kind) {
                case PARTITION_KEY:
                    return stmt.keyRestrictions[def.position()];
                case CLUSTERING_COLUMN:
                    return stmt.columnRestrictions[def.position()];
                case REGULAR:
                case STATIC:
                    return stmt.metadataRestrictions.get(def.name);
                default:
                    throw new AssertionError();
            }
        }

        private void updateRestrictionsForRelation(SelectStatement stmt, ColumnDefinition def, SingleColumnRelation relation, VariableSpecifications names) throws InvalidRequestException {
            switch(def.kind) {
                case PARTITION_KEY:
                    stmt.keyRestrictions[def.position()] = updateSingleColumnRestriction(def, stmt.keyRestrictions[def.position()], relation, names);
                    break;
                case CLUSTERING_COLUMN:
                    stmt.columnRestrictions[def.position()] = updateSingleColumnRestriction(def, stmt.columnRestrictions[def.position()], relation, names);
                    break;
                case COMPACT_VALUE:
                    throw new InvalidRequestException(String.format("Predicates on the non-primary-key column (%s) of a COMPACT table are not yet supported", def.name));
                case REGULAR:
                case STATIC:
                    Restriction r = updateSingleColumnRestriction(def, stmt.metadataRestrictions.get(def.name), relation, names);
                    if (r.isIN() && !((Restriction.IN) r).canHaveOnlyOneValue())
                        throw new InvalidRequestException(String.format("IN predicates on non-primary-key columns (%s) is not yet supported", def.name));
                    stmt.metadataRestrictions.put(def.name, r);
                    break;
            }
        }

        Restriction updateSingleColumnRestriction(ColumnDefinition def, Restriction existingRestriction, SingleColumnRelation newRel, VariableSpecifications boundNames) throws InvalidRequestException {
            ColumnSpecification receiver = def;
            if (newRel.onToken) {
                if (def.kind != ColumnDefinition.Kind.PARTITION_KEY)
                    throw new InvalidRequestException(String.format("The token() function is only supported on the partition key, found on %s", def.name));
                receiver = new ColumnSpecification(def.ksName, def.cfName, new ColumnIdentifier("partition key token", true), StorageService.getPartitioner().getTokenValidator());
            }
            switch(newRel.operator()) {
                case EQ:
                    {
                        if (existingRestriction != null)
                            throw new InvalidRequestException(String.format("%s cannot be restricted by more than one relation if it includes an Equal", def.name));
                        Term t = newRel.getValue().prepare(keyspace(), receiver);
                        t.collectMarkerSpecification(boundNames);
                        existingRestriction = new SingleColumnRestriction.EQ(t, newRel.onToken);
                    }
                    break;
                case IN:
                    if (existingRestriction != null)
                        throw new InvalidRequestException(String.format("%s cannot be restricted by more than one relation if it includes a IN", def.name));
                    if (newRel.getInValues() == null) {
                        assert newRel.getValue() != null;
                        Term t = newRel.getValue().prepare(keyspace(), receiver);
                        t.collectMarkerSpecification(boundNames);
                        existingRestriction = new SingleColumnRestriction.InWithMarker((Lists.Marker) t);
                    } else {
                        List<Term> inValues = new ArrayList<>(newRel.getInValues().size());
                        for (Term.Raw raw : newRel.getInValues()) {
                            Term t = raw.prepare(keyspace(), receiver);
                            t.collectMarkerSpecification(boundNames);
                            inValues.add(t);
                        }
                        existingRestriction = new SingleColumnRestriction.InWithValues(inValues);
                    }
                    break;
                case GT:
                case GTE:
                case LT:
                case LTE:
                    {
                        if (existingRestriction == null)
                            existingRestriction = new SingleColumnRestriction.Slice(newRel.onToken);
                        else if (!existingRestriction.isSlice())
                            throw new InvalidRequestException(String.format("Column \"%s\" cannot be restricted by both an equality and an inequality relation", def.name));
                        else if (existingRestriction.isMultiColumn())
                            throw new InvalidRequestException(String.format("Column \"%s\" cannot be restricted by both a tuple notation inequality and a single column inequality (%s)", def.name, newRel));
                        Term t = newRel.getValue().prepare(keyspace(), receiver);
                        t.collectMarkerSpecification(boundNames);
                        ((SingleColumnRestriction.Slice) existingRestriction).setBound(def.name, newRel.operator(), t);
                    }
                    break;
                case CONTAINS_KEY:
                    if (!(receiver.type instanceof MapType))
                        throw new InvalidRequestException(String.format("Cannot use CONTAINS_KEY on non-map column %s", def.name));
                case CONTAINS:
                    {
                        if (!receiver.type.isCollection())
                            throw new InvalidRequestException(String.format("Cannot use %s relation on non collection column %s", newRel.operator(), def.name));
                        if (existingRestriction == null)
                            existingRestriction = new SingleColumnRestriction.Contains();
                        else if (!existingRestriction.isContains())
                            throw new InvalidRequestException(String.format("Collection column %s can only be restricted by CONTAINS or CONTAINS KEY", def.name));
                        boolean isKey = newRel.operator() == Relation.Type.CONTAINS_KEY;
                        receiver = makeCollectionReceiver(receiver, isKey);
                        Term t = newRel.getValue().prepare(keyspace(), receiver);
                        ((SingleColumnRestriction.Contains) existingRestriction).add(t, isKey);
                    }
            }
            return existingRestriction;
        }

        private void processPartitionKeyRestrictions(SelectStatement stmt, boolean hasQueriableIndex, CFMetaData cfm) throws InvalidRequestException {
            boolean canRestrictFurtherComponents = true;
            ColumnDefinition previous = null;
            stmt.keyIsInRelation = false;
            Iterator<ColumnDefinition> iter = cfm.partitionKeyColumns().iterator();
            for (int i = 0; i < stmt.keyRestrictions.length; i++) {
                ColumnDefinition cdef = iter.next();
                Restriction restriction = stmt.keyRestrictions[i];
                if (restriction == null) {
                    if (stmt.onToken)
                        throw new InvalidRequestException("The token() function must be applied to all partition key components or none of them");
                    if (i > 0 && stmt.keyRestrictions[i - 1] != null) {
                        if (hasQueriableIndex) {
                            stmt.usesSecondaryIndexing = true;
                            stmt.isKeyRange = true;
                            break;
                        }
                        throw new InvalidRequestException(String.format("Partition key part %s must be restricted since preceding part is", cdef.name));
                    }
                    stmt.isKeyRange = true;
                    canRestrictFurtherComponents = false;
                } else if (!canRestrictFurtherComponents) {
                    if (hasQueriableIndex) {
                        stmt.usesSecondaryIndexing = true;
                        break;
                    }
                    throw new InvalidRequestException(String.format("Partitioning column \"%s\" cannot be restricted because the preceding column (\"%s\") is " + "either not restricted or is restricted by a non-EQ relation", cdef.name, previous));
                } else if (restriction.isOnToken()) {
                    stmt.isKeyRange = true;
                    stmt.onToken = true;
                } else if (stmt.onToken) {
                    throw new InvalidRequestException(String.format("The token() function must be applied to all partition key components or none of them"));
                } else if (!restriction.isSlice()) {
                    if (restriction.isIN()) {
                        if (i != stmt.keyRestrictions.length - 1)
                            throw new InvalidRequestException(String.format("Partition KEY part %s cannot be restricted by IN relation (only the last part of the partition key can)", cdef.name));
                        stmt.keyIsInRelation = true;
                    }
                } else {
                    throw new InvalidRequestException("Only EQ and IN relation are supported on the partition key (unless you use the token() function)");
                }
                previous = cdef;
            }
        }

        private void processColumnRestrictions(SelectStatement stmt, boolean hasQueriableIndex, CFMetaData cfm) throws InvalidRequestException {
            boolean canRestrictFurtherComponents = true;
            ColumnDefinition previous = null;
            boolean previousIsSlice = false;
            Iterator<ColumnDefinition> iter = cfm.clusteringColumns().iterator();
            for (int i = 0; i < stmt.columnRestrictions.length; i++) {
                ColumnDefinition cdef = iter.next();
                Restriction restriction = stmt.columnRestrictions[i];
                if (restriction == null) {
                    canRestrictFurtherComponents = false;
                    previousIsSlice = false;
                } else if (!canRestrictFurtherComponents) {
                    if (!(previousIsSlice && restriction.isSlice() && restriction.isMultiColumn())) {
                        if (hasQueriableIndex) {
                            stmt.usesSecondaryIndexing = true;
                            break;
                        }
                        throw new InvalidRequestException(String.format("PRIMARY KEY column \"%s\" cannot be restricted (preceding column \"%s\" is either not restricted or by a non-EQ relation)", cdef.name, previous));
                    }
                } else if (restriction.isSlice()) {
                    canRestrictFurtherComponents = false;
                    previousIsSlice = true;
                    Restriction.Slice slice = (Restriction.Slice) restriction;
                    if (!cfm.comparator.isCompound() && (!slice.isInclusive(Bound.START) || !slice.isInclusive(Bound.END)))
                        stmt.sliceRestriction = slice;
                } else if (restriction.isIN()) {
                    if (!restriction.isMultiColumn() && i != stmt.columnRestrictions.length - 1)
                        throw new InvalidRequestException(String.format("Clustering column \"%s\" cannot be restricted by an IN relation", cdef.name));
                    else if (stmt.selectACollection())
                        throw new InvalidRequestException(String.format("Cannot restrict column \"%s\" by IN relation as a collection is selected by the query", cdef.name));
                }
                previous = cdef;
            }
        }

        private void processOrderingClause(SelectStatement stmt, CFMetaData cfm) throws InvalidRequestException {
            verifyOrderingIsAllowed(stmt);
            if (stmt.keyIsInRelation) {
                stmt.orderingIndexes = new HashMap<>();
                for (ColumnIdentifier column : stmt.parameters.orderings.keySet()) {
                    final ColumnDefinition def = cfm.getColumnDefinition(column);
                    if (def == null)
                        handleUnrecognizedOrderingColumn(column);
                    int index = indexOf(def, stmt.selection);
                    if (index < 0)
                        index = stmt.selection.addColumnForOrdering(def);
                    stmt.orderingIndexes.put(def.name, index);
                }
            }
            stmt.isReversed = isReversed(stmt, cfm);
        }

        private boolean isReversed(SelectStatement stmt, CFMetaData cfm) throws InvalidRequestException {
            Boolean[] reversedMap = new Boolean[cfm.clusteringColumns().size()];
            int i = 0;
            for (Map.Entry<ColumnIdentifier, Boolean> entry : stmt.parameters.orderings.entrySet()) {
                ColumnIdentifier column = entry.getKey();
                boolean reversed = entry.getValue();
                ColumnDefinition def = cfm.getColumnDefinition(column);
                if (def == null)
                    handleUnrecognizedOrderingColumn(column);
                if (def.kind != ColumnDefinition.Kind.CLUSTERING_COLUMN)
                    throw new InvalidRequestException(String.format("Order by is currently only supported on the clustered columns of the PRIMARY KEY, got %s", column));
                if (i++ != def.position())
                    throw new InvalidRequestException(String.format("Order by currently only support the ordering of columns following their declared order in the PRIMARY KEY"));
                reversedMap[def.position()] = (reversed != isReversedType(def));
            }
            Boolean isReversed = null;
            for (Boolean b : reversedMap) {
                if (b == null)
                    continue;
                if (isReversed == null) {
                    isReversed = b;
                    continue;
                }
                if (!isReversed.equals(b))
                    throw new InvalidRequestException(String.format("Unsupported order by relation"));
            }
            assert isReversed != null;
            return isReversed;
        }

        private void checkNeedsFiltering(SelectStatement stmt) throws InvalidRequestException {
            if (!parameters.allowFiltering && (stmt.isKeyRange || stmt.usesSecondaryIndexing)) {
                if (stmt.restrictedColumns.size() > 1 || (stmt.restrictedColumns.isEmpty() && !stmt.columnFilterIsIdentity()))
                    throw new InvalidRequestException("Cannot execute this query as it might involve data filtering and " + "thus may have unpredictable performance. If you want to execute " + "this query despite the performance unpredictability, use ALLOW FILTERING");
            }
        }

        private int indexOf(ColumnDefinition def, Selection selection) {
            return indexOf(def, selection.getColumns().iterator());
        }

        private int indexOf(final ColumnDefinition def, Iterator<ColumnDefinition> defs) {
            return Iterators.indexOf(defs, new Predicate<ColumnDefinition>() {

                public boolean apply(ColumnDefinition n) {
                    return def.name.equals(n.name);
                }
            });
        }

        private void validateDistinctSelection(Collection<ColumnDefinition> requestedColumns, Collection<ColumnDefinition> partitionKey) throws InvalidRequestException {
            for (ColumnDefinition def : requestedColumns) if (!partitionKey.contains(def))
                throw new InvalidRequestException(String.format("SELECT DISTINCT queries must only request partition key columns (not %s)", def.name));
            for (ColumnDefinition def : partitionKey) if (!requestedColumns.contains(def))
                throw new InvalidRequestException(String.format("SELECT DISTINCT queries must request all the partition key columns (missing %s)", def.name));
        }

        private static ColumnSpecification makeCollectionReceiver(ColumnSpecification collection, boolean isKey) {
            assert collection.type.isCollection();
            switch(((CollectionType) collection.type).kind) {
                case LIST:
                    assert !isKey;
                    return Lists.valueSpecOf(collection);
                case SET:
                    assert !isKey;
                    return Sets.valueSpecOf(collection);
                case MAP:
                    return isKey ? Maps.keySpecOf(collection) : Maps.valueSpecOf(collection);
            }
            throw new AssertionError();
        }
    }

    static public class Parameters {

        private final Map<ColumnIdentifier, Boolean> orderings;

        private final boolean isDistinct;

        private final boolean isCount;

        private final ColumnIdentifier countAlias;

        private final boolean allowFiltering;

        public Parameters(Map<ColumnIdentifier, Boolean> orderings, boolean isDistinct, boolean isCount, ColumnIdentifier countAlias, boolean allowFiltering) {
            this.orderings = orderings;
            this.isDistinct = isDistinct;
            this.isCount = isCount;
            this.countAlias = countAlias;
            this.allowFiltering = allowFiltering;
        }
    }

    private static class SingleColumnComparator implements Comparator<List<ByteBuffer>> {

        private final int index;

        public int compare(List<ByteBuffer> a, List<ByteBuffer> b) {
            return comparator.compare(a.get(index), b.get(index));
        }

        private final Comparator<ByteBuffer> comparator;

        public SingleColumnComparator(int columnIndex, Comparator<ByteBuffer> orderer) {
            index = columnIndex;
            comparator = orderer;
        }
    }

    private static class CompositeComparator implements Comparator<List<ByteBuffer>> {

        private final List<Comparator<ByteBuffer>> orderTypes;

        private final List<Integer> positions;

        private CompositeComparator(List<Comparator<ByteBuffer>> orderTypes, List<Integer> positions) {
            this.orderTypes = orderTypes;
            this.positions = positions;
        }

        public int compare(List<ByteBuffer> a, List<ByteBuffer> b) {
            for (int i = 0; i < positions.size(); i++) {
                Comparator<ByteBuffer> type = orderTypes.get(i);
                int columnPos = positions.get(i);
                ByteBuffer aValue = a.get(columnPos);
                ByteBuffer bValue = b.get(columnPos);
                int comparison = type.compare(aValue, bValue);
                if (comparison != 0)
                    return comparison;
            }
            return 0;
        }
    }

    public Pageable getPageableCommand(QueryOptions options) throws RequestValidationException {
        return getPageableCommand(options, getLimit(options), System.currentTimeMillis());
    }

    private static final Predicate<ColumnDefinition> isStaticFilter = new Predicate<ColumnDefinition>() {

        public boolean apply(ColumnDefinition def) {
            return def.isStatic();
        }
    };

    private Iterator<Cell> applySliceRestriction(final Iterator<Cell> cells, final QueryOptions options) throws InvalidRequestException {
        assert sliceRestriction != null;
        final CellNameType type = cfm.comparator;
        final CellName excludedStart = sliceRestriction.isInclusive(Bound.START) ? null : type.makeCellName(sliceRestriction.bound(Bound.START, options));
        final CellName excludedEnd = sliceRestriction.isInclusive(Bound.END) ? null : type.makeCellName(sliceRestriction.bound(Bound.END, options));
        return new AbstractIterator<Cell>() {

            protected Cell computeNext() {
                while (cells.hasNext()) {
                    Cell c = cells.next();
                    if ((excludedStart != null && type.compare(c.name(), excludedStart) == 0) || (excludedEnd != null && type.compare(c.name(), excludedEnd) == 0))
                        continue;
                    return c;
                }
                return endOfData();
            }
        };
    }

    private final Set<ColumnDefinition> restrictedColumns = new HashSet<ColumnDefinition>();

    private final Map<ColumnIdentifier, Restriction> metadataRestrictions = new HashMap<ColumnIdentifier, Restriction>();

    public ResultMessage.Rows execute(QueryState state, QueryOptions options) throws RequestExecutionException, RequestValidationException {
        ConsistencyLevel cl = options.getConsistency();
        if (cl == null)
            throw new InvalidRequestException("Invalid empty consistency level");
        cl.validateForRead(keyspace());
        int limit = getLimit(options);
        long now = System.currentTimeMillis();
        Pageable command = getPageableCommand(options, limit, now);
        int pageSize = options.getPageSize();
        if (parameters.isCount && pageSize <= 0)
            pageSize = DEFAULT_COUNT_PAGE_SIZE;
        if (pageSize <= 0 || command == null || !QueryPagers.mayNeedPaging(command, pageSize)) {
            return execute(command, options, limit, now);
        } else {
            QueryPager pager = QueryPagers.pager(command, cl, options.getPagingState());
            if (parameters.isCount)
                return pageCountQuery(pager, options, pageSize, now, limit);
            if (needsPostQueryOrdering())
                throw new InvalidRequestException("Cannot page queries with both ORDER BY and a IN restriction on the partition key; you must either remove the " + "ORDER BY or the IN and sort client side, or disable paging for this query");
            List<Row> page = pager.fetchPage(pageSize);
            ResultMessage.Rows msg = processResults(page, options, limit, now);
            return pager.isExhausted() ? msg : msg.withPagingState(pager.state());
        }
    }

    private static Composite.EOC eocForRelation(Relation.Type op) {
        switch(op) {
            case LT:
                return Composite.EOC.START;
            case GT:
            case LTE:
                return Composite.EOC.END;
            default:
                return Composite.EOC.NONE;
        }
    }

    private Pageable getPageableCommand(QueryOptions options, int limit, long now) throws RequestValidationException {
        int limitForQuery = updateLimitForQuery(limit);
        if (isKeyRange || usesSecondaryIndexing)
            return getRangeCommand(options, limitForQuery, now);
        List<ReadCommand> commands = getSliceCommands(options, limitForQuery, now);
        return commands == null ? null : new Pageable.ReadCommands(commands);
    }
}
