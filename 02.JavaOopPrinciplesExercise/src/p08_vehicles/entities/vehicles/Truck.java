package p08_vehicles.entities.vehicles;

import p08_vehicles.entities.BaseVehicle;

public class Truck extends BaseVehicle {
    private static final Double INCREASE_TO_FUEL_CONSUMPTION_PER_KILOMETER_DURING_SUMMER = 1.6;

    public Truck(Double fuelQuantity, Double fuelConsumptionPerKilometer) {
        super(fuelQuantity, fuelConsumptionPerKilometer);
    }

    @Override
    public void refuel(Double amount) {
        Double realAmount = amount * 0.95;
        super.refuel(realAmount);
    }

    @Override
    protected void setFuelConsumptionPerKilometer(Double fuelConsumptionPerKilometer) {
        Double updatedFuelConsumption =
                fuelConsumptionPerKilometer + INCREASE_TO_FUEL_CONSUMPTION_PER_KILOMETER_DURING_SUMMER;
        super.setFuelConsumptionPerKilometer(updatedFuelConsumption);
    }
}
