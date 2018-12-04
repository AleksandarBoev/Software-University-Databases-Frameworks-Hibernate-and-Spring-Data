package softuni.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.domain.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
