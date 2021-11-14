package com.amsidh.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseService3 {
    private Integer service3Id;
    private String service3Message;
    private ResponseService4 service4;
}
