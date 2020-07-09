package com.yura.ordermatcher.prices;

import com.yura.ordermatcher.api.prices.Price;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {

    private final PriceReceiver priceReceiver;

    public PriceController(PriceReceiver priceReceiver) {
        this.priceReceiver = priceReceiver;
    }

    @PostMapping("/newPrice")
    public void newPrice(@RequestBody Price price) {
        priceReceiver.onNewPrice(price);
    }
}
