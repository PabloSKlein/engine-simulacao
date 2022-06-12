package com.unisinos.gb.enginesimulacao.model.event;

import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.model.Scheduled;
import com.unisinos.gb.enginesimulacao.model.Scheduler;

public abstract class Event extends Scheduled {

	private final EntitySet fila;
	private double time;

	public Event(Integer id, String name, Scheduler scheduler, EntitySet fila, double time) {
		super(id, name, scheduler);
		this.fila = fila;
		this.time = time;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public EntitySet getFila() {
		return fila;
	}

}
