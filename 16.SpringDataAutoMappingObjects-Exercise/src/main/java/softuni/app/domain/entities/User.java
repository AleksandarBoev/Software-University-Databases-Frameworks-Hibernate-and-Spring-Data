package softuni.app.domain.entities;

import softuni.app.domain.enums.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String fullName;
    private String email;
    private String password;
    private Role role;
    private List<Order> orders;
    private List<Game> games;

    public User() {
        this.orders = new ArrayList<>();
        this.games = new ArrayList<>();
        this.role = Role.NORMAL; //every new user starts off as a normal one
    }

    @Column(name = "full_name", length = 40)
    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(length = 50, unique = true)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(length = 30)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Enumerated(value = EnumType.STRING)
    @Column
    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    public List<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    //TODO not sure how to do this part
    public List<Game> getGames() {
        return this.games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
