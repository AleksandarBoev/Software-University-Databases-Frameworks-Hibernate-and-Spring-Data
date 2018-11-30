package automappingobjects.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/*
    What does a configuration do: Whenever a constructor wants a BufferedReader/Scanner type
    it gets them from here. Ofc the constructor has to have the @Autowired annotation.
 */
@Configuration
public class ConsoleReaderConfiguration {

    @Bean
    public BufferedReader reader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }
}
