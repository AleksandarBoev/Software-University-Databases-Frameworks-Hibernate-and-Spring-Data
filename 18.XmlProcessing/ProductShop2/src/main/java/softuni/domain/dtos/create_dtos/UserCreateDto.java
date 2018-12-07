package softuni.domain.dtos.create_dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;

//@XmlRootElement(name = "user")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class UserCreateDto {
    //•	Users have an id, first name (optional) and last name (at least 3 characters) and age (optional).

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    @NotNull(message = "User should have a last name!")
    @Size(min = 3, message = "Last name should be at least 3 characters long!")
    private String lastName;

    @XmlAttribute
    @Positive(message = "Age should be a positive number!")
    private Integer age;

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

    @Override
    public String toString() {
        return "UserCreateDto{" +
                "firstName = '" + this.firstName + '\'' +
                ", lastName = '" + this.lastName + '\'' +
                ", age = " + this.age +
                '}';
    }
}
