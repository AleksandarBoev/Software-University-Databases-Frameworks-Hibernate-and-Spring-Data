package softuni.domain.dtos.query_dtos;

import java.util.List;

public class Query4UserDto {
    private String firstName;
    private String lastName;
    private Integer age;
    private Query4ProductsSoldDto soldProducts;

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Query4ProductsSoldDto getSoldProducts() {
        return this.soldProducts;
    }

    public void setSoldProducts(Query4ProductsSoldDto soldProducts) {
        this.soldProducts = soldProducts;
    }
}
