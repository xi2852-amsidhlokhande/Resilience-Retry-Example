package com.amsidh.mvc.exception;


import java.io.Serializable;

public class Service1NotFound extends RuntimeException implements Serializable {
    public Service1NotFound(String message) {
        super(message);
    }
    public Service1NotFound(Exception exception) {
        super(exception);
    }
}
