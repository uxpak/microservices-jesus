package com.jesus.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jesus.userservice.entity.User;
import com.jesus.userservice.feignclients.PcFeignClient;
import com.jesus.userservice.feignclients.LaptopFeignClient;
import com.jesus.userservice.model.Pc;
import com.jesus.userservice.model.Laptop;
import com.jesus.userservice.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LaptopFeignClient laptopFeignClient;

    @Autowired
    PcFeignClient pcFeignClient;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        User userNew = userRepository.save(user);
        return userNew;
    }

    public List<Laptop> getLaptops(int userId) {
        List<Laptop> laptops = restTemplate.getForObject("http://laptop-service/laptop/byuser/" + userId, List.class);
        return laptops;
    }

    public List<Pc> getPcs(int userId) {
        List<Pc> pcs = restTemplate.getForObject("http://pc-service/pc/byuser/" + userId, List.class);
        return pcs;
    }

    public Laptop saveLaptop(int userId, Laptop laptop) {
        laptop.setUserId(userId);
        Laptop laptopNew = laptopFeignClient.save(laptop);
        return laptopNew;
    }

    public Pc savePc(int userId, Pc pc) {
        pc.setUserId(userId);
        Pc pcNew = pcFeignClient.save(pc);
        return pcNew;
    }

    public Map<String, Object> getUserAndDevices(int userId) {
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            result.put("Mensaje", "no existe el usuario");
            return result;
        }
        result.put("User", user);
        List<Laptop> laptops = laptopFeignClient.getLaptops(userId);
        if(laptops.isEmpty())
            result.put("Laptops", "ese user no tiene laptops");
        else
            result.put("Laptops", laptops);
        List<Pc> pcs = pcFeignClient.getPcs(userId);
        if(pcs.isEmpty())
            result.put("Pcs", "ese user no tiene pcs");
        else
            result.put("Pcs", pcs);
        return result;
    }
}
