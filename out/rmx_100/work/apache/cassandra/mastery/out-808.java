package org.apache.cassandra.cql3.statements;

import java.nio.ByteBuffer;
import java.util.*;
import org.apache.cassandra.cql3.*;
import org.apache.cassandra.config.CFMetaData;
import org.apache.cassandra.db.*;
import org.apache.cassandra.exceptions.*;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.cassandra.utils.Pair;
import org.apache.cassandra.config.ColumnDefinition;
import org.apache.cassandra.db.composites.Composite;

public class UpdateStatement extends ModificationStatement {

    private UpdateStatement(StatementType type, int boundTerms, CFMetaData cfm, Attributes attrs) {
        super(type, boundTerms, cfm, attrs);
    }

    public boolean requireFullClusteringKey() {
        return true;
    }

    public void addUpdateForKey(ColumnFamily cf, ByteBuffer key, Composite prefix, UpdateParameters params) throws InvalidRequestException {
        if (cfm.isCQL3Table() && !prefix.isStatic())
            cf.addColumn(params.makeColumn(cfm.comparator.rowMarker(prefix), ByteBufferUtil.EMPTY_BYTE_BUFFER));
        List<Operation> updates = getOperations();
        if (cfm.comparator.isDense()) {
            if (prefix.isEmpty())
                throw new InvalidRequestException(String.format("Missing PRIMARY KEY part %s", cfm.clusteringColumns().iterator().next()));
            if (!cfm.compactValueColumn().name.bytes.hasRemaining()) {
                assert updates.isEmpty();
                new Constants.Setter(cfm.compactValueColumn(), EMPTY).execute(key, cf, prefix, params);
            } else {
                if (updates.isEmpty())
                    throw new InvalidRequestException(String.format("Column %s is mandatory for this COMPACT STORAGE table", cfm.compactValueColumn().name));
                for (Operation update : updates) update.execute(key, cf, prefix, params);
            }
        } else {
            for (Operation update : updates) update.execute(key, cf, prefix, params);
        }
    }

    
<<<<<<< commits-rmx_100/apache/cassandra/13b753b0169aa668c8c6091d68705a4034f7cdd4/UpdateStatement-069f31f.java
public ColumnFamily updateForKey(ByteBuffer key, Composite prefix, UpdateParameters params) throws InvalidRequestException {
        ColumnFamily cf = ArrayBackedSortedColumns.factory.create(cfm);
        addUpdateForKey(cf, key, prefix, params);
        return cf;
    }
=======

>>>>>>> commits-rmx_100/apache/cassandra/54a7e0034148f451ff493f9f5363c26f10a21f20/UpdateStatement-dcf22ef.java


    static public class ParsedInsert extends ModificationStatement.Parsed {

        private final List<ColumnIdentifier> columnNames;

        private final List<Term.Raw> columnValues;

        public ParsedInsert(CFName name, Attributes.Raw attrs, List<ColumnIdentifier> columnNames, List<Term.Raw> columnValues, boolean ifNotExists) {
            super(name, attrs, null, ifNotExists);
            this.columnNames = columnNames;
            this.columnValues = columnValues;
        }

        protected ModificationStatement prepareInternal(CFMetaData cfm, VariableSpecifications boundNames, Attributes attrs) throws InvalidRequestException {
            UpdateStatement stmt = new UpdateStatement(ModificationStatement.StatementType.INSERT, boundNames.size(), cfm, attrs);
            if (stmt.isCounter())
                throw new InvalidRequestException("INSERT statement are not allowed on counter tables, use UPDATE instead");
            if (columnNames.size() != columnValues.size())
                throw new InvalidRequestException("Unmatched column names/values");
            if (columnNames.isEmpty())
                throw new InvalidRequestException("No columns provided to INSERT");
            for (int i = 0; i < columnNames.size(); i++) {
                ColumnDefinition def = cfm.getColumnDefinition(columnNames.get(i));
                if (def == null)
                    throw new InvalidRequestException(String.format("Unknown identifier %s", columnNames.get(i)));
                for (int j = 0; j < i; j++) if (def.name.equals(columnNames.get(j)))
                    throw new InvalidRequestException(String.format("Multiple definitions found for column %s", def.name));
                Term.Raw value = columnValues.get(i);
                switch(def.kind) {
                    case PARTITION_KEY:
                    case CLUSTERING_COLUMN:
                        Term t = value.prepare(keyspace(), def);
                        t.collectMarkerSpecification(boundNames);
                        stmt.addKeyValue(def, t);
                        break;
                    default:
                        Operation operation = new Operation.SetValue(value).prepare(keyspace(), def);
                        operation.collectMarkerSpecification(boundNames);
                        stmt.addOperation(operation);
                        break;
                }
            }
            return stmt;
        }
    }

    static public class ParsedUpdate extends ModificationStatement.Parsed {

        private final List<Pair<ColumnIdentifier, Operation.RawUpdate>> updates;

        private final List<Relation> whereClause;

        public ParsedUpdate(CFName name, Attributes.Raw attrs, List<Pair<ColumnIdentifier, Operation.RawUpdate>> updates, List<Relation> whereClause, List<Pair<ColumnIdentifier, ColumnCondition.Raw>> conditions) {
            super(name, attrs, conditions, false);
            this.updates = updates;
            this.whereClause = whereClause;
        }

        protected ModificationStatement prepareInternal(CFMetaData cfm, VariableSpecifications boundNames, Attributes attrs) throws InvalidRequestException {
            UpdateStatement stmt = new UpdateStatement(ModificationStatement.StatementType.UPDATE, boundNames.size(), cfm, attrs);
            for (Pair<ColumnIdentifier, Operation.RawUpdate> entry : updates) {
                ColumnDefinition def = cfm.getColumnDefinition(entry.left);
                if (def == null)
                    throw new InvalidRequestException(String.format("Unknown identifier %s", entry.left));
                Operation operation = entry.right.prepare(keyspace(), def);
                operation.collectMarkerSpecification(boundNames);
                switch(def.kind) {
                    case PARTITION_KEY:
                    case CLUSTERING_COLUMN:
                        throw new InvalidRequestException(String.format("PRIMARY KEY part %s found in SET part", entry.left));
                    default:
                        stmt.addOperation(operation);
                        break;
                }
            }
            stmt.processWhereClause(whereClause, boundNames);
            return stmt;
        }
    }

    private static final Constants.Value EMPTY = new Constants.Value(ByteBufferUtil.EMPTY_BYTE_BUFFER);
}
