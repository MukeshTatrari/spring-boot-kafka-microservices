package com.order.api.controller;

import com.domain.api.dto.Order;
import com.domain.api.dto.OrderEvent;
import com.order.api.kafka.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("order/api/v1")
public class OrderController {

    @Autowired
    private OrderProducer orderProducer;

    @PostMapping("/placeOrder")
    public String placeOrder(@RequestBody Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrder(order);
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("order status is pending state");
        orderProducer.sendMessage(orderEvent);

        return "Successfully placed order ";
    }
}
