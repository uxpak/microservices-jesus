package com.jesus.laptopservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jesus.laptopservice.entity.Laptop;
import com.jesus.laptopservice.service.LaptopService;


@RestController
@RequestMapping("/laptop")

public class LaptopController {
	   @Autowired
	    LaptopService laptopService;

	    @GetMapping
	    public ResponseEntity<List<Laptop>> getAll() {
	        List<Laptop> laptops = laptopService.getAll();
	        if(laptops.isEmpty())
	            return ResponseEntity.noContent().build();
	        return ResponseEntity.ok(laptops);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Laptop> getById(@PathVariable("id") int id) {
	        Laptop laptop = laptopService.getLaptopById(id);
	        if(laptop == null)
	            return ResponseEntity.notFound().build();
	        return ResponseEntity.ok(laptop);
	    }

	    @PostMapping()
	    public ResponseEntity<Laptop> save(@RequestBody Laptop laptop) {
	        Laptop laptopNew = laptopService.save(laptop);
	        return ResponseEntity.ok(laptopNew);
	    }

	    @GetMapping("/byuser/{userId}")
	    public ResponseEntity<List<Laptop>> getByUserId(@PathVariable("userId") int userId) {
	        List<Laptop> laptops = laptopService.byUserId(userId);
	        return ResponseEntity.ok(laptops);
	    }

}
