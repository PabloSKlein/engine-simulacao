package com.unisinos.gb.enginesimulacao.model;

import com.unisinos.gb.enginesimulacao.enumeration.QueueModeEnum;
import com.unisinos.gb.enginesimulacao.model.entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma fila.
 * 
 * @author Cristiano Farias <cristiano@snotra.com.br>
 * @since 12 de jun. de 2022
 */
public class EntitySet {
	private final String name;
	private final int id;
	private final int maxPossibleSize;
	private final List<Entity> entityList;
	private QueueModeEnum mode;

	public EntitySet(int id, String name, QueueModeEnum mode, int maxPossibleSize) {
		this.name = name;
		this.mode = mode;
		this.maxPossibleSize = maxPossibleSize;
		this.entityList = new ArrayList<>();
		this.id = id;
	}

	public void insert(Entity entity) {
		if (isFull()) {
			throw new RuntimeException("Fila cheia!");
		}
		entity.insertEntitySet(this);
		entityList.add(entity);
	}

	public Entity remove() {
		return mode.remove(entityList);
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

	// TODO
	public double averageTimeInSet() {
		return 0;
	}

	// TODO
	public double maxTimeInSet() {
		return 0;
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
}
