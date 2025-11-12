package org.elasticsearch.search.sort;

import org.apache.lucene.search.SortField;
import java.io.IOException;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.ParsingException;
import org.elasticsearch.common.ParseFieldMatcher;
import org.elasticsearch.index.query.QueryParseContext;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentFactory;

public class FieldSortBuilderTests extends AbstractSortTestCase<FieldSortBuilder> {

    @Override
    protected FieldSortBuilder createTestItem() {
        return randomFieldSortBuilder();
    }

    public static FieldSortBuilder randomFieldSortBuilder() {
        String fieldName = rarely() ? FieldSortBuilder.DOC_FIELD_NAME : randomAsciiOfLengthBetween(1, 10);
        FieldSortBuilder builder = new FieldSortBuilder(fieldName);
        if (randomBoolean()) {
            builder.order(RandomSortDataGenerator.order(null));
        }
        if (randomBoolean()) {
            builder.missing(RandomSortDataGenerator.missing(builder.missing()));
        }
        if (randomBoolean()) {
            builder.unmappedType(RandomSortDataGenerator.randomAscii(builder.unmappedType()));
        }
        if (randomBoolean()) {
            builder.sortMode(RandomSortDataGenerator.mode(builder.sortMode()));
        }
        if (randomBoolean()) {
            builder.setNestedFilter(RandomSortDataGenerator.nestedFilter(builder.getNestedFilter()));
        }
        if (randomBoolean()) {
            builder.setNestedPath(RandomSortDataGenerator.randomAscii(builder.getNestedPath()));
        }
        return builder;
    }

    @Override
    protected FieldSortBuilder mutate(FieldSortBuilder original) throws IOException {
        FieldSortBuilder mutated = new FieldSortBuilder(original);
        int parameter = randomIntBetween(0, 5);
        switch(parameter) {
            case 0:
                mutated.setNestedPath(RandomSortDataGenerator.randomAscii(mutated.getNestedPath()));
                break;
            case 1:
                mutated.setNestedFilter(RandomSortDataGenerator.nestedFilter(mutated.getNestedFilter()));
                break;
            case 2:
                mutated.sortMode(RandomSortDataGenerator.mode(mutated.sortMode()));
                break;
            case 3:
                mutated.unmappedType(RandomSortDataGenerator.randomAscii(mutated.unmappedType()));
                break;
            case 4:
                mutated.missing(RandomSortDataGenerator.missing(mutated.missing()));
                break;
            case 5:
                mutated.order(RandomSortDataGenerator.order(mutated.order()));
                break;
            default:
                throw new IllegalStateException("Unsupported mutation.");
        }
        return mutated;
    }

    @Override
    protected void sortFieldAssertions(FieldSortBuilder builder, SortField sortField) throws IOException {
        SortField.Type expectedType;
        if (builder.getFieldName().equals(FieldSortBuilder.DOC_FIELD_NAME)) {
            expectedType = SortField.Type.DOC;
        } else {
            expectedType = SortField.Type.CUSTOM;
        }
        assertEquals(expectedType, sortField.getType());
        assertEquals(builder.order() == SortOrder.ASC ? false : true, sortField.getReverse());
        if (expectedType == SortField.Type.CUSTOM) {
            assertEquals(builder.getFieldName(), sortField.getField());
        }
    }

    public void testReverseOptionFails() throws IOException {
        QueryParseContext context = new QueryParseContext(indicesQueriesRegistry);
        context.parseFieldMatcher(new ParseFieldMatcher(Settings.EMPTY));
        String json = "{ \"post_date\" : {\"reverse\" : true} },\n";
        XContentParser parser = XContentFactory.xContent(json).createParser(json);
        parser.nextToken();
        parser.nextToken();
        parser.nextToken();
        context.reset(parser);
        try {
            FieldSortBuilder.PROTOTYPE.fromXContent(context, "");
            fail("adding reverse sorting option should fail with an exception");
        } catch (ParsingException e) {
        }
    }

    @Override
    protected FieldSortBuilder fromXContent(QueryParseContext context, String fieldName) throws IOException {
        return FieldSortBuilder.fromXContent(context, fieldName);
    }
}
