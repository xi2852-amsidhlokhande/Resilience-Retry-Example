package com.amsidh.mvc.service;

import com.amsidh.mvc.entities.Service5;

import java.util.List;

public interface Service5Service {
    Service5 saveService5(Service5 service5);
    Service5 getService5ById(Integer service5Id);
    List<Service5> getAllService5();
}
