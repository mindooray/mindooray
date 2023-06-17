package com.nhnacademy.team4.mindooray.global.exception;

public class DuplicateAccountException extends RuntimeException {
    public DuplicateAccountException(String msg) {
        super(msg);
    }
}
