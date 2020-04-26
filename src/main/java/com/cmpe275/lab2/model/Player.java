package com.cmpe275.lab2.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "player")
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id; // primary key

	@Column(name = "first_name", nullable = false)
	private String firstname;

	@Column(name = "last_name", nullable = false)
	private String lastname;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "description")
	private String description;

	@Embedded
	private Address address;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "sponsor_id")
	@JsonIgnoreProperties({"beneficiaries"})
	private Sponsor sponsor;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(name = "opponents_table", joinColumns = @JoinColumn(name = "player_id"), inverseJoinColumns = @JoinColumn(name = "opponent_id"))
	@JsonIgnoreProperties({"sponsor","opponents"})
	private List<Player> opponents;

	public Player() {
		
	}

	public Player(String firstname, String lastname, String email) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}

	public Player(String firstname, String lastname, String email, String description, Address address) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.description = description;
		this.address = address;
	}

	public List<Player> getOpponents() {
		return opponents;
	}

	public void setOpponents(List<Player> opponents) {
		this.opponents = opponents;
	}

	public Sponsor getSponsor() {
		return sponsor;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
				+ ", description=" + description + ", address=" + address + "]";
	}

	public void addOpponent(Player opponent) {

		// add in opponent for main player
		if (this.opponents == null) {
			this.opponents = new ArrayList<Player>();
		}
		this.opponents.add(opponent);

		// add in opponent for the opponent player
		List<Player> opponents1 = opponent.getOpponents();
		if (opponents1 == null) {
			opponents1 = new ArrayList<Player>();
		}
		opponents1.add(this);
		opponent.setOpponents(opponents1);

	}

	public void removeOpponent(Player player) {
		this.opponents.remove(player);
		player.opponents.remove(this);
	}

}
