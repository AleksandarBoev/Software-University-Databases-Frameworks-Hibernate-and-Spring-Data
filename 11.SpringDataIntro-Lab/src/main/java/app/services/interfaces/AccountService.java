package app.services.interfaces;

import app.models.Account;

import java.math.BigDecimal;

public interface AccountService {
    boolean withdrawMoney(BigDecimal amount, long id);

    boolean withdrawMoney(BigDecimal amount, Account account);

    boolean transferMoney(BigDecimal amount, long id);

    boolean transferMoney(BigDecimal amount, Account account);

    boolean registerAccount(Account account);
}
