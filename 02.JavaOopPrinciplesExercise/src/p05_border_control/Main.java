package p05_border_control;

import p05_border_control.contracts.Identifiable;
import p05_border_control.entities.classes.CitizenImpl;
import p05_border_control.entities.classes.RobotImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<Identifiable> identifiablesToBeChecked = new ArrayList<>();

        String input;
        while (!"End".equals(input = reader.readLine())){
            String[] tokens = input.split(" ");

            Identifiable identifiable = null;
            if (tokens.length == 2) {
                identifiable = new RobotImpl(tokens[0], tokens[1]);
            } else if (tokens.length == 3) {
                identifiable = new CitizenImpl(tokens[0], Integer.parseInt(tokens[1]), tokens[2]);
            }
            identifiablesToBeChecked.add(identifiable);
        }
        String fakeIdIndicator = reader.readLine();
        reader.close();

        for (Identifiable identifiable : identifiablesToBeChecked) {
            if (identifiable.getId().endsWith(fakeIdIndicator)) {
                System.out.println(identifiable.getId());
            }
        }
    }
}
