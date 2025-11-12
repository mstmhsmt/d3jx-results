package org.elasticsearch.index.query;
import com.google.common.collect.Lists;
import org.elasticsearch.common.xcontent.XContentBuilder;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A filter that matches documents matching boolean combinations of other filters.
 * @deprecated Use {@link BoolFilterBuilder} instead
 */
@Deprecated public class OrFilterBuilder extends BaseFilterBuilder {
  private ArrayList<FilterBuilder> filters = Lists.newArrayList();

  private String filterName;

  public OrFilterBuilder(FilterBuilder... filters) {
    for (FilterBuilder filter : filters) {
      this.filters.add(filter);
    }
  }

  /**
     * Adds a filter to the list of filters to "or".
     */
  public OrFilterBuilder add(FilterBuilder filterBuilder) {
    filters.add(filterBuilder);
    return this;
  }

  public OrFilterBuilder filterName(String filterName) {
    this.filterName = filterName;
    return this;
  }

  @Override protected void doXContent(XContentBuilder builder, Params params) throws IOException {
    builder.startObject(OrFilterParser.NAME);
    builder.startArray("filters");
    for (FilterBuilder filter : filters) {
      filter.toXContent(builder, params);
    }
    builder.endArray();
    if (filterName != null) {
      builder.field("_name", filterName);
    }
    builder.endObject();
  }

  @Override protected String parserName() {
    return OrFilterParser.NAME;
  }
}