package app.services.implementations;

import app.models.Country;
import app.repositories.CountryRepository;
import app.services.interfaces.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CountryServiceImpl extends BaseServiceImpl<Country, CountryRepository> implements CountryService {
    @Autowired
    public CountryServiceImpl(CountryRepository repository) {
        super(repository);
    }
}
