package com.cts.service;

import com.cts.RaboApplication;
import com.cts.common.JunitCommon;
import com.cts.dto.ErrorRecord;
import com.cts.dto.TransactionValidationResult;
import com.cts.exception.CustomException;
import com.cts.model.Transaction;
import com.cts.util.TransactionValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@DisplayName("Transaction Service Test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = RaboApplication.class)
public class TransactionServiceImplTest {

    String transactionDatas;
    TransactionValidator validator;
    ObjectMapper mapper = new ObjectMapper();
    TransactionService transactionService;
    JunitCommon junitCommon = new JunitCommon();
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImplTest.class);

    @Before
    public void setUp() throws Exception {
        validator = new TransactionValidator();
        transactionService = new TransactionServiceImpl();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @DisplayName("TC-002 : Scenario-Should return Successfull message")
    public void getSuccessResult() throws Exception{
        transactionDatas = junitCommon.getTransactionList(JunitCommon.SUCCESS_RECORD_FILE);
        List<Transaction> transactionList = JunitCommon.convertTransactionsFromList(transactionDatas);
        TransactionValidationResult transactionValidationResult = transactionService.getValidationResult(transactionList);
        List<ErrorRecord> errorRecords = transactionValidationResult.getErrorRecords();
        assertEquals("SUCCESSFUL", transactionValidationResult.getResult());
    }


    // As per the given json input there will be one duplicate error record
    @Test
    @DisplayName("TC-003 : Scenario-Should return Duplicate message")
    public void getDuplicationResult() throws Exception{
        transactionDatas = junitCommon.getTransactionList(JunitCommon.DUPLICATE_RECORD_FILE);
        List<Transaction> transactionList = JunitCommon.convertTransactionsFromList(transactionDatas);
        TransactionValidationResult transactionValidationResult = transactionService.getValidationResult(transactionList);
        List<ErrorRecord> errorRecords = transactionValidationResult.getErrorRecords();
        assertEquals("DUPLICATE_REFERENCE", transactionValidationResult.getResult());
    }

    @Test
    @DisplayName("TC-002 : Scenario-Should return Duplicate and incorrect error message")
    public void getDuplicateAndIncorrectBalanceResult() throws Exception{
        transactionDatas = junitCommon.getTransactionList(JunitCommon.DUPLICATE_INCORRECTBALANCE_RECORD_FILE);
        List<Transaction> transactionList = JunitCommon.convertTransactionsFromList(transactionDatas);
        TransactionValidationResult transactionValidationResult = transactionService.getValidationResult(transactionList);
        List<ErrorRecord> errorRecords = transactionValidationResult.getErrorRecords();
        assertEquals("DUPLICATE_REFERENCE_INCORRECT_END_BALANCE", transactionValidationResult.getResult());
    }

    @Test(expected = CustomException.class)
    @DisplayName("TC-002 : Scenario- Should throw bad request")
    public void handleEmptyTransactionList() throws Exception{
        List<Transaction> transactionList = new ArrayList<>();
        TransactionValidationResult transactionValidationResult = transactionService.getValidationResult(transactionList);
    }

    @Test(expected = Exception.class)
    @DisplayName("TC-002 : Scenario- Should throw internal Server Exception on invalid data")
    public void handleInternalServerException() throws Exception{
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Transaction());
        TransactionValidationResult transactionValidationResult = transactionService.getValidationResult(transactionList);    }
}
