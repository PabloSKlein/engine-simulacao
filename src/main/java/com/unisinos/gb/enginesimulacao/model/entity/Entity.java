package com.unisinos.gb.enginesimulacao.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.model.PetriNet;

public abstract class Entity {

	private Integer id;
	private double creationTime;
	private String name;
	private Integer priority;
	private List<EntitySet> entitySetList = new ArrayList<>();
	private PetriNet petriNet;

	protected Entity(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public void insertEntitySet(EntitySet entitySet) {
		entitySetList.add(entitySet);
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

	public double getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(double creationTime) {
		this.creationTime = creationTime;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public double getTimeSinceCreation() {
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
