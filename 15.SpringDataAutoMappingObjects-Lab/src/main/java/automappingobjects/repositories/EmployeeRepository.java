package automappingobjects.repositories;

import automappingobjects.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "" +
            "SELECT e FROM automappingobjects.domain.entities.Employee e " +
            "WHERE FUNCTION('YEAR', e.birthDate) < :year"
    )
    List<Employee> findEmployeesByBirthDateBefore(@Param(value = "year") Integer year);
}
