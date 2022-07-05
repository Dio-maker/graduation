package com.example.covid.config;


import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrap_servers;
    @Value("${spring.kafka.producer.retries}")
    private String retries;
    @Value("${spring.kafka.producer.batch-size}")
    private String batch_size;
    @Value("${spring.kafka.producer.buffer-memory}")
    private String buffer_memory;
    @Value("${spring.kafka.producer.key-serializer}")
    private String key_serializer;
    @Value("${spring.kafka.producer.value-serializer}")
    private String value_serializer;
    @Value("${spring.kafka.producer.acks}")
    private String acks;
    @Bean
    public KafkaTemplate getKafkaTemplate(){
        Map<String, String> config= new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrap_servers);
        config.put(ProducerConfig.RETRIES_CONFIG,retries);
        config.put(ProducerConfig.BATCH_SIZE_CONFIG,batch_size);
        config.put(ProducerConfig.BUFFER_MEMORY_CONFIG,buffer_memory);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, key_serializer);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,value_serializer);
        config.put(ProducerConfig.ACKS_CONFIG,acks);
        DefaultKafkaProducerFactory producerFactory = new DefaultKafkaProducerFactory(config);
        KafkaTemplate kafkaTemplate = new KafkaTemplate(producerFactory);
        return kafkaTemplate;
    }
}
