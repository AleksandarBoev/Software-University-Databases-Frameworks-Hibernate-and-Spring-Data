package p09_animals.entities.animals;

public class Cat extends Animal {
    public Cat(String name, Integer age, String gender) {
        super(name, age, gender);
    }

    @Override
    public String produceSound() {
        return "MiauMiau";
    }
}
