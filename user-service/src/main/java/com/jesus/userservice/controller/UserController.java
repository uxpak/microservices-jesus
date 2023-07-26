package com.jesus.userservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jesus.userservice.entity.User;
import com.jesus.userservice.model.Pc;
import com.jesus.userservice.model.Laptop;
import com.jesus.userservice.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();
        if(users.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        if(user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @PostMapping()
    public ResponseEntity<User> save(@RequestBody User user) {
        User userNew = userService.save(user);
        return ResponseEntity.ok(userNew);
    }

    @CircuitBreaker(name = "laptopsCB", fallbackMethod = "fallBackGetLaptops")
    @GetMapping("/laptops/{userId}")
    public ResponseEntity<List<Laptop>> getLaptops(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);
        if(user == null)
            return ResponseEntity.notFound().build();
        List<Laptop> laptops = userService.getLaptops(userId);
        return ResponseEntity.ok(laptops);
    }

    @CircuitBreaker(name = "laptopsCB", fallbackMethod = "fallBackSaveLaptop")
    @PostMapping("/savelaptop/{userId}")
    public ResponseEntity<Laptop> saveLaptop(@PathVariable("userId") int userId, @RequestBody Laptop laptop) {
        if(userService.getUserById(userId) == null)
            return ResponseEntity.notFound().build();
        Laptop laptopNew = userService.saveLaptop(userId, laptop);
        return ResponseEntity.ok(laptop);
    }

    @CircuitBreaker(name = "pcsCB", fallbackMethod = "fallBackGetPcs")
    @GetMapping("/pcs/{userId}")
    public ResponseEntity<List<Pc>> getPcs(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);
        if(user == null)
            return ResponseEntity.notFound().build();
        List<Pc> pcs = userService.getPcs(userId);
        return ResponseEntity.ok(pcs);
    }

    @CircuitBreaker(name = "pcsCB", fallbackMethod = "fallBackSavePc")
    @PostMapping("/savepc/{userId}")
    public ResponseEntity<Pc> savePc(@PathVariable("userId") int userId, @RequestBody Pc pc) {
        if(userService.getUserById(userId) == null)
            return ResponseEntity.notFound().build();
        Pc pcNew = userService.savePc(userId, pc);
        return ResponseEntity.ok(pc);
    }

    @CircuitBreaker(name = "allCB", fallbackMethod = "fallBackGetAll")
    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Map<String, Object>> getAllDevices(@PathVariable("userId") int userId) {
        Map<String, Object> result = userService.getUserAndDevices(userId);
        return ResponseEntity.ok(result);
    }

    private ResponseEntity<List<Laptop>> fallBackGetLaptops(@PathVariable("userId") int userId, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + " tiene las laptops en el taller de reparacion", HttpStatus.OK);
    }

    private ResponseEntity<Laptop> fallBackSaveLaptop(@PathVariable("userId") int userId, @RequestBody Laptop laptop, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + " no tiene dinero para laptops", HttpStatus.OK);
    }

    private ResponseEntity<List<Pc>> fallBackGetPcs(@PathVariable("userId") int userId, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + " tiene las pcs en el taller de reparacion", HttpStatus.OK);
    }

    private ResponseEntity<Pc> fallBackSavePc(@PathVariable("userId") int userId, @RequestBody Pc pc, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + "  no tiene dinero para pcs", HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> fallBackGetAll(@PathVariable("userId") int userId, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + " tiene los dispositivos en el taller de reparacion", HttpStatus.OK);
    }

}
