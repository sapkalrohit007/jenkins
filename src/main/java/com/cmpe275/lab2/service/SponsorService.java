package com.cmpe275.lab2.service;

import com.cmpe275.lab2.model.Sponsor;

/*
 * Interface to define necessary Sponsor CRUD operations
 * */
public interface SponsorService {

	public Sponsor createSponsor(Sponsor sponsor);
	public Sponsor updateSponsor(Sponsor sponsor);
	public Sponsor getSponsor(String name);
	public Sponsor deleteSponsor(String name);
	
}
