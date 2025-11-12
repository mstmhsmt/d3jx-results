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


<<<<<<< Unknown file: This is a bug in JDime.
=======
/**
 * Base builder for metrics aggregations.
 */
public abstract class MetricsAggregationBuilder<B extends MetricsAggregationBuilder<B>> extends AbstractAggregationBuilder {
  private Map<String, Object> metaData;

  public MetricsAggregationBuilder(String name, String type) {
    super(name, type);
  }

  /**
     * Sets the meta data to be included in the metric aggregator's response
     */
  public B setMetaData(Map<String, Object> metaData) {
    this.metaData = metaData;
    return (B) this;
  }

  @Override public final XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
    builder.startObject(getName());
    if (this.metaData != null) {
      builder.field("meta", this.metaData);
    }
    builder.startObject(type);
    internalXContent(builder, params);
    return builder.endObject().endObject();
  }

  protected abstract void internalXContent(XContentBuilder builder, Params params) throws IOException;
}
>>>>>>> commits-hd_100/elastic/elasticsearch/ed3f7903f47d1894bbe9ae74e25ec00eec361c1c-56ae24bbd7309917dbd44ef19417a66f4257b3d3/B.java
