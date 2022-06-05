package com.unisinos.gb.enginesimulacao.model;

import com.unisinos.gb.enginesimulacao.enumeration.QueueModeEnum;

import java.util.ArrayList;
import java.util.List;

public class EntitySet {
    private final String name;
    private final int id;
    private final int maxPossibleSize;
    private final List<Entity> entityList;
    private QueueModeEnum mode;
    private int size;

    public EntitySet(String name, QueueModeEnum mode, int maxPossibleSize) {
        this.name = name;
        this.mode = mode;
        this.maxPossibleSize = maxPossibleSize;
        this.entityList = new ArrayList<>();
        this.id = 0;
    }

    public void insert(Entity entity) {
        entityList.add(entity);
    }

    public Entity remove() {
        return mode.remove(entityList);
    }

    public Entity removeById(int id) {
        var entity = entityList.stream()
                .filter(it -> it.getId() == id)
                .findFirst()
                .orElseThrow();

        entityList.remove(entity);
        return entity;
    }

    public boolean isEmpty(){
        return entityList.isEmpty();
    }

    public boolean isFull(){
        return entityList.size() == maxPossibleSize;
    }

    //TODO
    public double averageTimeInSet(){
        return 0;
    }

    //TODO
    public double maxTimeInSet(){
        return 0;
    }

    //TODO
    public double startLog(){
        return 0;
    }

    //TODO
    public double stopLog(){
        return 0;
    }

    //TODO
    public double getLog(){
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
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
