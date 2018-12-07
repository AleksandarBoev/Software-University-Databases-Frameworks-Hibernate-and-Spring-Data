package softuni.domain.dtos.create_dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "users")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class UserRootCreateDto {
    @XmlElement(name = "user")
    private UserCreateDto[] users;

    public UserCreateDto[] getUsers() {
        return this.users;
    }

    public void setUsers(UserCreateDto[] users) {
        this.users = users;
    }
}
