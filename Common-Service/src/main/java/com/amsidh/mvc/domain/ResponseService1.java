package com.amsidh.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseService1 {
     private Integer service1Id;
     private String service1Message;
     private ResponseService2 service2;
}
