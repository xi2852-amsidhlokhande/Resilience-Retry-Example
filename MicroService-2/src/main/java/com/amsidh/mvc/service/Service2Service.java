package com.amsidh.mvc.service;

import com.amsidh.mvc.entities.Service2;

import java.util.List;

public interface Service2Service {
    Service2 saveService2(Service2 service2);
    Service2 getService2ById(Integer service2Id);
    List<Service2> getAllService2();
}
