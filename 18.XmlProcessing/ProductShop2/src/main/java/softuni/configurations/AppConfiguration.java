package softuni.configurations;

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
    public FileReaderUtil fileReaderUtil() {
        return new FileReaderUtilImpl();
    }

    @Bean
    public FileWriterUtil fileWriterUtil() {
        return new FileWriterUtilImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ValidatorUtil validatorUtil() {
        return new ValidatorUtilImpl();
    }

    @Bean
    public BufferedReader bufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}
