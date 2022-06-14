package com.unisinos.gb.enginesimulacao.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.unisinos.gb.enginesimulacao.engine.Scheduler;
import com.unisinos.gb.enginesimulacao.enumeration.QueueModeEnum;

/**
 * Representa uma fila.
 * 
 * @author Cristiano Farias <cristiano@snotra.com.br>
 * @since 12 de jun. de 2022
 */
public class EntitySet {
	private final Scheduler scheduler;
	private Integer cont = 0;
	private Double somaTempoTotal = 0.0;
	private final String name;
	private final int id;
	private final int maxPossibleSize;
	private final List<Entity> entityList;
	private QueueModeEnum mode;

	public EntitySet(int id, String name, QueueModeEnum mode, int maxPossibleSize, Scheduler scheduler) {
		this.name = name;
		this.mode = mode;
		this.maxPossibleSize = maxPossibleSize;
		this.entityList = new ArrayList<>();
		this.id = id;
		this.scheduler = scheduler;
	}

	public void insert(Entity entity) {
		if (isFull()) {
			throw new RuntimeException("Fila cheia!");
		}
		entity.setCreationTime(scheduler.getTempo());
		entity.insertEntitySet(this);
		this.cont++;
		entityList.add(entity);
	}

	public Entity remove() {
		Entity enti = mode.remove(entityList);
		Double sum = scheduler.getTempo() - enti.getCreationTime();
		// System.out.println(this.name + " - " + sum);
		this.somaTempoTotal += sum;
		return enti;
	}

	public Double mediaEsperaFila() {
		return this.somaTempoTotal / this.cont;
	}

	public Entity removeById(int id) {
		var entity = entityList.stream().filter(it -> it.getId() == id).findFirst().orElseThrow();

		entityList.remove(entity);
		return entity;
	}

	public boolean isEmpty() {
		return entityList.isEmpty();
	}

	public boolean isFull() {
		return entityList.size() == maxPossibleSize;
	}

	public double averageTimeInSet() {
		double amountOfTime = 0;
		for (Entity entity : this.entityList) {
			amountOfTime += entity.getTimeSinceCreation();
		}
		return amountOfTime / this.entityList.size();
	}

	public double maxTimeInSet() {
		double maxTime = 0;
		for (Entity entity : this.entityList) {
			if (entity.getTimeSinceCreation() > maxTime)
				maxTime = entity.getTimeSinceCreation();
		}
		return maxTime;
	}

	// TODO
	public double startLog() {
		return 0;
	}

	// TODO
	public double stopLog() {
		return 0;
	}

	// TODO
	public double getLog() {
		return 0;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public int getMaxPossibleSize() {
		return maxPossibleSize;
	}

	public List<Entity> getEntityList() {
		return entityList;
	}

	public QueueModeEnum getMode() {
		return mode;
	}

	public void setMode(QueueModeEnum mode) {
		this.mode = mode;
	}

	public int getSize() {
		return this.entityList.size();
	}

	@Override
	public String toString() {
		return "FILA (" + this.getName() + ") -> " + "|".repeat(getSize()) + " (" + getSize() + ")";
	}
}
