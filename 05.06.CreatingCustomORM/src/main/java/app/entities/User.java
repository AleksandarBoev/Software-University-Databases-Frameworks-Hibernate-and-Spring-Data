package app.entities;

import app.annotations.Column;
import app.annotations.Entity;
import app.annotations.PrimaryKey;

import java.util.Date;

@Entity(nameInDb = "newest_users")
public final class User {

    @PrimaryKey(nameInDb = "id")
    private long id;

    @Column(nameInDb = "user_name")
    private String userName;

    @Column(nameInDb = "age")
    private int age;

    public User() {
    }

    public User(long id, String userName, int age) {
        this.id = id;
        this.userName = userName;
        this.age = age;
    }

    public User(String userName, int age) {
        this.setUserName(userName);
        this.setAge(age);
    }

    @Override
    public String toString() {
        return String.format("Id: %d | Username: %s | Age: %d", this.id, this.userName, this.age);
    }

    public long getPrimaryKey() {
        return this.id;
    }

    public void setPrimaryKey(long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
