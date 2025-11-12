package org.apache.storm.kafka.spout.builders;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.storm.kafka.spout.KafkaSpoutTupleBuilder;
import org.apache.storm.tuple.Values;
import java.util.List;

public class TopicKeyValueTupleBuilder<K extends java.lang.Object, V extends java.lang.Object> extends KafkaSpoutTupleBuilder<K, V> {
  /**
     * @param topics list of topics that use this implementation to build tuples
     */
  public TopicKeyValueTupleBuilder(String... topics) {
    super(topics);
  }

  @Override public List<Object> buildTuple(ConsumerRecord<K, V> consumerRecord) {
    return new Values(consumerRecord.topic(), consumerRecord.key(), consumerRecord.value());
  }
}