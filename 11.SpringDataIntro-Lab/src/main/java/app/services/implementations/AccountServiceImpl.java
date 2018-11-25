package app.services.implementations;

import app.models.Account;
import app.repositories.AccountRepository;
import app.services.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Primary
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean withdrawMoney(BigDecimal amount, long id) {
        Account account = this.accountRepository.findById(id).orElse(null);

        if (account == null || this.isNegative(amount) || !this.accountHasOwner(account) || account.getBalance().compareTo(amount) < 0)
            return false;

        account.setBalance(account.getBalance().subtract(amount));
        return true;
    }

    @Override
    public boolean withdrawMoney(BigDecimal amount, Account account) {
        return this.withdrawMoney(amount, account.getId());
    }

    @Override
    public boolean transferMoney(BigDecimal amount, long id) {
        Account account = this.accountRepository.findById(id).orElse(null);

        if (account == null || !this.accountHasOwner(account) || !this.isNegative(amount))
            return false;

        account.setBalance(account.getBalance().add(amount));
        return true;
    }

    @Override
    public boolean transferMoney(BigDecimal amount, Account account) {
        return this.transferMoney(amount, account.getId());
    }

    @Override
    public boolean registerAccount(Account account) {
        if (this.isNegative(account.getBalance()))
            return false;

        this.accountRepository.save(account);
        return true;
    }


    private boolean isNegative(BigDecimal money) {
        return money.compareTo(BigDecimal.ZERO) < 0;
    }

    private boolean accountIsPresent(long id) {
        return this.accountRepository.findById(id).orElse(null) != null;
    }

    private boolean accountHasOwner(Account account) {
        return account.getOwner() != null;
    }
}
