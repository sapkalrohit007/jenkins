package com.cmpe275.lab2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.lab2.errors.BadRequestException;
import com.cmpe275.lab2.errors.NotFoundException;
import com.cmpe275.lab2.service.OpponenentServiceImpl;

@RestController
public class OpponentController {

	@Autowired
	private OpponenentServiceImpl opponentService;
	/*
	 * (9) Add an opponent relationship 
	 * Path:opponents/{id1}/{id2} 
	 * Method: POST This
	 * 
	 * makes the two players with the given IDs opponents with each other. If the
	 * two players are already opponents, do nothing, just return 200. Otherwise,
	 * record this opponent relationship.
	 */
	@PostMapping("/opponents/{id1}/{id2}")
	public String addOpponent(@PathVariable(value = "id1") Long playerId1,
			@PathVariable(value = "id2") Long playerId2) {
		/*
		 * Error Handling: If either player does not exist, return 404. Return 400 for
		 * other bad requests, e.g., the given two players are the same. If all is
		 * successful, return HTTP code 200 and any informative text message in the HTTP
		 * payload.
		 */
			try {
				String result=opponentService.addOpponentRelationship(playerId1, playerId2);
				return result;
			}catch (NotFoundException e) {
				throw e;
			}catch (BadRequestException e) {
				throw e;
			}catch (Exception e) {
				throw new BadRequestException("please provide valid ID");	
			}
		
	}

	/*
	 * (10) Remove an opponent relationship 
	 * Path:opponents/{id1}/{id2} Method:
	 * DELETE
	 * 
	 * This request removes the opponent relation between the two players.
	 */
	@DeleteMapping("/opponents/{id1}/{id2}")
	public String deleteOpponent(@PathVariable(value = "id1") Long playerId1,
			@PathVariable(value = "id2") Long playerId2) {

		/*
		 * Error Handling: If either player does not exist, return 404. Return 400 for
		 * other bad requests; e.g, if the two players are not opponents, return 400
		 * too. Otherwise, Remove this opponent relation. Return HTTP code 200 and a
		 * meaningful text message if all is successful.
		 */
		try {
			String result = opponentService.removeOpponentRelationship(playerId1, playerId2);
			return result;
		}catch (NotFoundException e) {
			throw e;
		}catch (BadRequestException e) {
			throw e;
		}catch (Exception e) {
			throw new BadRequestException("please provide valid ID");	
		}
	}

}
