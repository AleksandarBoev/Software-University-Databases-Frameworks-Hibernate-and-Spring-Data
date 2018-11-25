package app.services.implementations;

import app.models.Identifiable;
import app.services.interfaces.BaseService;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseServiceImpl<T extends Identifiable, E extends CrudRepository<T, Long>> implements BaseService<T> {
    private E repository;

    //Can't write @Autowired, because annotations are NOT inherited
    protected BaseServiceImpl(E repository) {
        this.repository = repository;
    }

    public E getRepository() {
        return this.repository;
    }

    @Override
    public T getById(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public List<T> getAll() {
        List<T> elements = new ArrayList<>();

        for (T element : this.repository.findAll())
            elements.add(element);

        return elements;
    }

    /**
     * Registers an element into the database if it is not null and does not have an id.
     * @return true if registration is successful and false if not
     */
    @Override
    public boolean register(T element) {
        if (element == null || element.getId() != null)
            return false;

        this.repository.save(element);
        return true;
    }

    /**
     * Updates an element from the database if it is not null and has an id.
     * @return true if update is successful and false if not
     */
    @Override
    public boolean update(T element) {
        if (element == null || element.getId() == null)
            return false;

        this.repository.save(element);
        return true;
    }

    /**
     * Removes an element from the database if it is not null and if there the element
     * is in the database.
     * @return true if deletion is successful and false if not
     */
    @Override
    public boolean remove(T element) {
        if (element== null || !this.exists(element))
            return false;

        this.repository.delete(element);
        return true;
    }

    /**
     * @return true if element is in the database and false if not
     */
    @Override
    public boolean exists(T element) {
        if (element == null)
            return false;

        return this.repository.existsById(element.getId());
    }
}
