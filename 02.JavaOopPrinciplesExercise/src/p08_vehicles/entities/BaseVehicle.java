package p08_vehicles.entities;

import p08_vehicles.contracts.Vehicle;

import java.text.DecimalFormat;

public abstract class BaseVehicle implements Vehicle {
    private Double fuelQuantity; //just starting fuel quantity. Not maximum quantity according to the task.
    private Double fuelConsumptionPerKilometer;

    protected BaseVehicle(Double fuelQuantity, Double fuelConsumptionPerKilometer) {
        this.setFuelQuantity(fuelQuantity);
        this.setFuelConsumptionPerKilometer(fuelConsumptionPerKilometer);
    }

    @Override
    public String drive(Double distanceInKilometers) {
        Double fuelNeeded = distanceInKilometers * this.fuelConsumptionPerKilometer;
        if (this.fuelQuantity < fuelNeeded) {
            return String.format("%s needs refueling", this.getClass().getSimpleName());
        } else {
            this.fuelQuantity -= fuelNeeded;

            DecimalFormat df = new DecimalFormat("#.###############################");
            String distanceTraveled = df.format(distanceInKilometers); //eliminating trailing zeros

            return String.format("%s travelled %s km", this.getClass().getSimpleName(), distanceTraveled);
        }
    }

    @Override
    public void refuel(Double amountInLiters) {
        this.fuelQuantity += amountInLiters;
    }

    @Override
    public String toString() {
        return String.format("%s: %.2f", this.getClass().getSimpleName(), this.fuelQuantity);
    }

    protected void setFuelQuantity(Double fuelQuantity) {
        this.fuelQuantity = fuelQuantity;
    }

    protected void setFuelConsumptionPerKilometer(Double fuelConsumptionPerKilometer) {
        this.fuelConsumptionPerKilometer = fuelConsumptionPerKilometer;
    }
}
