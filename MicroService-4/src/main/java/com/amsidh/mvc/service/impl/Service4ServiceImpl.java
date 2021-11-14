package com.amsidh.mvc.service.impl;

import com.amsidh.mvc.entities.Service4;
import com.amsidh.mvc.repository.Service4Repository;
import com.amsidh.mvc.service.Service4Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class Service4ServiceImpl implements Service4Service {
    private final Service4Repository service4Repository;
    @Override
    public Service4 saveService4(Service4 service4) {
        log.info("Inside saveService4 method Service4ServiceImpl");
        return service4Repository.save(service4);
    }

    @Override
    public Service4 getService4ById(Integer service4Id) {
        log.info("Inside getService4ById method Service4ServiceImpl");
        return service4Repository.findById(service4Id).orElseThrow();
    }

    @Override
    public List<Service4> getAllService4() {
        log.info("Inside getAllService4 method Service4ServiceImpl");
        return service4Repository.findAll();
    }
}
