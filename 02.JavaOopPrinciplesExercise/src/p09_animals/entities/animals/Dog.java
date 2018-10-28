package p09_animals.entities.animals;

public class Dog extends Animal {
    public Dog(String name, Integer age, String gender) {
        super(name, age, gender);
    }

    @Override
    public String produceSound() {
        return "BauBau";
    }
}
