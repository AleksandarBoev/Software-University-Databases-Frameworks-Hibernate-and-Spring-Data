package p09_animals.entities.animals;

public class Frog extends Animal {
    public Frog(String name, Integer age, String gender) {
        super(name, age, gender);
    }

    @Override
    public String produceSound() {
        return "Frogggg";
    }
}
