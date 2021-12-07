package com.amsidh.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseService2 {
    private Integer service2Id;
    private String service2Message;
    private ResponseService3 service3;
}
