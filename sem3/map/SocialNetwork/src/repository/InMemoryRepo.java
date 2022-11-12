package repository;

import domain.Entity;
import domain.exception.DuplicatedException;
import domain.exception.ValidationException;
import domain.validators.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryRepo<T extends Entity> implements Repository<T> {
    private final ArrayList<T> list;
    private final Validator<T> validator;

    /**
     * Repository constructor
     * @param validator T validator
     */
    public InMemoryRepo(Validator<T> validator) {
        this.validator = validator;
        list = new ArrayList<>();
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

        for(T elem : list) {
            if(elem.equals(obj))
                throw new DuplicatedException("Can't store; found duplicate.");
        }

        list.add(obj);
    }

    /**
     * Removes object from repository
     * @param obj object to be removed
     */
    @Override
    public void remove(T obj) {
        list.remove(obj);
    }

    /**
     * Updates object from repository
     * @param oldObj old object
     * @param newObj updated object
     */
    @Override
    public void update(T oldObj, T newObj) {
        for(int i=0; i < list.size(); i++) {
            if(list.get(i).equals(oldObj))
                list.set(i, newObj);
        }
    }

    /**
     * Finds entity in repository
     * @param uuid uuid of entity to be found
     * @return the entity or null if it wasn't found
     */
    @Override
    public T findByUUID(UUID uuid) {
        for(T elem : list) {
            if(elem.getId() == uuid)
                return elem;
        }
        return null;
    }

    /**
     * Filters the entities stored using custom function
     * @param function filter function (returns boolean)
     * @return list of entities that matched
     */
    @Override
    public List<T> filterEntities(EntityFilterFunction function) {
        List<T> filteredList = new ArrayList<>();

        for(T elem : list) {
            if(function.filter(elem))
                filteredList.add(elem);
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
        for(T elem : list) {
            if(elem.equals(obj))
                return elem;
        }
        return null;
    }

    /**
     * @return list of every object from repository
     */
    @Override
    public List<T> getList() {
        return list;
    }

    /**
     * @return size of repository
     */
    @Override
    public int size() {
        return list.size();
    }
}
