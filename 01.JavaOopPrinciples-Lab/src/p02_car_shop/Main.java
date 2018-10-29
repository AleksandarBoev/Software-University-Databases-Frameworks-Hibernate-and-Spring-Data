package p02_car_shop;

import p02_car_shop.contracts.Car;
import p02_car_shop.entities.cars.Seat;

public class Main {
    public static void main(String[] args) {
        Car seat = new Seat("Leon", "gray", 100, "Spain");
        System.out.println(seat);
    }
}
