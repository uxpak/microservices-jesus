package com.jesus.pcservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jesus.pcservice.entity.Pc;
import com.jesus.pcservice.service.PcService;


@RestController
@RequestMapping("/pc")

public class PcController {
	   @Autowired
	    PcService pcService;

	    @GetMapping
	    public ResponseEntity<List<Pc>> getAll() {
	        List<Pc> pcs = pcService.getAll();
	        if(pcs.isEmpty())
	            return ResponseEntity.noContent().build();
	        return ResponseEntity.ok(pcs);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Pc> getById(@PathVariable("id") int id) {
	        Pc pc = pcService.getPcById(id);
	        if(pc == null)
	            return ResponseEntity.notFound().build();
	        return ResponseEntity.ok(pc);
	    }

	    @PostMapping()
	    public ResponseEntity<Pc> save(@RequestBody Pc pc) {
	        Pc pcNew = pcService.save(pc);
	        return ResponseEntity.ok(pcNew);
	    }

	    @GetMapping("/byuser/{userId}")
	    public ResponseEntity<List<Pc>> getByUserId(@PathVariable("userId") int userId) {
	        List<Pc> pcs = pcService.byUserId(userId);
	        return ResponseEntity.ok(pcs);
	    }

}
