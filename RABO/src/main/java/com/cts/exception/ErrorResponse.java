package com.cts.exception;

import java.sql.Timestamp;

public class ErrorResponse {

    private Timestamp timestamp;
    private Integer status;
    private String error;
    private String message;

    public ErrorResponse() {

    }

    public ErrorResponse(Timestamp timestamp, String error, Integer status, String message) {
        this.timestamp = timestamp;
        this.error = error;
        this.status = status;
        this.message = message;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }


    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setError(String error) {
        this.error = error;
    }


    public void setMessage(String message) {
        this.message = message;
    }
}
