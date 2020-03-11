package com.cts.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/*
* A common class to handle all the exception thrown in Application
 */
@ControllerAdvice
public class ApplicationExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);


    @Autowired
    private HttpServletRequest request;

    /**
     * This method handles the when Exception occurred and returns error response
     *
     * @param exception - Exception obj
     * @return - error message
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleException(Exception exception) {
        logger.error("Error Occurred : {0}" , exception);
        String errorMessage = "";
        if (exception instanceof JsonMappingException ) {
            errorMessage = "Invalid Json format";
        }
        return this.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }

    /**
     * This method handles the when CustomException occurred and returns error response from the properties file
     *
     * @param exception - CustomException obj
     * @return - error message
     */
    @ExceptionHandler({CustomException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleException(CustomException exception) {
        logger.error("Custom Error Occurred {0} ", exception);
        String errorMessage = "Invalid Transaction Data";
        return this.buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage);
    }

    /**
     * This method is used to create error response
     *
     * @param httpStatus   - http error code
     * @param errorMessage - error message from message properties
     * @return - error response
     */
    private ErrorResponse buildErrorResponse(HttpStatus httpStatus, String errorMessage) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        errorResponse.setStatus(httpStatus.value());
        errorResponse.setError(httpStatus.getReasonPhrase());
        errorResponse.setMessage(errorMessage);
        return errorResponse;
    }

}
