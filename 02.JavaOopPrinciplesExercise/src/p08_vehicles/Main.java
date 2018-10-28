package p08_vehicles;

import p08_vehicles.contracts.Vehicle;
import p08_vehicles.entities.vehicles.Car;
import p08_vehicles.entities.vehicles.Truck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final String SPLITTER = "\\s+";

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[] carTokens = reader.readLine().split(SPLITTER);
        Vehicle car = new Car(Double.parseDouble(carTokens[1]), Double.parseDouble(carTokens[2]));

        String[] truckTokens = reader.readLine().split(SPLITTER);
        Vehicle truck = new Truck(Double.parseDouble(truckTokens[1]), Double.parseDouble(truckTokens[2]));

        int numberOfInputs = Integer.parseInt(reader.readLine());

        for (int i = 0; i < numberOfInputs; i++) {
            String[] tokens = reader.readLine().split(SPLITTER);

            String command = tokens[0];
            String vehicleType = tokens[1];
            Vehicle currentVehicle = null;
            if (vehicleType.equals("Car")) {
                currentVehicle = car; //reference type of data
            } else {
                currentVehicle = truck;
            }
            Double fuelOrDistanceValue = Double.parseDouble(tokens[2]);

            switch (command) {
                case "Drive":
                    System.out.println(currentVehicle.drive(fuelOrDistanceValue));
                    break;

                case "Refuel":
                    currentVehicle.refuel(fuelOrDistanceValue);
                    break;
            }
        }
        reader.close();
        System.out.println(car);
        System.out.println(truck);
    }
}
