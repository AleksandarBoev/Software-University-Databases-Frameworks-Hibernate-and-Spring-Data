package softuni.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.app.domain.entities.User;

/*
    App will work with one user at a time.
 */

@Controller
public class AppController implements CommandLineRunner {
    private User user;

    @Override
    public void run(String... args) throws Exception {

    }


}
