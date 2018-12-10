package alararestaurant.domain.dtos.orders;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "item")
public class OrderItemImportDto {
    @XmlElement(name = "name")
    private String itemName;

    @XmlElement(name = "quantity")
    private Integer quantity;

    //•	name – text with min length 3 and max length 30 (required, unique)
    @NotNull
    @Size(min = 3, max = 30)
    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    //•	quantity – the quantity of the item in the order (required, non-negative and non-zero)
    @NotNull
    @Positive
    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
