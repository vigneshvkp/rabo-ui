package com.cts.dto;

import com.cts.util.CustomerServiceEnum;

import java.util.ArrayList;
import java.util.List;

public class TransactionValidationResult {
    private String result = CustomerServiceEnum.SUCCESSFUL.toString();
    private List<ErrorRecord> errorRecords = new ArrayList<>();


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<ErrorRecord> getErrorRecords() {
        return errorRecords;
    }

    public void setErrorRecords(List<ErrorRecord> errorRecords) {
        this.errorRecords.addAll(errorRecords);
    }
    public void appendErrorRecords(ErrorRecord errorRecord){
        errorRecords.add(errorRecord);
    }
}
