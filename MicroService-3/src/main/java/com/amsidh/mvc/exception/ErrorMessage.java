package com.amsidh.mvc.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorMessage implements Serializable {
    private Integer statusCode;
    private Date timestamp;
    private String message;
    private String description;
}
