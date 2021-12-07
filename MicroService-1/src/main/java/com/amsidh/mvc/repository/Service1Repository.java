package com.amsidh.mvc.repository;

import com.amsidh.mvc.entities.Service1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Service1Repository extends JpaRepository<Service1, Integer> {
}
