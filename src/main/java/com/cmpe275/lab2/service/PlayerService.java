package com.cmpe275.lab2.service;

import com.cmpe275.lab2.model.Player;

/*
 * Interface to define necessary Player CRUD operations
 * */
public interface PlayerService {
	
	public Player createPlayer(Player player,String sponsorName);
	public Player updatePlayer(Player player,String sponsorName);
	public Player getPlayer(long id);
	public Player deletePlayer(long id);
}
