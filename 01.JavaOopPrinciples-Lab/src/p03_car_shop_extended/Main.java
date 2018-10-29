package p03_car_shop_extended;

import p03_car_shop_extended.cars.Audi;
import p03_car_shop_extended.cars.Seat;
import p03_car_shop_extended.contracts.Rentable;
import p03_car_shop_extended.contracts.Sellable;

public class Main {
    public static void main(String[] args) {
        Sellable seat = new Seat("Leon", "Gray", 110, "Spain", 11111.1);
        Rentable audi = new Audi("Leon", "Gray", 110, "Spain", 3, 99.9);

        System.out.println(seat);
        System.out.println(audi);
    }
}
