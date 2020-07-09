package com.yura.ordermatcher;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yura.ordermatcher.api.orders.SubmitNewOrder;
import com.yura.ordermatcher.api.prices.Price;
import org.springframework.stereotype.Service;

@Service
public class JsonTestUtil {
    public String toJson(SubmitNewOrder submitNewOrder) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(submitNewOrder);
    }

    public String toJson(Price price) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(price);
    }
}
