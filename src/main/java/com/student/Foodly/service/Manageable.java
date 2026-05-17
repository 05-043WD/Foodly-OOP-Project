package com.student.Foodly.service;

import java.util.List;

/**
 * Manageable interface demonstrating Abstraction and Polymorphism (OOP).
 * All CRUD operations are declared here and implemented by UserService and FoodItemService.
 *
 * @param <T> the entity type managed by this service
 */
public interface Manageable<T> {

    /**
     * Create: persist a new entity to the data store.
     */
    void create(T entity);

    /**
     * Read: retrieve all entities from the data store.
     */
    List<T> readAll();

    /**
     * Update: find entity by id and update it in the data store.
     */
    void update(T entity);

    /**
     * Delete: remove entity by id from the data store.
     */
    void delete(String id);
}
