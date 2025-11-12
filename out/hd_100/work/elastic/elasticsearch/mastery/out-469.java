package org.elasticsearch.search.aggregations.metrics;

import java.util.Map;
import org.elasticsearch.search.aggregations.metrics.geobounds.GeoBoundsAggregator.GeoBoundsAggregatorBuilder;
import org.elasticsearch.search.aggregations.metrics.geobounds.GeoBoundsAggregator;
import org.elasticsearch.search.aggregations.BaseAggregationTestCase;

public class GeoBoundsTests extends BaseAggregationTestCase<GeoBoundsAggregator.GeoBoundsAggregatorBuilder> {

    
<<<<<<< commits-hd_100/elastic/elasticsearch/ed3f7903f47d1894bbe9ae74e25ec00eec361c1c-56ae24bbd7309917dbd44ef19417a66f4257b3d3/A.java

=======
@Override
    public final XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject(getName());
        if (this.metaData != null) {
            builder.field("meta", this.metaData);
        }
        builder.startObject(type);
        internalXContent(builder, params);
        return builder.endObject().endObject();
    }
>>>>>>> commits-hd_100/elastic/elasticsearch/ed3f7903f47d1894bbe9ae74e25ec00eec361c1c-56ae24bbd7309917dbd44ef19417a66f4257b3d3/B.java


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
