package com.itplanet.contest.exception;

public class ConflictEx extends RuntimeException{

    public ConflictEx(String record) {
        super("Record already exist " + record);
    }
}
