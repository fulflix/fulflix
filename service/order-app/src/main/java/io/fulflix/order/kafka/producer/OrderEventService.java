package io.fulflix.order.kafka.producer;

import io.fulflix.order.api.dto.AdminCreateOrderRequest;
import io.fulflix.order.kafka.OrderTopic;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishOrderCreatedEvent(AdminCreateOrderRequest event) {
        kafkaTemplate.send(OrderTopic.CREATED.getTopic(), event);
    }
}
