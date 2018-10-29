package p03_car_shop_extended.cars;

import p03_car_shop_extended.contracts.Sellable;

public abstract class BaseSellable extends BaseCar implements Sellable {
    private Double price;

    protected BaseSellable(String model, String color, Integer horsePower, String countryProduced, Double price) {
        super(model, color, horsePower, countryProduced);
        this.setPrice(price);

    }

    @Override
    public Double getPrice() {
        return this.price;
    }

    private void setPrice(Double price) {
        this.price = price; //some authentication
    }
}
