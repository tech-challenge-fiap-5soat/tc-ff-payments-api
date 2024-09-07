package com.tc.ff.payments_api.dataprovider.messaging;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tc.ff.payments_api.core.domain.entity.PaymentEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventPublisher {
    @Value("${aws.queues.PaymentEventsQueue}")
    private String queueName;

    private final AmazonSQS amazonSQSClient;
    private final ObjectMapper objectMapper;

    public PaymentEventPublisher(AmazonSQS amazonSQSClient, ObjectMapper objectMapper) {
        this.amazonSQSClient = amazonSQSClient;
        this.objectMapper = objectMapper;
    }

    public void publishMessage(PaymentEvent paymentEvent) {
        try {
            GetQueueUrlResult queueUrl = amazonSQSClient.getQueueUrl(this.queueName);
            amazonSQSClient.sendMessage(queueUrl.getQueueUrl(), objectMapper.writeValueAsString(paymentEvent));
        } catch (Exception e) {
            System.out.println("Queue Exception Message: " + e.getMessage());
        }
    }
}
