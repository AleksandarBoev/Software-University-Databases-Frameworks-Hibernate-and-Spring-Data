package p01_define_an_interface_person;

public class Citizen implements Person {
    private String name;
    private int age;

    public Citizen(String name, int age) {
        this.setName(name);
        this.setAge(age);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setAge(int age) {
        this.age = age;
    }
}
