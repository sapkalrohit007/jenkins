package com.cmpe275.lab2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275.lab2.dao.PlayerRepository;
import com.cmpe275.lab2.errors.BadRequestException;
import com.cmpe275.lab2.errors.NotFoundException;
import com.cmpe275.lab2.model.Player;

@Service
public class OpponenentServiceImpl implements OpponentService {
	@Autowired
	private PlayerRepository playerDao;
	
	@Override
	public String addOpponentRelationship(long id1, long id2) {
		if(id1==id2) {
			throw new BadRequestException("both players have same Id");
		}
		Optional<Player> player1 = playerDao.findById(id1);
		Optional<Player> player2 = playerDao.findById(id2);
		if(!player1.isPresent() || !player2.isPresent()) {
			throw new NotFoundException("players with given Id are not present/Incorrect player Id");
		}
		Player pl1=player1.get();
		Player pl2=player2.get();
		List<Player> opponents = pl1.getOpponents();
		if(opponents!=null && opponents.contains(pl2)) {
			return "Suceess.....Already opponent of each other";
		}
		pl1.addOpponent(pl2);
		playerDao.save(pl1);
		playerDao.save(pl2);
		return "Success";
	}

	@Override
	public String removeOpponentRelationship(long id1, long id2) {
		if(id1==id2) {
			throw new BadRequestException("both players have same Id");
		}
		Optional<Player> player1 = playerDao.findById(id1);
		Optional<Player> player2 = playerDao.findById(id2);
		if(!player1.isPresent() || !player2.isPresent()) {
			throw new NotFoundException("players with given Id are not present/Incorrect player Id");
		}
		Player pl1=player1.get();
		Player pl2=player2.get();
		
		List<Player> opponents = pl1.getOpponents();
		if(opponents!=null && opponents.contains(pl2)) {
			pl1.removeOpponent(pl2);
			playerDao.save(pl1);
			playerDao.save(pl2);
			return "Success";
		}else {
			throw new BadRequestException("Two players are not opponents");
		}
	}



}
