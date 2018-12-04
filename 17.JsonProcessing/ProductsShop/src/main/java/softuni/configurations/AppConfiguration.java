package softuni.configurations;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.utils.implementations.FileWriteUtilImpl;
import softuni.utils.interfaces.FileReadUtil;
import softuni.utils.implementations.FileReadUtilImpl;
import softuni.utils.interfaces.FileWriteUtil;

import javax.validation.Validation;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
public class AppConfiguration {
    @Bean
    public FileReadUtil fileReadUtil() {
        return new FileReadUtilImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public Validation validation() {
        return new Validation();
    }

    @Bean
    public BufferedReader bufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    @Bean
    public FileWriteUtil fileWriteUtil() {
        return new FileWriteUtilImpl();
    }
}
