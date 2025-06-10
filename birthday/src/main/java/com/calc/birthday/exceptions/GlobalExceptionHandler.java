package com.calc.birthday.exceptions;

import com.calc.birthday.beans.ErrorResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({FiscalCodeFormatException.class,FiscalCodeLengthException.class, FiscalCodeWrongMonthException.class})
    public ResponseEntity<ErrorResponseBean> handleFiscalCodeException(Exception ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponseBean errorResponseBean = new ErrorResponseBean(ex,request);
        errorResponseBean.setCodStatus(status.value());
        errorResponseBean.setErrorStatusDescription(status.getReasonPhrase());

        log.error(ex.getMessage());

        return ResponseEntity.status(status).body(errorResponseBean);
    }


    @ExceptionHandler({Exception.class,RuntimeException.class})
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponseBean errorResponseBean = new ErrorResponseBean(ex, request);
        errorResponseBean.setCodStatus(status.value());
        errorResponseBean.setErrorStatusDescription(status.getReasonPhrase());

        log.error(ex.getMessage());

        return ResponseEntity.status(status).body(errorResponseBean);
    }
}