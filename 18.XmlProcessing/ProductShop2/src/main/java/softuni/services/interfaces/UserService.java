package softuni.services.interfaces;

import softuni.domain.entities.User;
import softuni.repositories.UserRepository;

import javax.xml.bind.JAXBException;

public interface UserService extends BaseService<User, UserRepository> {
    void exportUsersWhoHaveSoldAProduct(String fullFilePath) throws JAXBException;

    void exportUsersAndProductsOrderedByProductsCountDescLastNameAsc(String fullFilePath) throws JAXBException;
}
