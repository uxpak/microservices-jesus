package com.jesus.userservice.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.jesus.userservice.model.Pc;

import java.util.List;

@FeignClient(name = "pc-service")
@RequestMapping("/pc")
public interface PcFeignClient {

    @PostMapping()
    Pc save(@RequestBody Pc pc);

    @GetMapping("/byuser/{userId}")
    List<Pc> getPcs(@PathVariable("userId") int userId);
}
