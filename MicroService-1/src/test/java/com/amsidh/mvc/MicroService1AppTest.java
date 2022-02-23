package com.amsidh.mvc;

import com.amsidh.mvc.controller.Service1Controller;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
public class MicroService1AppTest {

    private final Service1Controller service1Controller;

    @Test
    void contextLoads(){
        Assertions.assertNotNull(service1Controller);
    }
}
