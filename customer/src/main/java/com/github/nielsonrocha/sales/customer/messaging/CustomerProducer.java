package com.github.nielsonrocha.sales.customer.messaging;

import com.github.nielsonrocha.sales.customer.entity.Customer;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Component
@AllArgsConstructor
public class CustomerProducer {

    private final KafkaTemplate<String, Customer> kafkaTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(CustomerProducer.class);

    private static final String TOPIC_CUSTOMERS = "customers";

    public void send(final @RequestBody Customer customer) {
        LOG.info("sending data='{}' to topic='{}'", customer, TOPIC_CUSTOMERS);

        Message<Customer> message = MessageBuilder
                .withPayload(customer)
                .setHeader(KafkaHeaders.TOPIC, TOPIC_CUSTOMERS)
                .build();

        kafkaTemplate.send(message);
    }
}
