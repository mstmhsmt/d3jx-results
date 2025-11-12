package org.elasticsearch.index.query;
import org.apache.lucene.util.BytesRef;
import org.elasticsearch.common.ParsingException;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.xcontent.XContentParser;
import java.io.IOException;

/**
 * Parser for type query
 */
public class TypeQueryParser extends BaseQueryParser<TypeQueryBuilder> {
  @Override public String[] names() {
    return new String[] { TypeQueryBuilder.NAME };
  }

  @Override public TypeQueryBuilder fromXContent(QueryParseContext parseContext) throws IOException, QueryParsingException {
    XContentParser parser = parseContext.parser();
    BytesRef type = null;
    String queryName = null;
    float boost = AbstractQueryBuilder.DEFAULT_BOOST;
    String currentFieldName = null;
    XContentParser.Token token;
    while ((token = parser.nextToken()) != XContentParser.Token.END_OBJECT) {
      if (token == XContentParser.Token.FIELD_NAME) {
        currentFieldName = parser.currentName();
      } else {
        if (token.isValue()) {
          if ("_name".equals(currentFieldName)) {
            queryName = parser.text();
          } else {
            if ("boost".equals(currentFieldName)) {
              boost = parser.floatValue();
            } else {
              if ("value".equals(currentFieldName)) {
                type = parser.utf8Bytes();
              }
            }
          }
        } else {
          throw new QueryParsingException(parseContext, "[type] filter doesn\'t support [" + currentFieldName + "]");
        }
      }
    }
    if (type == null) {
      throw new QueryParsingException(parseContext, "[type] filter needs to be provided with a value for the type");
    }
    return new TypeQueryBuilder(type).boost(boost).queryName(queryName);
  }


<<<<<<< Unknown file: This is a bug in JDime.
=======
  @Override public Query parse(QueryParseContext parseContext) throws IOException, ParsingException {
    XContentParser parser = parseContext.parser();
    XContentParser.Token token = parser.nextToken();
    if (token != XContentParser.Token.FIELD_NAME) {
      throw new ParsingException(parseContext, "[type] filter should have a value field, and the type name");
    }
    String fieldName = parser.currentName();
    if (!fieldName.equals("value")) {
      throw new ParsingException(parseContext, "[type] filter should have a value field, and the type name");
    }
    token = parser.nextToken();
    if (token != XContentParser.Token.VALUE_STRING) {
      throw new ParsingException(parseContext, "[type] filter should have a value field, and the type name");
    }
    BytesRef type = parser.utf8Bytes();
    parser.nextToken();
    Query filter;
    DocumentMapper documentMapper = parseContext.mapperService().documentMapper(type.utf8ToString());
    if (documentMapper == null) {
      filter = new TermQuery(new Term(TypeFieldMapper.NAME, type));
    } else {
      filter = documentMapper.typeFilter();
    }
    return filter;
  }
>>>>>>> commits-rmx_100/elasticsearch/elasticsearch/c8d1f7aa673e20a8806826b86b348dd34f9b4864/TypeQueryParser-cf834be.java


  @Override public TypeQueryBuilder getBuilderPrototype() {
    return TypeQueryBuilder.PROTOTYPE;
  }
}