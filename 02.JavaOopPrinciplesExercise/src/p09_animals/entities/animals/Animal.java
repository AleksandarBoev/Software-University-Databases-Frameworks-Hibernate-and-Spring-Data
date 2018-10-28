package p09_animals.entities.animals;

import p09_animals.contracts.Soundable;

import java.util.Set;

public class Animal implements Soundable {
    private static final String INVALID_INPUT_MESSAGE = "Invalid input!";
    private static final Set<String> GENDERS = Set.of("Male", "Female");

    private String name;
    private Integer age;
    private String gender;

    public Animal(String name, Integer age, String gender) {
        this.setName(name);
        this.setAge(age);
        this.setGender(gender);
    }

    @Override
    public String produceSound() {
        return "Not implemented!";
    }

    @Override
    public String toString() {
        return String.format("%s%n%s %d %s%n%s",
                this.getClass().getSimpleName(), this.name, this.age, this.gender, this.produceSound());
    }

    private void setName(String name) {
        if (name.length() < 1) {
            throw new IllegalArgumentException(INVALID_INPUT_MESSAGE);
        } else {
            this.name = name;
        }
    }

    private void setAge(Integer age) {
        if (age < 0) {
            throw new IllegalArgumentException(INVALID_INPUT_MESSAGE);
        } else {
            this.age = age;
        }
    }

    protected void setGender(String gender) {
        if (!GENDERS.contains(gender)) {
            throw new IllegalArgumentException(INVALID_INPUT_MESSAGE);
        } else {
            this.gender = gender;
        }
    }
}
