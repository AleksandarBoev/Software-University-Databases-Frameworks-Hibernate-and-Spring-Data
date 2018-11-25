package app.services.implementations;

import app.models.Town;
import app.repositories.TownRepository;
import app.services.interfaces.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class TownServiceImpl extends BaseServiceImpl<Town, TownRepository> implements TownService {
    @Autowired
    public TownServiceImpl(TownRepository repository) {
        super(repository);
    }
}
