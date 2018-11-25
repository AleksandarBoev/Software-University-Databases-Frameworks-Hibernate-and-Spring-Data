package app;

import app.models.Account;
import app.models.User;
import app.repositories.AccountRepository;
import app.repositories.UserRepository;
import app.services.implementations.AccountServiceImpl;
import app.services.implementations.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@SpringBootApplication
@Component
public class ConsoleRunner implements CommandLineRunner {
    private UserServiceImpl userService;
    private UserRepository userRepository;
    private AccountServiceImpl accountService;
    private AccountRepository accountRepository;

    @Autowired
    public ConsoleRunner(UserServiceImpl userService, UserRepository userRepository, AccountServiceImpl accountService, AccountRepository accountRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.fillWithData();
        this.test();
    }

    private void fillWithData() {
        User user1 = new User("Aleksandar", 23);
        User user2 = new User("Gosho", 32);
        User user3 = new User("Pesho", 50);

        this.userService.registerUser(user1);
        this.userService.registerUser(user2);
        this.userService.registerUser(user3);

        user1 = this.userRepository.getByUserName("Aleksandar"); //with generated id
        user2 = this.userRepository.getByUserName("Gosho");
        user3 = this.userRepository.getByUserName("Pesho");

        Account account1 = new Account(new BigDecimal("1000"), user1);
        Account account2 = new Account(new BigDecimal("2000"), user1);
        Account account3 = new Account(new BigDecimal("4444"), user2);
        Account account4 = new Account(new BigDecimal("5555"), user3);
        Account account5 = new Account(new BigDecimal("6666"), null);


        this.accountService.registerAccount(account1);
        this.accountService.registerAccount(account2);
        this.accountService.registerAccount(account3);
        this.accountService.registerAccount(account4);
    }

    private void test() {
        User invalidUser1 = this.userRepository.findById(1L).orElse(null);
        System.out.println("Attempt to register already existing user: " + this.userService.registerUser(invalidUser1)); //TODO

        User invalidUser2 = new User("Aleksandar", 23);
        System.out.println("Attempt to register user with a non unique name: " + this.userService.registerUser(invalidUser2));

        BigDecimal negativeAmount = new BigDecimal("-10");
        BigDecimal bigAmount = new BigDecimal("100000000000");

        Account accountWithoutOwner = new Account(new BigDecimal("10000"), null);
        this.accountRepository.save(accountWithoutOwner);
        Account accountNotInDb = new Account(new BigDecimal("10000"), this.userRepository.findById(1L).orElse(null));

        System.out.println("Attempt to withdraw a negative amount: " + this.accountService.withdrawMoney(negativeAmount, 1L)); //TODO
        System.out.println("Attempt to do anything to an account without an owner: " + this.accountService.withdrawMoney(new BigDecimal("1"), accountWithoutOwner));
        System.out.println("Attempt to do anything to an account, not in the database: " + this.accountService.transferMoney(new BigDecimal("1"), accountNotInDb));
        System.out.println("Attempt to withdraw more money than possible: " + this.accountService.withdrawMoney(bigAmount, this.accountRepository.findById(1L).orElse(null)));
    }
}
