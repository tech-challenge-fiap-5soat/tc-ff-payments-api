package com.tc.ff.payments_api.dataprovider.messaging;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tc.ff.payments_api.core.domain.entity.OrderEvent;
import com.tc.ff.payments_api.core.service.PaymentService;
import com.tc.ff.payments_api.entrypoint.controller.payload.request.RegisterPendingPaymentRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class OrderEventsConsumer {

    @Value("${aws.queues.OrderEventsQueue}")
    private String queueName;

    private final AmazonSQS amazonSQSClient;
    private final ObjectMapper objectMapper;

    private final PaymentService paymentService;

    public OrderEventsConsumer(AmazonSQS amazonSQSClient, ObjectMapper objectMapper, PaymentService paymentService) {
        this.amazonSQSClient = amazonSQSClient;
        this.objectMapper = objectMapper;
        this.paymentService = paymentService;
    }

    @Scheduled(fixedDelay = 5000) // It runs every 5 seconds.
    public void consumeMessages() {
        try {
            String queueUrl = amazonSQSClient.getQueueUrl(this.queueName).getQueueUrl();

            ReceiveMessageResult receiveMessageResult = amazonSQSClient.receiveMessage(queueUrl);

            if (!receiveMessageResult.getMessages().isEmpty()) {
                com.amazonaws.services.sqs.model.Message message =
                        receiveMessageResult.getMessages().get(0);

                log.info("Read Message from queue: {}", message.getBody());

                OrderEvent orderEvent = objectMapper.readValue(message.getBody(), OrderEvent.class);
                handleEvent(orderEvent);

                amazonSQSClient.deleteMessage(queueUrl, message.getReceiptHandle());
            }

        } catch (Exception e) {
            log.error("Queue Exception Message: {}", e.getMessage());
        }
    }

    private void handleEvent(OrderEvent orderEvent) {
        switch (orderEvent.getEventType()) {
            case "CreatedCheckout":
                handleCreatedCheckout(orderEvent);
                break;
            default:
                log.warn("Unhandled event type: {}", orderEvent.getEventType());
        }
    }

    private void handleCreatedCheckout(OrderEvent orderEvent) {
        log.info(
                "Handling CreatedCheckout event for order: {}",
                orderEvent.getOrder().getId());

        RegisterPendingPaymentRequest request = RegisterPendingPaymentRequest.builder()
                .orderId(orderEvent.getOrder().getId())
                .amount(orderEvent.getOrder().getAmount())
                .build();

        paymentService.create(request);
    }
}
