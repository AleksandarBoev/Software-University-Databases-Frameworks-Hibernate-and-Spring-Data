package softuni.domain.dtos.export_dtos;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Query6CarSaleDto {
    private Query4CarDto car;
    private String customerName;
    private Double discount;
    private BigDecimal price;
    private BigDecimal priceWithDiscount;

    public Query4CarDto getCar() {
        return this.car;
    }

    public void setCar(Query4CarDto car) {
        this.car = car;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceWithDiscount() {
        return this.priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal priceWithDiscount) {
        BigDecimal discountAmount = priceWithDiscount.multiply(BigDecimal.valueOf(this.discount));
        this.priceWithDiscount = priceWithDiscount.subtract(discountAmount);
    }
}
