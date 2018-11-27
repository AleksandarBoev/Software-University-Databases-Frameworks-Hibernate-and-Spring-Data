package advancedquerying.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseServiceImpl<E, R extends JpaRepository<E, Long>> {
    private R repository;

    protected BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    protected R getRepository() {
        return this.repository;
    }
}
