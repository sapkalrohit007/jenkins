package com.cmpe275.lab2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275.lab2.dao.SponsorRepository;
import com.cmpe275.lab2.errors.AlreadyExistsException;
import com.cmpe275.lab2.errors.NotFoundException;
import com.cmpe275.lab2.model.Player;
import com.cmpe275.lab2.model.Sponsor;

/*
 * Actual implementation of Sponsor CRUD operations
 * */
@Service
public class SponsorServiceImpl implements SponsorService {

	@Autowired
	private SponsorRepository sponsorDao;

	@Override
	public Sponsor createSponsor(Sponsor sponsor) {
		Sponsor existingSponsor=null;
		
		try {
			existingSponsor = sponsorDao.findByName(sponsor.getName());
		} catch (Exception e) {
			throw new RuntimeException(e.fillInStackTrace());
		}
		
		if (existingSponsor != null) {
			throw new AlreadyExistsException("Sponsor with given Id already present!");
		} else {
			try {
				return sponsorDao.save(sponsor);
			}catch (Exception e) {
				throw new RuntimeException(e.fillInStackTrace());
			}	
		}
	}

	@Override
	public Sponsor updateSponsor(Sponsor sponsor) {
		Sponsor existingSponsor=null;
		try {
			existingSponsor = sponsorDao.findByName(sponsor.getName());
		} catch (Exception e) {
			throw new RuntimeException(e.fillInStackTrace());
		}
		
		if (existingSponsor != null) {
			existingSponsor.setName(sponsor.getName());
			existingSponsor.setAddress(sponsor.getAddress());
			existingSponsor.setDescription(sponsor.getDescription());
			Sponsor updatedSponsor = sponsorDao.save(existingSponsor);
			return updatedSponsor;
		} else {
			throw new NotFoundException("Sponsor does not exist!");
		}
	}

	@Override
	public Sponsor getSponsor(String name) {
		Sponsor sponsor=null;
		try {
			sponsor = sponsorDao.findByName(name);
		} catch (Exception e) {
			throw new RuntimeException(e.fillInStackTrace());
		}
		
		if (sponsor != null) {
			return sponsor;
		} else {
			throw new NotFoundException("Sponsor does not exist!");
		}
	}

	@Override
	public Sponsor deleteSponsor(String name) {
		Sponsor sponsor=null;
		
		try {
			sponsor = sponsorDao.findByName(name);
		}catch (Exception e) {
			throw new RuntimeException(e.fillInStackTrace());
		}
		
		if (sponsor != null) {
			List<Player>beneficiaries=sponsor.getBeneficiaries();
			System.out.println(beneficiaries);
			System.out.println("inside");
			for(Player beneficiar:beneficiaries) {
				beneficiar.setSponsor(null);
			}
			sponsorDao.delete(sponsor);
			return sponsor;
		} else {
			throw new NotFoundException("Sponsor does not exist!");
		}
	}

}
