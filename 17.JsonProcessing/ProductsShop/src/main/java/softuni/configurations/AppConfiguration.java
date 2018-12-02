package softuni.configurations;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.utils.interfaces.FileReadUtil;
import softuni.utils.implementations.FileReadUtilImpl;

import javax.validation.Validation;

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
}
