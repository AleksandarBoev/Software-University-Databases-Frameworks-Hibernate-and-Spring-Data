package softuni.services.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.domain.entities.BaseEntity;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface BaseService<E extends BaseEntity, R extends JpaRepository<E, Long>> {
    E getById(Long id);

    Iterable<E> getAll();

    boolean register(E element);

    boolean update(E element);

    void saveAll(Iterable<E> elements);

    String seedDataFromXmlFile(String xmlRelativeFilePath) throws IOException, JAXBException;
}
