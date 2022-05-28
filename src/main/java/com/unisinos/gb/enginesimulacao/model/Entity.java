package com.unisinos.gb.enginesimulacao.model;

import java.util.Date;

public abstract class Entity {

	private String name;
	private Integer id;
	private long creationTime;
	private Integer priority;
	// private PetriNet petriNet;

	public Entity(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public long getTimeSinceCreation() {
		long atual = new Date().getTime();
		return atual - getCreationTime();
	}

//	public Entity(String name,PetriNet  petriNet) {
//		this.name = name;
//		this.petriNet = petriNet;
//	}

//
//	getPriority(): integer e setPriority(priority)
//	getTimeSinceCreation(): double
//	getSets():EntitySet List  retorna lista de EntitySets nas quais a entidade está inserida
//	setPetriNet(PetriNet)
//
//	e getPetriNet():PetriNet

}
