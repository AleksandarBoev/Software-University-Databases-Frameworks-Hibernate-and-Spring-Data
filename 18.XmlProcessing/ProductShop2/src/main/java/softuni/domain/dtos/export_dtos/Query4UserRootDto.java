package softuni.domain.dtos.export_dtos;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "users")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Query4UserRootDto {
    @XmlAttribute
    private Integer count;

    @XmlElement(name = "user")
    private Query4UserDto[] users;

    public Query4UserRootDto() {
    }

    public Query4UserRootDto(Integer count, Query4UserDto[] users) {
        this.count = count;
        this.users = users;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Query4UserDto[] getUsers() {
        return this.users;
    }

    public void setUsers(Query4UserDto[] users) {
        this.users = users;
    }
}
