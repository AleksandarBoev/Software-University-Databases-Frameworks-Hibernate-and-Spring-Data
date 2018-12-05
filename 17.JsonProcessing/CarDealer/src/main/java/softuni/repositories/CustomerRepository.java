package softuni.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.domain.entities.Customer;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //Get all customers that have bought at least 1 car and get their names,
    // count of cars bought and total money spent on cars. Order the result list by
    // total money spent in descending order then by total cars bought again in descending order.
    // Export the list of customers to JSON in the format provided below.
    //A price of a car is formed by the total price of its parts.
    @Query(value = "" +
            "SELECT cust FROM softuni.domain.entities.Customer cust " +
            "INNER JOIN cust.carsBought " +
            "GROUP BY cust "
    )
    List<Customer> getCustomersByHavingBoughtCars();
}
