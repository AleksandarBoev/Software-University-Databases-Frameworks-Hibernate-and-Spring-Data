package softuni.domain.dtos.seed_dtos;

import softuni.domain.entities.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ProductSeedDto {
    private String name;
    private BigDecimal price;
    private User buyer;
    private User seller;

    @NotNull(message = "Product price can't be null!")
    @Size(min = 3, message = "Product name should be at least 3 characters long!")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Product price can't be null!")
    @Positive(message = "Product price should be a positive number!")
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public User getBuyer() {
        return this.buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    @NotNull(message = "Product needs a seller!")
    public User getSeller() {
        return this.seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    @Override
    public String toString() {
        String buyerNames = "null";
        if (this.buyer != null)
            buyerNames = this.buyer.getFirstName() + " " + this.buyer.getLastName();

        String sellerNames = this.seller.getFirstName() + " " + this.seller.getLastName();

        return "ProductSeedDto {" +
                "name = '" + this.name + '\'' +
                ", price = " + this.price +
                ", buyer = " + buyerNames +
                ", seller = " + sellerNames +
                '}';
    }
}
