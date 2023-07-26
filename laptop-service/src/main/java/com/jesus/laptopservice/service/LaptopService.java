package com.jesus.laptopservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jesus.laptopservice.entity.Laptop;
import com.jesus.laptopservice.repository.LaptopRepository;


@Service
public class LaptopService {
	
	  @Autowired
	    LaptopRepository laptopRepository;

	    public List<Laptop> getAll() {
	        return laptopRepository.findAll();
	    }

	    public Laptop getLaptopById(int id) {
	        return laptopRepository.findById(id).orElse(null);
	    }

	    public Laptop save(Laptop laptop) {
	        Laptop laptopNew = laptopRepository.save(laptop);
	        return laptopNew;
	    }

	    public List<Laptop> byUserId(int userId) {
	        return laptopRepository.findByUserId(userId);
	    }

}
