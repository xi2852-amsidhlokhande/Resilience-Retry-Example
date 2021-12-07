package com.amsidh.mvc.repository;

import com.amsidh.mvc.entities.Service2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Service2Repository extends JpaRepository<Service2, Integer> {
}
