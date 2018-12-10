package alararestaurant.domain.dtos.import_dtos.items;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ItemImportDto {
    //•	name – text with min length 3 and max length 30 (required, unique)
    //•	category – the item’s category (required) - > name – text with
    // min length 3 and max length 30 (required)
    //•	price – decimal (non-negative, minimum value: 0.01, required)

    @Expose
    private String name;

    @Expose
    private BigDecimal price;

    @Expose
    private String category;


    @NotNull
    @Size(min = 3, max = 30)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @DecimalMin("0.01")
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull
    @Size(min = 3, max = 30)
    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
