package automappingobjects.services.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseService<E, R extends JpaRepository<E, Long>> {
    E getById(Long id);

    List<E> getAll();

    void save(E element);
}
