package com.yura.ordermatcher.rest;

import com.yura.ordermatcher.OrdersTestUtil;
import com.yura.ordermatcher.api.orders.OrderPriceType;
import com.yura.ordermatcher.api.orders.SubmitNewOrder;
import com.yura.ordermatcher.repositories.OrderFillRepository;
import com.yura.ordermatcher.repositories.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrdersTestUtil ordersTestUtil;

    @MockBean
    private OrderFillRepository orderFillRepository;
    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void testSubmitOrder() throws Exception {
        SubmitNewOrder submitNewOrder = new SubmitNewOrder();
        submitNewOrder.setBid(true);
        submitNewOrder.setClientOrderId("myOrder");
        submitNewOrder.setPrice(1.0002);
        submitNewOrder.setSize(999);
        submitNewOrder.setSymbol("XXX/YYY");
        submitNewOrder.setOrderPriceType(OrderPriceType.LIMIT);
        ResultActions resultActions = this.mockMvc
                .perform(post("/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ordersTestUtil.toJson(submitNewOrder)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("\"clientOrderId\":\"myOrder\"}")));
    }

}