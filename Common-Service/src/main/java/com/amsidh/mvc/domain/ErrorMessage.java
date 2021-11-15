package com.amsidh.mvc.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorMessage implements Serializable {
	private static final long serialVersionUID = -5228377535403551059L;
	private Integer statusCode;
    private Date timestamp;
    private String message;
    private String description;
}
