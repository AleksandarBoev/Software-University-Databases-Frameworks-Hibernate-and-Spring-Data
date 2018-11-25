package app.services.implementations;

import app.models.Album;
import app.repositories.AlbumRepository;
import app.services.interfaces.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
@Primary
public class AlbumServiceImpl extends BaseServiceImpl<Album, AlbumRepository> implements AlbumService {

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository) {
        super(albumRepository);
    }

    @Override
    public void doStuff() {

    }
}
