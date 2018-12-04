package softuni.domain.dtos.seed_dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SupplierSeedDto {
    private String name;
    private Boolean isImporter;

    @NotNull(message = "Supplier name can't be null!")
    @Size(min = 1, message = "Name can't be an empty string!")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "'isImporter' must be true or false! Not null!")
    public Boolean getIsImporter() {
        return this.isImporter;
    }

    public void setIsImporter(Boolean isImporter) {
        this.isImporter = isImporter;
    }

    @Override
    public String toString() {
        return "SupplierSeedDto{" +
                "name = '" + this.name + '\'' +
                ", isImporter = " + this.isImporter +
                '}';
    }
}
