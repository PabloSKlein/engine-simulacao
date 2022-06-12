package com.unisinos.gb.enginesimulacao.model;

public abstract class Scheduled {
	private final Integer id;
	private final String name;
	private final Scheduler scheduler;

	public Scheduled(Integer id, String name, Scheduler scheduler) {
		super();
		this.id = id;
		this.name = name;
		this.scheduler = scheduler;
	}

	public Integer getId() {
		return id;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public String getName() {
		return name;
	}

}
