package app.services.implementations;

import app.models.Picture;
import app.repositories.PictureRepository;
import app.services.interfaces.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class PictureServiceImpl extends BaseServiceImpl<Picture, PictureRepository> implements PictureService {
    @Autowired
    public PictureServiceImpl(PictureRepository repository) {
        super(repository);
    }
}
