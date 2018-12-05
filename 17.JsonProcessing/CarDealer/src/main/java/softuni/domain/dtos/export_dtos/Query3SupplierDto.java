package softuni.domain.dtos.export_dtos;

public class Query3SupplierDto {
    private Long id;
    private String name;
    private Integer partsCount;

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

    public Integer getPartsCount() {
        return this.partsCount;
    }

    public void setPartsCount(Integer partsCount) {
        this.partsCount = partsCount;
    }
}
