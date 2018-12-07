package softuni.domain.entities;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    //•	Users have an id, first name (optional) and last name (at least 3 characters) and age (optional).
    //•	Users should have many products sold and many products bought.
    //•	Users should have many friends (i.e. users).
    private String firstName;
    private String lastName;
    private Integer age;
    private List<Product> productsSold;
    private List<Product> productsBought;
    private List<User> friends;

    @Column(name = "first_name")
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column
    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Transactional
    @OneToMany(mappedBy = "seller", fetch = FetchType.EAGER)
    public List<Product> getProductsSold() {
        return this.productsSold;
    }

    public void setProductsSold(List<Product> productsSold) {
        this.productsSold = productsSold;
    }

    @OneToMany(mappedBy = "buyer")
    public List<Product> getProductsBought() {
        return this.productsBought;
    }

    public void setProductsBought(List<Product> productsBought) {
        this.productsBought = productsBought;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_friends",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id")
    )
    public List<User> getFriends() {
        return this.friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}
