package p02_car_shop.contracts;

public interface Car {
    int TIRES = 4; //don't think this is good practise

    String getModel();

    String getColor();

    Integer getHorsePower();
}
