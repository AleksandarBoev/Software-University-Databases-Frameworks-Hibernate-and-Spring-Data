package softuni.domain.dtos.create_dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(value = XmlAccessType.FIELD)
public class CategoryCreateDto {
    @XmlElement
    @NotNull(message = "Category name can't be null!")
    @Size(min = 1, message = "Category name can't be empty!")
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
