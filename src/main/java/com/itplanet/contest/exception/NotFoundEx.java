package com.itplanet.contest.exception;

public class NotFoundEx extends RuntimeException{

    public NotFoundEx(Long id) {
        super(String.format("Record with id %d not found.", id));
    }

    public NotFoundEx(String cause) {
        super(String.format(cause));
    }
}
