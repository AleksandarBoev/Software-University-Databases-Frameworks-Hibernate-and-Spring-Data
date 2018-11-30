package automappingobjects.services.implementations;

import automappingobjects.services.interfaces.BaseService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class BaseServiceImpl<E, R extends JpaRepository<E, Long>> implements BaseService<E, R> {
    private R repository;

    protected BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    protected R getRepository() {
        return this.repository;
    }

    @Override
    public E getById(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public List<E> getAll() {
        return this.repository.findAll();
    }

    @Override
    public void save(E element) {
        this.repository.save(element);
    }
}
