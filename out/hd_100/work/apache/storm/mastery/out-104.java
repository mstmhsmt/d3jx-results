package org.apache.storm.kafka.spout.builders;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import java.util.List;
import org.apache.storm.kafka.spout.KafkaSpoutTupleBuilder;
import org.apache.storm.tuple.Values;

public class TopicKeyValueTupleBuilder<K, V> extends KafkaSpoutTupleBuilder<K, V> {

    public TopicKeyValueTupleBuilder(String... topics) {
        super(topics);
    }

    @Override
    public List<Object> buildTuple(ConsumerRecord<K, V> consumerRecord) {
        return new Values(consumerRecord.topic(), consumerRecord.key(), consumerRecord.value());
    }
}
