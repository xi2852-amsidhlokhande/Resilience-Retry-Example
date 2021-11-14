package com.amsidh.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseService4 {
    private Integer service4Id;
    private String service4Message;
    private ResponseService5 service5;
}
