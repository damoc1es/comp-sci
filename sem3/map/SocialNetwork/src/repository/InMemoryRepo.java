package repository;

import domain.Entity;
import domain.exception.DuplicatedException;
import domain.exception.ValidationException;
import domain.validators.Validator;

import java.util.*;

public class InMemoryRepo<T extends Entity> implements Repository<T> {
    private final Map<UUID, T> map;
    private final Validator<T> validator;

    /**
     * Repository constructor
     * @param validator T validator
     */
    public InMemoryRepo(Validator<T> validator) {
        this.validator = validator;
        map = new HashMap<>();
    }

    /**
     * Stores object in repository
     * @param obj object
     * @throws DuplicatedException if equal object was found
     * @throws ValidationException if object isn't valid (validator throws)
     */
    @Override
    public void store(T obj) throws DuplicatedException, ValidationException {
        validator.validate(obj);

        if(findByUUID(obj.getId()) != null)
            throw new DuplicatedException("Can't store; found duplicate.");

        for(Map.Entry<UUID, T> elem : map.entrySet()) {
            if(elem.getValue().equals(obj))
                throw new DuplicatedException("Can't store; found duplicate.");
        }

        map.put(obj.getId(), obj);
    }

    /**
     * Removes object from repository
     * @param obj object to be removed
     */
    @Override
    public void remove(T obj) {
        map.remove(obj.getId());
    }

    /**
     * Updates object from repository
     * @param oldObj old object
     * @param newObj updated object
     */
    @Override
    public void update(T oldObj, T newObj) {
        map.remove(oldObj.getId());
        map.put(newObj.getId(), newObj);
    }

    /**
     * Finds entity in repository
     * @param uuid uuid of entity to be found
     * @return the entity or null if it wasn't found
     */
    @Override
    public T findByUUID(UUID uuid) {
        return map.get(uuid);
    }

    /**
     * Filters the entities stored using custom function
     * @param function filter function (returns boolean)
     * @return list of entities that matched
     */
    @Override
    public List<T> filterEntities(EntityFilterFunction function) {
        List<T> filteredList = new ArrayList<>();

        for(Map.Entry<UUID, T> elem : map.entrySet()) {
            if(function.filter(elem.getValue()))
                filteredList.add(elem.getValue());
        }

        return filteredList;
    }

    /**
     * Finds object in repository
     * @param obj object
     * @return the object or null if it wasn't found
     */
    @Override
    public T find(T obj) {
        for(Map.Entry<UUID, T> elem : map.entrySet()) {
            if(elem.getValue().equals(obj))
                return elem.getValue();
        }

        return null;
    }

    /**
     * @return list of every object from repository
     */
    @Override
    public List<T> getList() {
        return List.copyOf(map.values());
    }

    /**
     * @return size of repository
     */
    @Override
    public int size() {
        return map.size();
    }
}
