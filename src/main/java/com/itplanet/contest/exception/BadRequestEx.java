package com.itplanet.contest.exception;

public class BadRequestEx extends RuntimeException{

    public BadRequestEx() {
        super("Some msg");
    }

    public BadRequestEx(String msg) {
        super(msg);
    }
}
