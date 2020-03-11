package com.cts.controller;


import static org.mockito.ArgumentMatchers.any;

import com.cts.RaboApplication;
import com.cts.common.JunitCommon;
import com.cts.dto.TransactionValidationResult;
import com.cts.service.TransactionService;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product Controller Test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = RaboApplication.class)
public class TransactionControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(TransactionControllerTest.class);
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    TransactionService transactionService;

    String transactionDatas;
    JunitCommon junitCommon = new JunitCommon();

    @Before
    public void setUp() throws Exception {
        transactionDatas = junitCommon.getTransactionList(JunitCommon.SUCCESS_RECORD_FILE);
    }

    @Test
    @DisplayName("TC-001 : Description - Should Accept the Transactions And produce 200 response")
    public void processTransactions() throws Exception{
        try {
            MvcResult result = null;
            MockHttpServletResponse response = null;
            RequestBuilder requestBuilder = null;

            TransactionValidationResult transactionValidationResult = new TransactionValidationResult();
            Mockito.lenient().when(transactionService.getValidationResult(any())).thenReturn(transactionValidationResult);

            requestBuilder = MockMvcRequestBuilders.post(JunitCommon.TRANSACTION_URL)
                    .content(transactionDatas)
                    .contentType(MediaType.APPLICATION_JSON);

            result = mockMvc.perform(requestBuilder).andReturn();
            response = result.getResponse();
            assertEquals(200, response.getStatus());

        } catch (Exception e) {
            logger.error("exception {} ", e);
        }
    }
}
