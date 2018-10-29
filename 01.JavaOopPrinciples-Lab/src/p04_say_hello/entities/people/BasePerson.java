package p04_say_hello.entities.people;

import p04_say_hello.contracts.Person;

public abstract class BasePerson implements Person {
    private String name;

    protected BasePerson(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    protected void setName(String name) {
        this.name = name; //some authentication
    }
}
