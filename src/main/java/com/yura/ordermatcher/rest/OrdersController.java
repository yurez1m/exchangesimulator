package com.yura.ordermatcher.rest;

import com.yura.ordermatcher.api.in.SubmitNewOrder;
import com.yura.ordermatcher.api.out.SubmitAck;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersController {

    @PostMapping("/submit")
    public SubmitAck placeOrder(SubmitNewOrder order) {
        return new SubmitAck();
    }
}
