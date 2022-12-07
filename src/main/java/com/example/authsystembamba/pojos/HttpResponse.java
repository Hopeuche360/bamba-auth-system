package com.example.authsystembamba.pojos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class HttpResponse {
    private Date timestamp = new Date();
    private int statusCode;
    private HttpStatus httpStatus;
    private String reason;
    private String message;

    public HttpResponse(int statusCode, HttpStatus httpStatus, String reason, String message) {
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.message = message;
    }
}
