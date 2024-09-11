package com.tc.ff.payments_api.dataprovider.messaging;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
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

    public SendMessageResult publishMessage(PaymentEvent paymentEvent) {
        SendMessageResult sendMessageResult = null;
        try {
            GetQueueUrlResult queueUrl = amazonSQSClient.getQueueUrl(this.queueName);

            SendMessageRequest sendMessageRequest = new SendMessageRequest()
                    .withQueueUrl(queueUrl.getQueueUrl())
                    .withMessageBody(objectMapper.writeValueAsString(paymentEvent))
                    .withDelaySeconds(10);

            sendMessageResult = amazonSQSClient.sendMessage(sendMessageRequest);
            return sendMessageResult;
        } catch (Exception e) {
            System.out.println("Queue Exception Message: " + e.getMessage());
            return sendMessageResult;
        }
    }
}
