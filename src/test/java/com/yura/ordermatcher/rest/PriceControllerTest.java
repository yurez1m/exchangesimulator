package com.yura.ordermatcher.rest;

import com.yura.ordermatcher.JsonTestUtil;
import com.yura.ordermatcher.api.prices.Price;
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

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderFillRepository orderFillRepository;
    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    JsonTestUtil jsonTestUtil;

    @Test
    public void testSubmitPrice() throws Exception {
        Price price = new Price("SYMBOL_TEST", 1.001, 1000, 2.002, 2000);
        ResultActions resultActions = this.mockMvc
                .perform(post("/newPrice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTestUtil.toJson(price)))
                .andExpect(status().isOk());
    }

}