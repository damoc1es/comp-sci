package repository;

import domain.exception.DuplicatedException;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRepo<T> implements Repository<T> {
    ArrayList<T> list;

    public InMemoryRepo() {
        list = new ArrayList<>();
    }

    @Override
    public void store(T obj) throws DuplicatedException {
        for(T elem : list) {
            if(elem.equals(obj))
                throw new DuplicatedException("Can't store; found duplicate.");
        }

        list.add(obj);
    }

    @Override
    public void remove(T obj) {
        list.remove(obj);
    }

    @Override
    public void update(T oldObj, T newObj) {
        for(int i=0; i < list.size(); i++) {
            if(list.get(i).equals(oldObj))
                list.set(i, newObj);
        }
    }

    @Override
    public T find(T obj) {
        for(T elem : list) {
            if(elem.equals(obj))
                return elem;
        }
        return null;
    }

    @Override
    public List<T> getList() {
        return list;
    }

    @Override
    public int size() {
        return list.size();
    }
}
