package com.cts.exception;

import com.cts.dto.ErrorRecord;

import java.io.Serializable;
import java.util.List;

public class CustomException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 2405172041950251807L;

    private List<ErrorRecord> data;
    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException( String message, List<ErrorRecord> data) {
        super(message);
        this.data = data;
    }

    public CustomException( String message, String response) {
        super(message);
        this.response = response;
    }


    public String getErrorMessage() {
        return null != response ? response : getMessage();
    }
}
