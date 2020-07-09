package com.yura.ordermatcher.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yura.ordermatcher.JsonTestUtil;
import com.yura.ordermatcher.api.orders.OrderType;
import com.yura.ordermatcher.api.orders.SubmitNewOrder;
import com.yura.ordermatcher.api.prices.Price;
import com.yura.ordermatcher.orders.Order;
import com.yura.ordermatcher.orders.OrderManager;
import com.yura.ordermatcher.orders.OrderStatus;
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

import java.util.ArrayList;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private JsonTestUtil jsonTestUtil;

    @MockBean
    private OrderFillRepository orderFillRepository;
    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderManager orderManager;

    @Test
    public void testSubmitOrder() throws Exception {
        SubmitNewOrder submitNewOrder = submitNewOrderAndCheckResponse("myOrder", true, 1.0002, 999, "XXX/YYY");


        ResultActions resultActions = this.mockMvc
                .perform(get("/getAll")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        String json = resultActions.andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        OrdersList allOrders = mapper.readValue(json, OrdersList.class);

        Order receivedOrder = allOrders.get(0);

        assertEquals(1, allOrders.size());

        assertSubmitEqualsOrder(submitNewOrder, receivedOrder);
    }

    @Test
    public void testGetOrderByClientId() throws Exception {
        SubmitNewOrder submitNewOrder1 = submitNewOrderAndCheckResponse("myOrder1", true, 1.0002, 999, "XXX/YYY");
        SubmitNewOrder submitNewOrder2 = submitNewOrderAndCheckResponse("myOrder2", true, 2.0002, 111, "XXX/YYY");

        ResultActions resultActions = this.mockMvc
                .perform(get("/getById").param("clientOrderId", "myOrder2")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        String json = resultActions.andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        Order receivedOrder = mapper.readValue(json, Order.class);

        assertSubmitEqualsOrder(submitNewOrder2, receivedOrder);
    }

    @Test
    public void testOrdersMatchesWithPrice() throws Exception {
        SubmitNewOrder submitNewOrder = submitNewOrderAndCheckResponse("myOrder1", true, 1.0002, 999, "XXX/YYY");
        Price price = new Price("XXX/YYY", 1.001, 1000, 2.002, 2000);
        this.mockMvc.perform(post("/newPrice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestUtil.toJson(price)));

        ResultActions resultActions = this.mockMvc
                .perform(get("/getById").param("clientOrderId", "myOrder1")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        String json = resultActions.andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        Order receivedOrder = mapper.readValue(json, Order.class);
        assertEquals(999, receivedOrder.getLeftSize());
        assertEquals(OrderStatus.NEW, receivedOrder.getOrderStatus());

        price = new Price("XXX/YYY", 1.0001, 1000, 1.0002, 500);
        this.mockMvc.perform(post("/newPrice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestUtil.toJson(price)));

        resultActions = this.mockMvc
                .perform(get("/getById").param("clientOrderId", "myOrder1")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        json = resultActions.andReturn().getResponse().getContentAsString();

        mapper = new ObjectMapper();
        receivedOrder = mapper.readValue(json, Order.class);
        assertEquals(499, receivedOrder.getLeftSize());
        assertEquals(OrderStatus.PARTIALLY_FILLED, receivedOrder.getOrderStatus());
    }

    private SubmitNewOrder submitNewOrderAndCheckResponse(String clientOrderId, boolean bid, double price, int size, String symbol) throws Exception {
        SubmitNewOrder submitNewOrder = new SubmitNewOrder();
        submitNewOrder.setBid(bid);
        submitNewOrder.setClientOrderId(clientOrderId);
        submitNewOrder.setPrice(price);
        submitNewOrder.setSize(size);
        submitNewOrder.setSymbol(symbol);
        submitNewOrder.setOrderType(OrderType.LIMIT);
        this.mockMvc
                .perform(post("/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTestUtil.toJson(submitNewOrder)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("\"clientOrderId\":\"" + submitNewOrder.getClientOrderId() + "\"}")));
        return submitNewOrder;
    }

    private void assertSubmitEqualsOrder(SubmitNewOrder submitNewOrder, Order receivedOrder) {
        assertEquals(submitNewOrder.getClientOrderId(), receivedOrder.getClientOrderId());
        assertEquals(submitNewOrder.isBid(), receivedOrder.isBid());
        assertEquals(submitNewOrder.getPrice(), receivedOrder.getPrice(), 0.000001);
        assertEquals(submitNewOrder.getOrderType(), receivedOrder.getOrderType());
        assertEquals(submitNewOrder.getSymbol(), receivedOrder.getSymbol());
    }

    static class OrdersList extends ArrayList<Order> {
    }
}