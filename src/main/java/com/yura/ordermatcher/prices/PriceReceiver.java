package com.yura.ordermatcher.prices;

import com.yura.ordermatcher.api.prices.Price;
import com.yura.ordermatcher.orderbook.BookEntry;
import com.yura.ordermatcher.orderbook.OrderBookManager;
import org.springframework.stereotype.Service;

@Service
public class PriceReceiver {
    private final OrderBookManager orderBookManager;

    public PriceReceiver(OrderBookManager orderBookManager) {
        this.orderBookManager = orderBookManager;
    }

    public void onNewPrice(Price price) {
        orderBookManager.removeEntry(0, price.getSymbol());
        orderBookManager.removeEntry(1, price.getSymbol());
        orderBookManager.putEntry(convertToBookEntry(price, true), price.getSymbol());
        orderBookManager.putEntry(convertToBookEntry(price, false), price.getSymbol());
    }

    private BookEntry convertToBookEntry(Price price, boolean bid) {
        return new BookEntry(bid ? 0 : 1, price.getPriceForSide(bid), price.getSizeForSide(bid), bid) {
            @Override
            public void onMatch(double price, long size) {

            }
        };
    }
}
