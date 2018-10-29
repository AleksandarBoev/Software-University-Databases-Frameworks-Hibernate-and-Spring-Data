package p03_car_shop_extended.cars;

import p03_car_shop_extended.contracts.Car;

public abstract class BaseCar implements Car {
    private String model;
    private String color;
    private Integer horsePower;
    private String countryProduced;

    protected BaseCar(String model, String color, Integer horsePower, String countryProduced) {
        this.setModel(model);
        this.setColor(color);
        this.setHorsePower(horsePower);
        this.setCountryProduced(countryProduced);
    }

    @Override
    public String getModel() {
        return this.model;
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public Integer getHorsePower() {
        return this.horsePower;
    }

    @Override
    public String toString() {
        return String.format("This is a %s %s, model %s and has %d horse power. It has been produced in %s.",
                this.color, this.getClass().getSimpleName(), this.model, this.horsePower, this.countryProduced);
    }

    private void setModel(String model) {
        this.model = model; //some authentication
    }

    private void setColor(String color) {
        this.color = color; //some authentication
    }

    private void setHorsePower(Integer horsePower) {
        this.horsePower = horsePower; //some authentication
    }

    private void setCountryProduced(String countryProduced) {
        this.countryProduced = countryProduced; //some authentication
    }
}
