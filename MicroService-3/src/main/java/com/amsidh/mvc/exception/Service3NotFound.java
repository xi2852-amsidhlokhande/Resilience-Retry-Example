package com.amsidh.mvc.exception;


import java.io.Serializable;

public class Service3NotFound extends RuntimeException implements Serializable {
    public Service3NotFound(String message) {
        super(message);
    }
}
