package org.elasticsearch.search.aggregations.metrics;

import java.util.Map;
import org.elasticsearch.search.aggregations.metrics.geobounds.GeoBoundsAggregator.GeoBoundsAggregatorBuilder;
import org.elasticsearch.search.aggregations.metrics.geobounds.GeoBoundsAggregator;
import org.elasticsearch.search.aggregations.BaseAggregationTestCase;

public class GeoBoundsTests extends BaseAggregationTestCase<GeoBoundsAggregator.GeoBoundsAggregatorBuilder> {

    



    @Override
    protected GeoBoundsAggregatorBuilder createTestAggregatorBuilder() {
        GeoBoundsAggregatorBuilder factory = new GeoBoundsAggregatorBuilder(randomAsciiOfLengthBetween(1, 20));
        String field = randomAsciiOfLengthBetween(3, 20);
        factory.field(field);
        if (randomBoolean()) {
            factory.wrapLongitude(randomBoolean());
        }
        if (randomBoolean()) {
            factory.missing("0,0");
        }
        return factory;
    }

    public B setMetaData(Map<String, Object> metaData) {
        this.metaData = metaData;
        return (B) this;
    }

    private Map<String, Object> metaData;
}
