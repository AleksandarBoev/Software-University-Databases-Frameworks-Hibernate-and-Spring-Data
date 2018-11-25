package app.repositories;

import app.models.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> getAccountByBalanceBetween(BigDecimal minMoney, BigDecimal maxMoney);

    Account getAccountByOwnerIsNull();
}
