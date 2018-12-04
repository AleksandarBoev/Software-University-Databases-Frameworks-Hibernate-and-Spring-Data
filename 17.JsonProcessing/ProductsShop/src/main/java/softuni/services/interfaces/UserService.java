package softuni.services.interfaces;

public interface UserService {
    String seedUsers(String json);

    String getJsonUsersWithAtLeastOneSoldItem();

    String getJsonProductSoldOrderByProductsSoldDescLastNameAsc();
}
