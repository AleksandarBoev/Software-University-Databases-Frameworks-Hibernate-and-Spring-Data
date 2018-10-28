package p08_vehicles.entities.vehicles;

import p08_vehicles.entities.BaseVehicle;

public class Car extends BaseVehicle {
    private static final Double INCREASE_TO_FUEL_CONSUMPTION_PER_KILOMETER_DURING_SUMMER = 0.9;

    public Car(Double fuelQuantity, Double fuelConsumptionPerKilometer) {
        super(fuelQuantity, fuelConsumptionPerKilometer);
    }

    @Override
    protected void setFuelConsumptionPerKilometer(Double fuelConsumptionPerKilometer) {
        Double updatedFuelConsumptionPerKilometer =
                fuelConsumptionPerKilometer + INCREASE_TO_FUEL_CONSUMPTION_PER_KILOMETER_DURING_SUMMER;
        super.setFuelConsumptionPerKilometer(updatedFuelConsumptionPerKilometer);
    }
}
