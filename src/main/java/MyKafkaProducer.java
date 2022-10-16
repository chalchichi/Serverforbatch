import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Scanner;

public class MyKafkaProducer {
    private String TOPIC_NAME;

    public void pubMessage(Message message) {
        this.TOPIC_NAME = message.gettopic();
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Makejob.kafkaserver);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, Message> producer = new KafkaProducer<>(properties);

        System.out.print("Input > "+message);
        ProducerRecord<String, Message> record = new ProducerRecord<>(TOPIC_NAME, message);
        try {
            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    // some exception
                }
            });

        } catch (Exception e) {
            // exception
        } finally {
            producer.flush();
        }
        return;
    }
}
