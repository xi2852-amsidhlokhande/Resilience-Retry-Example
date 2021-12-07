package com.amsidh.mvc.exception;


import java.io.Serializable;

public class Service4NotFound extends RuntimeException implements Serializable {
    public Service4NotFound(String message) {
        super(message);
    }
}
