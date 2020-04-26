package com.cmpe275.lab2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.lab2.errors.BadRequestException;
import com.cmpe275.lab2.model.Address;
import com.cmpe275.lab2.model.Sponsor;
import com.cmpe275.lab2.service.SponsorService;

@RestController
public class SponsorController {

	@Autowired
	private SponsorService sponsorService;

	/*
	 * (5) Create an sponsor 
	 * Path: sponsor?name=XX&description=YY&street=ZZ&...
	 * Method: POST 
	 * 
	 * This API creates a sponsor object. For simplicity, all the fields 
	 * (name, description, street, city, etc) are passed in as query parameters. 
	 * Only name is required. The beneficiaries cannot be passed in as a parameter. 
	 * The request returns the newly created sponsor object in its deep form in the 
	 * requested format in the HTTP payload.
	 */
	@PostMapping("/sponsor")
	public Sponsor createSponsor(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "street", required = false) String street,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "zip", required = false) String zip) {

		if(description!=null) {
			description=description.trim();
		}
		if(street!=null) {
			street=street.trim();
		}
		if(city!=null) {
			city=city.trim();
		}
		if(state!=null) {
			state=state.trim();
		}
		if(zip!=null) {
			zip=zip.trim();
		}
		
		name=name.trim();
		if(name.length() < 2) {
			throw new BadRequestException("Name should be atleast 2 characters long");
		}
		Address address = new Address(street,city,state,zip);
		Sponsor sponsor = new Sponsor(name, description, address);
		Sponsor newSponsor = sponsorService.createSponsor(sponsor);
		
		return newSponsor;
	}

	/*
	 * (6) Get a sponsor 
	 * Path:sponsor/{name} 
	 * Method: GET
	 * 
	 * This returns a deep sponsor object in the requested format in its HTTP payload.
	 */
	@GetMapping("/sponsor/{name}")
	public Sponsor getSponsor(@PathVariable(value = "name") String sponsorName) {
		sponsorName = sponsorName.trim();
		
		if(sponsorName.length() < 2) {
			throw new BadRequestException("Name should be atleast 2 characters long");
		}

		Sponsor sponsor = sponsorService.getSponsor(sponsorName.trim());
		
		return sponsor;
	}

	/*
	 * (7) Update a sponsor 
	 * Path: sponsor/{name}?description=YY&street=ZZ&...
	 * Method: POST
	 * 
	 * This API updates a sponsor object. For simplicity, all the fields
	 * (description, street, city, etc), except name, are passed in as query parameters. 
	 * Only name is required. The beneficiaries cannot be passed in as a parameter. 
	 * Similar to the get method, the request returns the updated sponsor 
	 * object in its deep form, including the shallow beneficiaries.
	 */

	@PutMapping("/sponsor/{name}")
	public Sponsor updateSponsor(@PathVariable(value = "name") String sponsorName,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "street", required = false) String street,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "zip", required = false) String zip) {
		
		if (description != null) {
			description = description.trim();
		}
		if (street != null) {
			street = street.trim();
		}
		if (city != null) {
			city = city.trim();
		}
		if (zip != null) {
			zip = zip.trim();
		}
		
		sponsorName = sponsorName.trim();
		
		if(sponsorName.length() < 2) {
			throw new BadRequestException("Name should be atleast 2 characters long");
		}


		Address address = new Address(street, city, state, zip);
		Sponsor sponsor = new Sponsor(sponsorName, description, address);
		Sponsor updatedSponsor = sponsorService.updateSponsor(sponsor);

		// Error Handling: If the sponsor name does not exist, 404 should be returned.
		// If required parameters are missing, return 400 instead. Otherwise, return
		// 200.
		return updatedSponsor;
	}

	/*
	 * (8) Delete a sponsor 
	 * URL: http://sponsor/{name} 
	 * Method: DELETE 
	 * 
	 * This method deletes the sponsor object with the given name, 
	 * and returns the deep form of the deleted sponsor object.
	 */

	@DeleteMapping("/sponsor/{name}")
	public Sponsor deleteSponsor(@PathVariable(value = "name") String sponsorName) {

		sponsorName = sponsorName.trim();
		
		if(sponsorName.length() < 2) {
			throw new BadRequestException("Name should be atleast 2 characters long");
		}
		
		Sponsor sponsor = sponsorService.deleteSponsor(sponsorName);
		return sponsor;

		// Error Handling: If there is still any player benefiting from this sponsor,
		// return 400. If the sponsor with the given name does not exist, return 404.
	}

}
