import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

public class MyKafkaConsumer {
    private String TOPIC_NAME;

    public Message sub(String topic) {
        TOPIC_NAME = topic;
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Makejob.kafkaserver);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, TOPIC_NAME);

        KafkaConsumer<String, Message> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(TOPIC_NAME));

        ArrayList<Message> messages = new ArrayList<Message>();
        Message message = null;
        try {
            while(true) {
                ConsumerRecords<String, Message> records = consumer.poll(Duration.ofMillis(100000));
                if(!records.isEmpty()) {
                    for (ConsumerRecord<String, Message> record : records) {
                        messages.add(record.value());
                    }
                    message = messages.get(0);
                    for(int i=0 ;i<messages.size() ; i++)
                    {
                        message.messagelist.addAll(messages.get(i).messagelist);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            // exception
        } finally {
            consumer.close();
        }
        return message;
    }
}
