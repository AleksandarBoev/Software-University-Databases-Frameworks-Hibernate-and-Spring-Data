package softuni.domain.dtos.seed_dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class CustomerSeedDto {
    private String name;
    private String birthDate;
    private Boolean isYoungDriver;

    @NotNull(message = "Customer name can't be null!")
    @Size(min = 1, message = "Customer name can't be an empty string!")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getYoungDriver() {
        return this.isYoungDriver;
    }

    public void setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    @Override
    public String toString() {
        return "CustomerSeedDto{" +
                "name = '" + this.name + '\'' +
                ", birthDate = " + this.birthDate +
                ", isYoungDriver = " + this.isYoungDriver +
                '}';
    }
}
