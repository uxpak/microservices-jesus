package com.jesus.userservice.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.jesus.userservice.model.Laptop;

import java.util.List;

@FeignClient(name = "laptop-service")
@RequestMapping("/laptop")
public interface LaptopFeignClient {

    @PostMapping()
    Laptop save(@RequestBody Laptop laptop);

    @GetMapping("/byuser/{userId}")
    List<Laptop> getLaptops(@PathVariable("userId") int userId);
}
