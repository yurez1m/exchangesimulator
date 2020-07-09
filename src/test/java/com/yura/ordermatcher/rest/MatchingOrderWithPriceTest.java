package com.yura.ordermatcher.rest;

import com.yura.ordermatcher.OrdersTestUtil;
import com.yura.ordermatcher.repositories.OrderFillRepository;
import com.yura.ordermatcher.repositories.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MatchingOrderWithPriceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrdersTestUtil ordersTestUtil;

    @MockBean
    private OrderFillRepository orderFillRepository;
    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void orderMatchingPrices() {
        
    }
}
