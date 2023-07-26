package com.jesus.pcservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jesus.pcservice.entity.Pc;


@Repository
public interface PcRepository extends JpaRepository<Pc, Integer> {
	  List<Pc> findByUserId(int userId);

}
