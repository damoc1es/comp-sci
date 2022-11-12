package repository;

import domain.Entity;

@FunctionalInterface
public interface EntityFilterFunction {
    boolean filter(Entity entity);
}
