package alararestaurant.repository;

import alararestaurant.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    //Export all orders which are finished by the Burger Flippers:
    // (Burger Flippers is the name of a position)
    //•	Extract from the database, employees’ names, orders’ customers
    // and items in the orders with their name, price and quantity.
    //•	Order them by employee name, and by order id.
    @Query(value = "" +
            "SELECT " +
            "   e.name, " +
            "   o " +
            "FROM alararestaurant.domain.entities.Order o " +
            "INNER JOIN o.employee e " +
            "INNER JOIN e.position p " +
            "INNER JOIN o.orderItems oi " +
            "WHERE p.name = :positionName " +
            "GROUP BY e " +
            "ORDER BY e.name ASC, o.id ASC "
    )
    List<Object[]> getEmployeeNameAndOrders(@Param(value = "positionName") String positionName);
}
