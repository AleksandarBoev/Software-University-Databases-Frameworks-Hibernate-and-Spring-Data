package softuni.domain.dtos.export_dtos;

import java.util.List;

public class Query4CarPartsDto {
    private Query4CarDto car;
    private List<Query4PartDto> parts;

    public Query4CarPartsDto() {
    }

    public Query4CarPartsDto(Query4CarDto car, List<Query4PartDto> parts) {
        this.car = car;
        this.parts = parts;
    }

    public Query4CarDto getCar() {
        return this.car;
    }

    public void setCar(Query4CarDto car) {
        this.car = car;
    }

    public List<Query4PartDto> getParts() {
        return this.parts;
    }

    public void setParts(List<Query4PartDto> parts) {
        this.parts = parts;
    }
}
