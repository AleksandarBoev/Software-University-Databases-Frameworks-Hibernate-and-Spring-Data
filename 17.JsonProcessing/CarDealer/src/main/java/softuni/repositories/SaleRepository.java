package softuni.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.domain.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
