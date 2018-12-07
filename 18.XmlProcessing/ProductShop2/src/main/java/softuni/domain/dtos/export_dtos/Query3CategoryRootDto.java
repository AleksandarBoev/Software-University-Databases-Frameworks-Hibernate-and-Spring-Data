package softuni.domain.dtos.export_dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class Query3CategoryRootDto {
    @XmlElement(name = "category")
    Query3CategoryDto[] query3CategoryDtos;

    public Query3CategoryDto[] getQuery3CategoryDtos() {
        return this.query3CategoryDtos;
    }

    public void setQuery3CategoryDtos(Query3CategoryDto[] query3CategoryDtos) {
        this.query3CategoryDtos = query3CategoryDtos;
    }
}
