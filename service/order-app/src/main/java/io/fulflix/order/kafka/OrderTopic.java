package io.fulflix.order.kafka;

import lombok.Getter;

@Getter
public enum OrderTopic {
    CREATED("order-created");

    private final String topic;

    OrderTopic(String topic) {
        this.topic = topic;
    }
}
