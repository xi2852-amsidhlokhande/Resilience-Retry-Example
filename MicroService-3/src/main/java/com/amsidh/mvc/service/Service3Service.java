package com.amsidh.mvc.service;

import com.amsidh.mvc.entities.Service3;

import java.util.List;

public interface Service3Service {
    Service3 saveService3(Service3 service3);
    Service3 getService3ById(Integer service3Id);
    List<Service3> getAllService3();
}
