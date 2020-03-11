package com.cts.service;

import com.cts.dto.TransactionValidationResult;
import com.cts.model.Transaction;

import java.util.List;


public interface TransactionService {

    TransactionValidationResult getValidationResult(List<Transaction> transactions);
}
