package com.jesus.pcservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jesus.pcservice.entity.Pc;
import com.jesus.pcservice.repository.PcRepository;


@Service
public class PcService {
	
	  @Autowired
	    PcRepository pcRepository;

	    public List<Pc> getAll() {
	        return pcRepository.findAll();
	    }

	    public Pc getPcById(int id) {
	        return pcRepository.findById(id).orElse(null);
	    }

	    public Pc save(Pc pc) {
	        Pc pcNew = pcRepository.save(pc);
	        return pcNew;
	    }

	    public List<Pc> byUserId(int userId) {
	        return pcRepository.findByUserId(userId);
	    }

}
