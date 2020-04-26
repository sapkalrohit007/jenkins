package com.cmpe275.lab2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275.lab2.dao.PlayerRepository;
import com.cmpe275.lab2.dao.SponsorRepository;
import com.cmpe275.lab2.errors.AlreadyExistsException;
import com.cmpe275.lab2.errors.NotFoundException;
import com.cmpe275.lab2.model.Player;
import com.cmpe275.lab2.model.Sponsor;

/*
 * Actual implementation of Player CRUD operations
 * */
@Service
public class PlayerServiceImpl implements PlayerService {
	
	@Autowired
	private PlayerRepository playerDao;
	
	@Autowired
	private SponsorRepository sponsorDao;
	
	@Override
	public Player createPlayer(Player player, String sponsorName) {
		Player existingPlayer = playerDao.findByEmail(player.getEmail());
		if (existingPlayer != null) {
			throw new AlreadyExistsException("Player with given Email Id already present");
		} else {
			if(sponsorName != null && sponsorName.length()!=0) {
				Optional<Sponsor> sponsorResult = sponsorDao.findById(sponsorName);
				if(sponsorResult.isPresent()) {
					player.setSponsor(sponsorResult.get());
					sponsorResult.get().getBeneficiaries().add(player);
				}else {
					throw new NotFoundException("Incorrect sponsor Id...Sponsor with given name is not valid");
				}
			}
			try {
				Player result = playerDao.saveAndFlush(player);
				return result;
			} catch (Exception e) {
				throw new RuntimeException(e.fillInStackTrace());
			}
		}
	}

	@Override
	public Player updatePlayer(Player player, String sponsorName) {
			Optional<Player> existingPlayer = playerDao.findById(player.getId());
			if (!existingPlayer.isPresent()) {
				throw new NotFoundException("Player with given Id not present");
			} else {
				Player existingPlayerFinal=existingPlayer.get();
				Player existingPlayerWithEmail = playerDao.findByEmail(player.getEmail());
				if (existingPlayerWithEmail != null && existingPlayerWithEmail.getId()!=player.getId()) {
					throw new AlreadyExistsException("Player with given Email Id already present");
				}
				existingPlayerFinal.setFirstname(player.getFirstname());
				existingPlayerFinal.setLastname(player.getLastname());
				existingPlayerFinal.setDescription(player.getDescription());
				existingPlayerFinal.setEmail(player.getEmail());
				existingPlayerFinal.setAddress(player.getAddress());
				if(sponsorName!=null && sponsorName.length()!=0) {
					Optional<Sponsor> sponsorResult = sponsorDao.findById(sponsorName);
					if(sponsorResult.isPresent()) {
						existingPlayerFinal.setSponsor(sponsorResult.get());
					}else {
						throw new NotFoundException("Incorrect sponsor Id...Sponsor with given name is not valid");
					}
				}	
				try {
					return playerDao.save(existingPlayerFinal);
				} catch (Exception e) {
					throw new RuntimeException(e.fillInStackTrace());
				}
			}
	}	
	
	
	@Override
	public Player getPlayer(long id) {
		Optional<Player> result = playerDao.findById(id);
		if(result.isPresent()) {
			
			try {
				return result.get();
			} catch (Exception e) {
				throw new RuntimeException(e.fillInStackTrace());
			}
		}else {
			throw new NotFoundException("player with given Id does not found");
		}
	}

	@Override
	public Player deletePlayer(long id) {
		Optional<Player> result = playerDao.findById(id);
		if(result.isPresent()) {
			Player player=result.get();
			List<Player> opponents = player.getOpponents();
			for(Player opponent:opponents) {
				List<Player> newOpponents = opponent.getOpponents();
				newOpponents.remove(player);
			}
			Sponsor sponsor=player.getSponsor();
			if(sponsor!=null) {
				sponsor.getBeneficiaries().remove(player);
			}
			
			try {
				playerDao.delete(player);
				return player;
			} catch (Exception e) {
				throw new RuntimeException(e.fillInStackTrace());
			}
		}else {
			throw new NotFoundException("player with given Id does not found");
		}
	}
}
