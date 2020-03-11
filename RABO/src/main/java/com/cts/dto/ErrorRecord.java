package com.cts.dto;

public class ErrorRecord {
    private Long reference;
    private String accountNumber;

    public ErrorRecord (){

    }
    public ErrorRecord(Long ref, String accNumber){
        this.reference = ref;
        this.accountNumber = accNumber;
    }
    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
