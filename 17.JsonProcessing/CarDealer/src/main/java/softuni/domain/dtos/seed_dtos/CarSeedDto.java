package softuni.domain.dtos.seed_dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigInteger;

public class CarSeedDto {
    private String make;
    private String model;
    private BigInteger travelledDistance;

    @NotNull(message = "Car make can't be null!")
    @Size(min = 1, message = "Car make can't be an empty string!")
    public String getMake() {
        return this.make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @NotNull(message = "Car model can't be null!")
    @Size(min = 1, message = "Car model can't be an empty string!")
    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Positive(message = "Cars travelled distance should be positive!")
    public BigInteger getTravelledDistance() {
        return this.travelledDistance;
    }

    public void setTravelledDistance(BigInteger travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    @Override
    public String toString() {
        return "CarSeedDto{" +
                "make = '" + this.make + '\'' +
                ", model = '" + this.model + '\'' +
                ", travelledDistance = " + this.travelledDistance +
                '}';
    }
}
