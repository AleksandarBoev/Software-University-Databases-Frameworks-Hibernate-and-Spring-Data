package softuni.configurations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.utils.implementations.FileReaderUtilImpl;
import softuni.utils.implementations.FileWriterUtilImpl;
import softuni.utils.implementations.ValidatorUtilImpl;
import softuni.utils.interfaces.FileReaderUtil;
import softuni.utils.interfaces.FileWriterUtil;
import softuni.utils.interfaces.ValidatorUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
public class AppConfiguration {
    @Bean
    BufferedReader bufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    @Bean
    FileReaderUtil fileReaderUtil() {
        return new FileReaderUtilImpl();
    }

    @Bean
    FileWriterUtil fileWriterUtil() {
        return new FileWriterUtilImpl();
    }

    @Bean
    Gson gson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    ValidatorUtil validatorUtil() {
        return new ValidatorUtilImpl();
    }
}
