package softuni.domain.dtos.query_dtos;

import java.util.List;

public class Query4AllUsersDto {
    private Integer usersCount;
    private List<Query4UserDto> users;

    public Integer getUsersCount() {
        return this.usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public List<Query4UserDto> getUsers() {
        return this.users;
    }

    public void setUsers(List<Query4UserDto> users) {
        this.users = users;
    }
}
