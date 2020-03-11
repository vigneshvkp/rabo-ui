package com.cts.controller;

import com.cts.dto.TransactionValidationResult;
import com.cts.model.Transaction;
import com.cts.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @PostMapping(value = "/statement")
    public TransactionValidationResult processTransactions(@RequestBody List<Transaction> transactions) {
        logger.debug("Request for validating transaction is received");
        return transactionService.getValidationResult(transactions);
    }

}
