package p09_animals;

import p09_animals.contracts.Factory;
import p09_animals.contracts.Soundable;
import p09_animals.entities.factories.SoundableFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main { //task description is bugged
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Factory<Soundable> soundableFactory = new SoundableFactory(); //TODO factory is not made well

        try {
            String input;
            while (!"Beast!".equals(input = reader.readLine())) {
                String animalType = input;
                String[] animalTokens = reader.readLine().split(" ");
                String[] animalArguments = new String[] {animalType, animalTokens[0], animalTokens[1], animalTokens[2]};

                Soundable animal = soundableFactory.create(animalArguments);
                System.out.println(animal);
            }
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        }
        reader.close();
    }
}
