package com.amsidh.mvc.service;

import com.amsidh.mvc.entities.Service1;

import java.util.List;

public interface Service1Service {
    Service1 saveService1(Service1 service1);
    Service1 getService1ById(Integer service1Id);
    List<Service1> getAllService1();
}
