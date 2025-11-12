package org.elasticsearch.index.query;
import org.elasticsearch.common.geo.GeoHashUtils;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.xcontent.XContentBuilder;
import java.io.IOException;

public class GeoBoundingBoxQueryBuilder extends AbstractQueryBuilder<GeoBoundingBoxQueryBuilder> {
  public static final String NAME = "geo_bbox";

  public static final String TOP_LEFT = GeoBoundingBoxQueryParser.TOP_LEFT;

  public static final String BOTTOM_RIGHT = GeoBoundingBoxQueryParser.BOTTOM_RIGHT;

  private static final int TOP = 0;

  private static final int LEFT = 1;

  private static final int BOTTOM = 2;

  private static final int RIGHT = 3;

  private final String name;

  private double[] box = { Double.NaN, Double.NaN, Double.NaN, Double.NaN };

  private String type;

  static private final 
<<<<<<< commits-hd_100/elastic/elasticsearch/de54671173003750b5df2671c1ab64d1903fe8fb-9b376ca851ea7a37742de4fc5898cf5af8fd187c/A.java
  GeoBoundingBoxQueryBuilder
=======
  Boolean
>>>>>>> commits-hd_100/elastic/elasticsearch/de54671173003750b5df2671c1ab64d1903fe8fb-9b376ca851ea7a37742de4fc5898cf5af8fd187c/B.java
   
<<<<<<< commits-hd_100/elastic/elasticsearch/de54671173003750b5df2671c1ab64d1903fe8fb-9b376ca851ea7a37742de4fc5898cf5af8fd187c/A.java
  PROTOTYPE = new GeoBoundingBoxQueryBuilder(null)
=======
  coerce
>>>>>>> commits-hd_100/elastic/elasticsearch/de54671173003750b5df2671c1ab64d1903fe8fb-9b376ca851ea7a37742de4fc5898cf5af8fd187c/B.java
  ;

  private Boolean ignoreMalformed;

  public GeoBoundingBoxQueryBuilder(String name) {
    this.name = name;
  }

  /**
     * Adds top left point.
     *
     * @param lat The latitude
     * @param lon The longitude
     */
  public GeoBoundingBoxQueryBuilder topLeft(double lat, double lon) {
    box[TOP] = lat;
    box[LEFT] = lon;
    return this;
  }

  public GeoBoundingBoxQueryBuilder topLeft(GeoPoint point) {
    return topLeft(point.lat(), point.lon());
  }

  public GeoBoundingBoxQueryBuilder topLeft(String geohash) {
    return topLeft(GeoHashUtils.decode(geohash));
  }

  /**
     * Adds bottom right corner.
     *
     * @param lat The latitude
     * @param lon The longitude
     */
  public GeoBoundingBoxQueryBuilder bottomRight(double lat, double lon) {
    box[BOTTOM] = lat;
    box[RIGHT] = lon;
    return this;
  }

  public GeoBoundingBoxQueryBuilder bottomRight(GeoPoint point) {
    return bottomRight(point.lat(), point.lon());
  }

  public GeoBoundingBoxQueryBuilder bottomRight(String geohash) {
    return bottomRight(GeoHashUtils.decode(geohash));
  }

  /**
     * Adds bottom left corner.
     *
     * @param lat The latitude
     * @param lon The longitude
     */
  public GeoBoundingBoxQueryBuilder bottomLeft(double lat, double lon) {
    box[BOTTOM] = lat;
    box[LEFT] = lon;
    return this;
  }

  public GeoBoundingBoxQueryBuilder bottomLeft(GeoPoint point) {
    return bottomLeft(point.lat(), point.lon());
  }

  public GeoBoundingBoxQueryBuilder bottomLeft(String geohash) {
    return bottomLeft(GeoHashUtils.decode(geohash));
  }

  /**
     * Adds top right point.
     *
     * @param lat The latitude
     * @param lon The longitude
     */
  public GeoBoundingBoxQueryBuilder topRight(double lat, double lon) {
    box[TOP] = lat;
    box[RIGHT] = lon;
    return this;
  }

  public GeoBoundingBoxQueryBuilder topRight(GeoPoint point) {
    return topRight(point.lat(), point.lon());
  }

  public GeoBoundingBoxQueryBuilder topRight(String geohash) {
    return topRight(GeoHashUtils.decode(geohash));
  }

  public GeoBoundingBoxQueryBuilder coerce(boolean coerce) {
    this.coerce = coerce;
    return this;
  }

  public GeoBoundingBoxQueryBuilder ignoreMalformed(boolean ignoreMalformed) {
    this.ignoreMalformed = ignoreMalformed;
    return this;
  }

  /**
     * Sets the type of executing of the geo bounding box. Can be either `memory` or `indexed`. Defaults
     * to `memory`.
     */
  public GeoBoundingBoxQueryBuilder type(String type) {
    this.type = type;
    return this;
  }

  @Override protected void doXContent(XContentBuilder builder, Params params) throws IOException {
    if (Double.isNaN(box[TOP])) {
      throw new IllegalArgumentException("geo_bounding_box requires top latitude to be set");
    } else {
      if (Double.isNaN(box[BOTTOM])) {
        throw new IllegalArgumentException("geo_bounding_box requires bottom latitude to be set");
      } else {
        if (Double.isNaN(box[RIGHT])) {
          throw new IllegalArgumentException("geo_bounding_box requires right longitude to be set");
        } else {
          if (Double.isNaN(box[LEFT])) {
            throw new IllegalArgumentException("geo_bounding_box requires left longitude to be set");
          }
        }
      }
    }
    builder.startObject(NAME);
    builder.startObject(name);
    builder.array(TOP_LEFT, box[LEFT], box[TOP]);
    builder.array(BOTTOM_RIGHT, box[RIGHT], box[BOTTOM]);
    builder.endObject();
    if (type != null) {
      builder.field("type", type);
    }

<<<<<<< commits-hd_100/elastic/elasticsearch/de54671173003750b5df2671c1ab64d1903fe8fb-9b376ca851ea7a37742de4fc5898cf5af8fd187c/A.java
    printBoostAndQueryName(builder);
=======
    if (coerce != null) {
      builder.field("coerce", coerce);
    }
>>>>>>> commits-hd_100/elastic/elasticsearch/de54671173003750b5df2671c1ab64d1903fe8fb-9b376ca851ea7a37742de4fc5898cf5af8fd187c/B.java

    if (ignoreMalformed != null) {
      builder.field("ignore_malformed", ignoreMalformed);
    }
    builder.endObject();
  }

  @Override public String getWriteableName() {
    return NAME;
  }
}