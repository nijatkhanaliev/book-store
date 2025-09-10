package com.bookstore.exception;

public class AlreadyExpired extends RuntimeException {
    public AlreadyExpired(String message) {
        super(message);
    }
}
