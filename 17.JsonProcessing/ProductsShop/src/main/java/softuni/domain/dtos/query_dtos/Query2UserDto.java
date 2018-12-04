package softuni.domain.dtos.query_dtos;

import java.util.List;

public class Query2UserDto {
    private String firstName;
    private String lastName;
    private List<Query2ProductDto> soldProducts;

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

    public List<Query2ProductDto> getSoldProducts() {
        return this.soldProducts;
    }

    public void setSoldProducts(List<Query2ProductDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
