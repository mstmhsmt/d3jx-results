package org.elasticsearch.ingest.common;
import org.elasticsearch.ElasticsearchParseException;
import org.elasticsearch.test.ESTestCase;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.equalTo;

public class TrimProcessorFactoryTests extends ESTestCase {
  public void testCreate() throws Exception {
    TrimProcessor.Factory factory = new TrimProcessor.Factory();
    Map<String, Object> config = new HashMap<>();
    config.put("field", "field1");
    String processorTag = randomAsciiOfLength(10);
    TrimProcessor uppercaseProcessor = 
<<<<<<< commits-hd_100/elastic/elasticsearch/65c9b0b588cc233ea95c71dcd29e511dcf09698d-54904775478e58ecf827cb0013607c7a59329f0c/A.java
    factory.create(null, config)
=======
    (TrimProcessor) factory.create(processorTag, config)
>>>>>>> commits-hd_100/elastic/elasticsearch/65c9b0b588cc233ea95c71dcd29e511dcf09698d-54904775478e58ecf827cb0013607c7a59329f0c/B.java
    ;
    assertThat(uppercaseProcessor.getTag(), equalTo(processorTag));
    assertThat(uppercaseProcessor.getField(), equalTo("field1"));
  }

  public void testCreateMissingField() throws Exception {
    TrimProcessor.Factory factory = new TrimProcessor.Factory();
    Map<String, Object> config = new HashMap<>();
    try {
      factory.create(null, config);
      fail("factory create should have failed");
    } catch (ElasticsearchParseException e) {
      assertThat(e.getMessage(), equalTo("[field] required property is missing"));
    }
  }
}