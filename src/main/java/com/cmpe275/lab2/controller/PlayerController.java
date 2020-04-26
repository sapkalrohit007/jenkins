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
import com.cmpe275.lab2.model.Player;
import com.cmpe275.lab2.service.PlayerService;

@RestController
public class PlayerController {

	@Autowired
	private PlayerService playerService;

	/*
	 * (1) Create a player 
	 * Path: player?firstname=XX&lastname=YY&email=ZZ&description=UU&street=VV$... 
	 * Method: POST
	 * 
	 * Required Parameters: Only the firstname, lastname, and email are required.
	 * Anything else is optional. Opponents is not allowed to be passed in as a
	 * parameter. You are not allowed to provide the ID of the player either, as the
	 * server is supposed to assign the ID. The sponsor parameter, if present, must
	 * be the ID of an existing sponsor. The request returns the newly created
	 * player object in its deep form, encoded in the requested format, JSON or XML,
	 * in its HTTP payload.
	 */
	@PostMapping("/player")
	public Player createPlayer(@RequestParam(value = "firstname", required = true) String fname,
			@RequestParam(value = "lastname", required = true) String lname,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "street", required = false) String street,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "zip", required = false) String zip,
			@RequestParam(value = "sponsor", required = false) String sponsor) {
		if(description!=null) {
			description=description.trim();
		}
		if(street!=null) {
			street=street.trim();
		}
		if(city!=null) {
			city=city.trim();
		}
		if(zip!=null) {
			zip=zip.trim();
		}
		if(sponsor!=null) {
			sponsor=sponsor.trim();
		}
		fname=fname.trim();
		lname=lname.trim();
		email=email.trim();
		if(fname.length()==0 || lname.length()==0 || email.length()==0) {
			throw new BadRequestException("missing required parameters or bad parameters");
		}
		Address address = new Address(street,city,state,zip);
		Player player = new Player(fname, lname, email, description, address);
		
		Player resultPlayer=playerService.createPlayer(player, sponsor);
		return resultPlayer;
	}

	/*
	 * (2) Get a player 
	 * Path:player/{id} 
	 * Method: GET 
	 * This returns a deep player object in the requested format in its HTTP payload.
	 */
	@GetMapping("/player/{id}")
	public Player getPlayer(@PathVariable(value = "id") Long playerId) {
			Player result = playerService.getPlayer(playerId);
			return result;		
	}

	/**
	 * (3) Update a player 
	 * Path:player/{id}?firstname=XX&lastname=YY&email=ZZ&description=UU&street=VV$...
	 * Method: PUT 
	 * This API updates a player object. 
	 * Same as above, all the player fields (firstname, lastname, email, street, city, sponsor, etc), except opponents, are passed in as query parameters. 
	 * Required fields like email must be present. 
	 * The object constructed from the parameters will completely replace the existing object in the server, except that it does not change the player’s list of opponents.
	 */
	@PutMapping("/player/{id}")
	public Player updatePlayer(@PathVariable(value = "id", required = true) Long playerId,
			@RequestParam(value = "firstname", required = true) String fname,
			@RequestParam(value = "lastname", required = true) String lname,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "street", required = false) String street,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "zip", required = false) String zip,
			@RequestParam(value = "sponsor", required = false) String sponsor) {

			if(description!=null) {
				description=description.trim();
			}
			if(street!=null) {
				street=street.trim();
			}
			if(city!=null) {
				city=city.trim();
			}
			if(zip!=null) {
				zip=zip.trim();
			}
			if(sponsor!=null) {
				sponsor=sponsor.trim();
			}
			fname=fname.trim();
			lname=lname.trim();
			email=email.trim();
			if(fname.length()==0 || lname.length()==0 || email.length()==0) {
				throw new BadRequestException("missing required parameters or bad parameters");
			}
			Address address = new Address(street,city,state,zip);
			Player player = new Player(fname, lname, email, description, address);
			player.setId(playerId);
			Player resultPlayer = playerService.updatePlayer(player, sponsor);
			
			return resultPlayer;

		// Error Handling: If the player of the given user ID does not exist, the HTTP
		// return code should be 404; if the given ID is not a valid number, return 400.
	}

	/*
	 * (4) Delete a player 
	 * URL: http://player/{id} 
	 * Method: DELETE 
	 * This deletes the player object with the given ID, and returns the deep form of the deleted player. 
	 * When a player is deleted, the relevant opponent and sponsoring relationships are cascadingly removed too.
	 */
	@DeleteMapping("/player/{id}")
	public Player deletePlayer(@PathVariable(value = "id") Long playerId) {
		Player result = playerService.deletePlayer(playerId);
		return result;		
		// Error Handling: If the player with the given ID does not exist, return 404.
		// Return 400 for other bad requests.
	}

}
