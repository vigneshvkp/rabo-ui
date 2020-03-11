package com.cts.service;

import com.cts.dto.ErrorRecord;
import com.cts.dto.TransactionValidationResult;
import com.cts.exception.CustomException;
import com.cts.model.Transaction;
import com.cts.util.CustomerServiceEnum;
import com.cts.util.TransactionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/*
 * This class will responsible for iteration all the transactions and proudce the response data, or throw the exception
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private TransactionValidator validator = new TransactionValidator();
    private TransactionValidationResult transactionValidationResult;
    private boolean isDuplicateFound = false;
    private boolean isInCorrectBalanceFound = false;

    /*
    * @param transaction - This object contains invalid data (duplicate/ incorrect balance)
    * Add the error object into a separate list
     */
    private void addToErrorList(Transaction transaction) {
        List<ErrorRecord> errorRecords = transactionValidationResult.getErrorRecords();
        boolean isErrorRecordAlreadyExists = errorRecords.stream().anyMatch(errorRecord -> errorRecord.getReference().equals(transaction.getReference()));
        if (!isErrorRecordAlreadyExists) {
            transactionValidationResult.appendErrorRecords(new ErrorRecord(transaction.getReference(), transaction.getAccountNumber()));
        } else {
            logger.debug("An error record is already present with same reference number so leaving an error record to avoid duplication");
        }
    }

    /*
     * @param transactionMap - contains transaction number as key and corresponding objects as value
     * Iterate through the entire list and mark if it has any duplicate record or inccorrect balance
     */

    private void findErrorRecords(Map<Long, List<Transaction>> transactionMap){
        for (List<Transaction> transactionList : transactionMap.values()) {
            // there should be only one item if there is no duplicate
            // or there will be many items if there is duplicates. but it will contain atleast one record
            Transaction transaction = transactionList.get(0);

            if (transactionList.size() > 1) {
                isDuplicateFound = true;
                addToErrorList(transaction);
            }

            if (!validator.isValidEndBalance(transaction)) {
                isInCorrectBalanceFound = true;
                addToErrorList(transaction);
            }

        }
        logger.debug("Successfully iterated the transaction details and producing the result");
    }


    /*
     * @param transactionList : It's the input data
     * To iterate throught the entire list and mark the errors and generate the response data.
     */
    @Override
    public TransactionValidationResult getValidationResult(List<Transaction> transactions) {
        // clearing the flag value while getting a new api request
        isDuplicateFound = false;
        isInCorrectBalanceFound = false;
        transactionValidationResult = new TransactionValidationResult();
        TransactionValidationResult response = new TransactionValidationResult();
        checkEmptyTransaction(transactions, response);

        Map<Long, List<Transaction>> transactionMap = validator.convertTransactionListToMap(transactions);
        findErrorRecords(transactionMap);
        makeResultData();
        return transactionValidationResult;
    }

    /*
     * @param transactions - input data, contains all the transactions.
     * @param response - A local variable to set the error response and add to custom Exception
     */
    private void checkEmptyTransaction(List<Transaction> transactions, TransactionValidationResult response) {
        if (transactions.isEmpty()) {
            logger.debug("Empty request received for transaction validation");
            response.setResult(CustomerServiceEnum.BAD_REQUEST.toString());
            response.setErrorRecords(new ArrayList<>());
            throw new CustomException(response.getResult() ,response.getErrorRecords());
        }
    }

    /*
     * Check the flag and product the Result with appropiate value.
     */
    private void makeResultData() {
        if (isDuplicateFound && isInCorrectBalanceFound) {
            transactionValidationResult.setResult("DUPLICATE_REFERENCE_INCORRECT_END_BALANCE");
        } else if (isDuplicateFound) {
            transactionValidationResult.setResult("DUPLICATE_REFERENCE");
        } else if (isInCorrectBalanceFound) {
            transactionValidationResult.setResult("INCORRECT_END_BALANCE");
        }

        if(!isDuplicateFound && !isInCorrectBalanceFound){
            transactionValidationResult.setResult("SUCCESSFUL");
            transactionValidationResult.setErrorRecords(new ArrayList<>());
        }
    }

}
