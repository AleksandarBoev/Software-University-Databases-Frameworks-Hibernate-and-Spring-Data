package mostwanted.repository;

import mostwanted.domain.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TownRepository extends JpaRepository<Town, Integer> {
    Boolean existsTownByName(String townName);

    Town findByName(String name);

    @Query(value = ""+
    "SELECT t FROM mostwanted.domain.entities.Town t " +
            "WHERE t.name = :townName"
    )
    List<Town> getTownsByName(@Param(value = "townName") String name);
}
