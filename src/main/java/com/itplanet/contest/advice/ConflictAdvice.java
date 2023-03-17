package com.itplanet.contest.advice;

import com.itplanet.contest.exception.ConflictEx;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ConflictAdvice {

    @ResponseBody
    @ExceptionHandler(ConflictEx.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected String conflictExHandler(ConflictEx ex) {
        return ex.getMessage();
    }
}
