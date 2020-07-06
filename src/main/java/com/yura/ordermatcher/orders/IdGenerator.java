package com.yura.ordermatcher.orders;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class IdGenerator {
    private AtomicLong id = new AtomicLong();

    public long getNextId() {
        return id.incrementAndGet();
    }
}
