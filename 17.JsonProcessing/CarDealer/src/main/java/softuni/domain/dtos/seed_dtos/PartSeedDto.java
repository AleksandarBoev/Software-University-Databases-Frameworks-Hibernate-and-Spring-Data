package softuni.domain.dtos.seed_dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class PartSeedDto {
    private String name;
    private BigDecimal price;
    private Integer quantity;

    @NotNull(message = "Part name can't be null!")
    @Size(min = 1, message = "Part name can't be an empty string!")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Positive(message = "Price should be positive!")
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Positive(message = "Part quantity should be positive!")
    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "PartSeedDto{" +
                "name = '" + this.name + '\'' +
                ", price = " + this.price +
                ", quantity = " + this.quantity +
                '}';
    }
}
