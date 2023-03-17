package com.itplanet.contest.advice;

import com.itplanet.contest.exception.ForbiddenEx;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ForbiddenAdvice {

    @ResponseBody
    @ExceptionHandler(ForbiddenEx.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected String forbiddenExHandler(ForbiddenEx ex) {
        return ex.getMessage();
    }
}
