package com.cmpe275.lab2.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmpe275.lab2.model.Sponsor;

public interface SponsorRepository extends JpaRepository<Sponsor, String> {
	
	Sponsor findByName(String name);

	Sponsor deleteByName(String name);
}
