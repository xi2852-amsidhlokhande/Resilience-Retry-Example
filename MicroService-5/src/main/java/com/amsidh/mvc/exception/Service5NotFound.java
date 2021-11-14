package com.amsidh.mvc.exception;


import java.io.Serializable;

public class Service5NotFound extends RuntimeException implements Serializable {
    public Service5NotFound(String message) {
        super(message);
    }
}
