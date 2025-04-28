package com.kyeiiih.b2cpaymentservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "USER")
    void testInitiateB2CPayment() throws Exception {
        String requestJson = """
            {
                "phoneNumber": "+254712345678",
                "amount": 100.0,
                "currency": "KES",
                "transactionReference": "TX123"
            }
            """;

        mockMvc.perform(post("/api/v1/payments/b2c")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.transactionId").value("TX123"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testInvalidPhoneNumber() throws Exception {
        String requestJson = """
            {
                "phoneNumber": "123456",
                "amount": 100.0,
                "currency": "KES",
                "transactionReference": "TX123"
            }
            """;

        mockMvc.perform(post("/api/v1/payments/b2c")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

}