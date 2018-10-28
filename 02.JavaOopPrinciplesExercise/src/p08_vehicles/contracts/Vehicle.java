package p08_vehicles.contracts;

public interface Vehicle {
    String drive(Double distanceInKilometers);

    void refuel(Double amountInLiters);
}
