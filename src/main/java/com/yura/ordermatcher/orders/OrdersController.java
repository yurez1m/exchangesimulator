package com.yura.ordermatcher.orders;

import com.yura.ordermatcher.api.orders.CancelOrder;
import com.yura.ordermatcher.api.orders.SubmitNewOrder;
import com.yura.ordermatcher.api.orders.SubmitAck;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class OrdersController {

    private final OrderFactory orderFactory;
    private final OrderManager orderManager;

    public OrdersController(OrderFactory orderFactory, OrderManager orderManager) {
        this.orderFactory = orderFactory;
        this.orderManager = orderManager;
    }

    @PostMapping("/submit")
    public SubmitAck placeOrder(@RequestBody SubmitNewOrder submitCommand) {
        Order order = orderFactory.fromSubmit(submitCommand);
        orderManager.placeOrder(order);
        return new SubmitAck(order.getExchangeOrderId(), submitCommand.getClientOrderId());
    }

    @PostMapping("/cancel")
    public void cancelOrder(CancelOrder cancelOrder) {
        orderManager.cancelOrder(cancelOrder.getClientOrderId());
    }

    @GetMapping("/getAll")
    public Collection<Order> getAllOrders() {
        return orderManager.getAllOrders();
    }

    @GetMapping("/getById")
    public Order getOrder(String clientOrderId) {
        return orderManager.getOrder(clientOrderId);
    }
}
