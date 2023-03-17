package com.itplanet.contest.exception;

public class ForbiddenEx extends RuntimeException{

    public ForbiddenEx() {
        super("Forbidden 403");
    }
}
