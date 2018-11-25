package app.services.interfaces;

import java.util.List;

public interface BaseService<T> {
    T getById(Long id);

    T getByName(String name);

    boolean register(T element);

    List<T> getAll();

    boolean update(T element);
}
