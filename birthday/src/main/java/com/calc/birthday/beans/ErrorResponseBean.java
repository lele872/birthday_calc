package com.calc.birthday.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ErrorResponseBean {

    public ErrorResponseBean(Exception ex, WebRequest request) {
        this.timestamp = LocalDate.now();
        this.exceptionDescription = ex.getMessage();
        this.path = request.getDescription(false).replace("uri=", "");
    }

    private Object timestamp;

    private Integer codStatus;

    private String errorStatusDescription;

    private String exceptionDescription;

    private String path;

}
