package com.cmpe275.lab2.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmpe275.lab2.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
	Player findByEmail(String email);
}
