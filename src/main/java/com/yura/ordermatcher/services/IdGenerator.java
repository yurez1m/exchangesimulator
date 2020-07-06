package com.yura.ordermatcher.services;

import org.springframework.stereotype.Service;

@Service
public class IdGenerator {
    private long id = 1;

    public long getNextId() {
        return id++;
    }
}
