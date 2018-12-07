package softuni.domain.dtos.create_dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "categories")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CategoryRootCreateDto {
    @XmlElement(name = "category")
    private CategoryCreateDto[] categoryCreateDtos;

    public CategoryCreateDto[] getCategoryCreateDtos() {
        return this.categoryCreateDtos;
    }

    public void setCategoryCreateDtos(CategoryCreateDto[] categoryCreateDtos) {
        this.categoryCreateDtos = categoryCreateDtos;
    }
}
