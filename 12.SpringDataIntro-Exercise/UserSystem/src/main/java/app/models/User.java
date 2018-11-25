package app.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Identifiable{
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Picture profilePicture;
    private Timestamp registeredOn;
    private Timestamp lastTimeLoggedIn;
    private Integer age;
    private Boolean isDeleted;
    private Town bornIn;
    private Town livingIn;
    private Set<User> friends;
    private Set<Album> albums;

    public User() {
        this.friends = new LinkedHashSet<>();
    }

    public User(String username, String firstName, String lastName, String password, String email,
                Picture profilePicture, Timestamp registeredOn, Timestamp lastTimeLoggedIn,
                Integer age, Boolean isDeleted, Town bornIn, Town livingIn) {
        this();
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.profilePicture = profilePicture;
        this.registeredOn = registeredOn;
        this.lastTimeLoggedIn = lastTimeLoggedIn;
        this.age = age;
        this.isDeleted = isDeleted;
        this.bornIn = bornIn;
        this.livingIn = livingIn;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(length = 30, nullable = false)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Transient
    public String getFullName() {
        String fullName = null;
        if (this.firstName != null && !"".equals(this.firstName))
            fullName = this.firstName;

        if (this.lastName != null && !"".equals(this.lastName))
            fullName += " " + this.lastName;

        return fullName;
    }

    @Column(length = 50, nullable = false)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(nullable = false)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne //TODO potential eager fetch!
    @JoinColumn(name = "profile_picture", referencedColumnName = "id")
    public Picture getProfilePicture() {
        return this.profilePicture;
    }

    public void setProfilePicture(Picture profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Column(name = "registered_on")
    public Timestamp getRegisteredOn() {
        return this.registeredOn;
    }

    public void setRegisteredOn(Timestamp registeredOn) {
        this.registeredOn = registeredOn;
    }

    @Column(name = "last_time_logged_in")
    public Timestamp getLastTimeLoggedIn() {
        return this.lastTimeLoggedIn;
    }

    public void setLastTimeLoggedIn(Timestamp lastTimeLoggedIn) {
        this.lastTimeLoggedIn = lastTimeLoggedIn;
    }

    @Column
    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "is_deleted")
    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @ManyToOne //TODO potential eager fetch!
    @JoinColumn(name = "born_in_town_id", referencedColumnName = "id")
    public Town getBornIn() {
        return this.bornIn;
    }

    public void setBornIn(Town bornIn) {
        this.bornIn = bornIn;
    }

    @ManyToOne //TODO potential eager fetch!
    @JoinColumn(name = "living_in_town_id", referencedColumnName = "id")
    public Town getLivingIn() {
        return this.livingIn;
    }

    public void setLivingIn(Town livingIn) {
        this.livingIn = livingIn;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "friends",
    joinColumns = @JoinColumn(name = "user1", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "user2", referencedColumnName = "id"))
    public Set<User> getFriends() {
        return this.friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public void addFriend(User friend) {
        this.friends.add(friend);
        friend.getFriends().add(this); //TODO since it is ManyToMany this shouldn't be necessary. But it is, because the other person does not get this person in his list of friends!
    }

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL) //the cascade is needed for the deletion
    public Set<Album> getAlbums() {
        return this.albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }
}
