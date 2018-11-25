package app.services.interfaces;

import app.models.Identifiable;

import java.util.List;

public interface BaseService<T extends Identifiable> {
    T getById(Long id);

    List<T> getAll();

    boolean register(T element);

    boolean update(T element);

    boolean remove(T element);

    boolean exists(T element);
}
