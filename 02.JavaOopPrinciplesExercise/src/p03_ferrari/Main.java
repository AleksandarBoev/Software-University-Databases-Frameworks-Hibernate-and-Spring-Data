package p03_ferrari;

import p03_ferrari.contracts.Car;
import p03_ferrari.entities.Ferrari;
import p03_ferrari.entities.Model488Spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.instrument.IllegalClassFormatException;

public class Main {
    public static void main(String[] args) throws IllegalClassFormatException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String driverName = reader.readLine();
        reader.close();
        Car car = new Model488Spider(driverName);
        System.out.println(car);

        String ferrariName = Ferrari.class.getSimpleName();
        String carInterface = Car.class.getSimpleName();
        boolean isCreated = Car.class.isInterface();
        if (!isCreated) {
            throw new IllegalClassFormatException("No interface created!");
        }
    }
}
