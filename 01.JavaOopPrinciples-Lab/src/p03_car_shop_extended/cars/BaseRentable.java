package p03_car_shop_extended.cars;

import p03_car_shop_extended.contracts.Rentable;

public abstract class BaseRentable extends BaseCar implements Rentable {
    private Integer minRentDay;
    private Double pricePerDay;

    protected BaseRentable(String model, String color, Integer horsePower, String countryProduced, Integer minRentDay, Double pricePerDay) {
        super(model, color, horsePower, countryProduced);
        this.setMinRentDay(minRentDay);
        this.setPricePerDay(pricePerDay);
    }

    @Override
    public Integer getMinRentDay() {
        return this.minRentDay;
    }

    @Override
    public Double getPricePerDay() {
        return this.pricePerDay;
    }

    private void setMinRentDay(Integer minRentDay) {
        this.minRentDay = minRentDay;//some authentication
    }

    private void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;//some authentication
    }
}
