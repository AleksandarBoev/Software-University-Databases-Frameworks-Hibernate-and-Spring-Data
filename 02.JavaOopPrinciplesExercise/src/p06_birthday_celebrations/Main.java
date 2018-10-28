package p06_birthday_celebrations;

import p06_birthday_celebrations.classes.CitizenImpl;
import p06_birthday_celebrations.classes.PetImpl;
import p06_birthday_celebrations.contracts.Birthable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
        Gets runtime error in one of the tests if I use LocalDate.
        Reason: provided year in input is with more/less than 4 digits.
         */

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        List<Birthable> birthablesList = new ArrayList<>();
//        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String input;
        while (!"End".equals(input = reader.readLine())) {
            String[] tokens = input.split(" ");

            Birthable birthable = null;
            switch (tokens[0]) {
                case "Citizen":
                    birthable = new CitizenImpl(
                            tokens[1], Integer.parseInt(tokens[2]), tokens[3], tokens[4]);
                    break;

                case "Pet":
                    birthable = new PetImpl(tokens[1], tokens[2]);
                    break;
                default:
                    continue; //don't add robots
            }
            birthablesList.add(birthable);
        }
//        Integer specificYear = Integer.parseInt(reader.readLine());
        String specificYearString = reader.readLine();
        reader.close();


        for (Birthable birthable : birthablesList) {
            if (birthable.extractYearOfBirthDateString().equals(specificYearString)) {
                System.out.println(birthable.getBirthDateString());
            }
        }
    }
}
