package softuni.domain.dtos.export_dtos;

import softuni.domain.entities.Sale;

import java.time.LocalDateTime;
import java.util.List;

public class Query1CustomerDto {
    private Long id;
    private String name;
    private String birthDate;
    private Boolean isYoungDriver;
    private List<String> sales;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<String> getSales() {
        return this.sales;
    }

    public void setSales(List<String> sales) {
        this.sales = sales;
    }
}
