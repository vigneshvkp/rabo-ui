package com.cts.util;

import com.cts.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class TransactionValidator {

    private static final Logger logger = LoggerFactory.getLogger(TransactionValidator.class);

    // convert transaction list to map with transaction number as key and trasaction objects as value
    // This will help to find duplicates without many iterations.
    public Map<Long, List<Transaction>> convertTransactionListToMap(List<Transaction> transactionList) {
        Map<Long, List<Transaction>> transactionMap = new HashMap<>();
        for (Transaction transaction : transactionList) {
            List<Transaction> transactionList1 = transactionMap.get(transaction.getReference());
            if (transactionList1 == null) {
                transactionList1 = new ArrayList<>();
                transactionList1.add(transaction);
                transactionMap.put(transaction.getReference(), transactionList1);
            } else {
                transactionList1.add(transaction);
            }
        }
        logger.debug("Successfully converted Transaction List to map");
        return transactionMap;
    }

    public boolean isValidEndBalance(Transaction transaction) {
        return (transaction.getStartBalance().add(transaction.getMutation())).compareTo(transaction.getEndBalance()) == 0;
    }
}
