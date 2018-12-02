package softuni.domain.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CategorySeedDto {
    private String name;

    @NotNull(message = "Category name can't be null!")
    @Min(value = 3, message = "Name should be at least 3 characters long!")
    @Max(value = 15, message = "Name should be no more than 15 characters long!")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
