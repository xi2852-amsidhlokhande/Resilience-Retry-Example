package com.amsidh.mvc.exception;


import java.io.Serializable;

public class Service2NotFound extends RuntimeException implements Serializable {
    public Service2NotFound(String message) {
        super(message);
    }
}
