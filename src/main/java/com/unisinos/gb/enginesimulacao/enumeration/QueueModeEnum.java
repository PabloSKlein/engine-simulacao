package com.unisinos.gb.enginesimulacao.enumeration;

import com.unisinos.gb.enginesimulacao.model.entity.Entity;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public enum QueueModeEnum {
    FIFO {
        public Entity remove(List<Entity> entityList) {
            return entityList.remove(0);
        }
    }, LIFO {
        public Entity remove(List<Entity> entityList) {
            return entityList.remove(entityList.size() - 1);
        }
    }, PRIORITY_BASED {
        public Entity remove(List<Entity> entityList) {
            var entity = entityList.stream()
                    .max(Comparator.comparingLong(Entity::getPriority))
                    .orElseThrow();
            entityList.remove(entity);
            return entity;
        }
    }, NONE {
        public Entity remove(List<Entity> entityList) {
            return entityList.remove(randomIntFromInterval(0, entityList.size() - 1));
        }
    };

    public abstract Entity remove(List<Entity> entityList);

    public static int randomIntFromInterval(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
