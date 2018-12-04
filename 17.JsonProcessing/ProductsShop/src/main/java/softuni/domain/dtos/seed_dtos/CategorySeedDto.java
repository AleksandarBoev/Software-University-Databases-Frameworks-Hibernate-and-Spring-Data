package softuni.domain.dtos.seed_dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategorySeedDto {
    private String name;

    @NotNull(message = "Category name can't be null!")
    @Size(min = 3, message = "Name should be at least 3 characters long!")
    @Size(max = 15, message = "Name should be no more than 15 characters long!")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategorySeedDto{" +
                "name = '" + this.name + '\'' +
                '}';
    }
}
