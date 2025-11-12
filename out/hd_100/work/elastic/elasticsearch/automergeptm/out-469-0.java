package org.elasticsearch.search.aggregations.metrics;
import org.elasticsearch.search.aggregations.BaseAggregationTestCase;
import org.elasticsearch.search.aggregations.metrics.geobounds.GeoBoundsAggregator;
import org.elasticsearch.search.aggregations.metrics.geobounds.GeoBoundsAggregator.GeoBoundsAggregatorBuilder;
import java.util.Map;

public class GeoBoundsTests extends BaseAggregationTestCase<GeoBoundsAggregator.GeoBoundsAggregatorBuilder> {
  @Override protected GeoBoundsAggregatorBuilder createTestAggregatorBuilder() {
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
}


