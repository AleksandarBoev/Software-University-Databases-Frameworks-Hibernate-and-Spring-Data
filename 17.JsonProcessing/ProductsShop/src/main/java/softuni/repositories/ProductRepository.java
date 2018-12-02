package softuni.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.domain.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
