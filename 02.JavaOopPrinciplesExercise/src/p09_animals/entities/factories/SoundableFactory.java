package p09_animals.entities.factories;

import p09_animals.contracts.Factory;
import p09_animals.contracts.Soundable;
import p09_animals.entities.animals.*;

public class SoundableFactory implements Factory {
    private static final String ANIMAL_CLASSES_FILE_PATH = "p09_animals.entities.animals.";

    @Override //TODO try finding the Class.forName way of creating an instance
    public Soundable create(String[] arguments) {
        String animalType = arguments[0];
        String name = arguments[1];
        Integer age = Integer.parseInt(arguments[2]);

        String gender = null;
        if (arguments.length == 4) {
            gender = arguments[3];
        }

        switch (animalType) {
            case "Animal":
                return new Animal(name, age, gender);

            case "Cat":
                return new Cat(name, age, gender);

            case "Tomcat":
                return new Tomcat(name, age);

            case "Kitten":
                return new Kitten(name, age);

            case "Dog":
                return new Dog(name, age, gender);

            case "Frog":
                return new Frog(name, age, gender);

            default:
                return null;
        }
    }
}
