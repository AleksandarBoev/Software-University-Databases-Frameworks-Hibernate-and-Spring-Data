package softuni.domain.dtos.seed_dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserSeedDto {
    private String firstName;
    private String lastName;
    private Integer age;

    public UserSeedDto() {
    }

    @Size(min = 1, message = "First name can't be an empty string!")
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull(message = "Last name can't be null!")
    @Size(min = 3, message = "Last name should be at least 3 characters long!")
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Min(value = 1, message = "Age should be a positive number!")
    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User {" +
                "firstName = '" + this.firstName + '\'' +
                ", lastName = '" + this.lastName + '\'' +
                ", age = " + this.age +
                '}';
    }
}
