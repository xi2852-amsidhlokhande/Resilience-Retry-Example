package com.amsidh.mvc.service;

import com.amsidh.mvc.entities.Service4;

import java.util.List;

public interface Service4Service {
    Service4 saveService4(Service4 service4);
    Service4 getService4ById(Integer service4Id);
    List<Service4> getAllService4();
}
