package automappingobjects.services.implementations;

import automappingobjects.domain.entities.Address;
import automappingobjects.repositories.AddressRepository;
import automappingobjects.services.interfaces.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends BaseServiceImpl<Address, AddressRepository> implements AddressService {
    @Autowired
    public AddressServiceImpl(AddressRepository repository) {
        super(repository);
    }
}
