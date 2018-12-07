package softuni.domain.dtos.export_dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(value = XmlAccessType.FIELD)
public class Query4ProductRootDto {
    @XmlAttribute
    private Integer count;

    @XmlElement(name = "product")
    private Query4ProductDto[] query4ProductDto;

    public Query4ProductRootDto() {
    }

    public Query4ProductRootDto(Integer count, Query4ProductDto[] query4ProductDto) {
        this.count = count;
        this.query4ProductDto = query4ProductDto;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Query4ProductDto[] getQuery4ProductDto() {
        return this.query4ProductDto;
    }

    public void setQuery4ProductDto(Query4ProductDto[] query4ProductDto) {
        this.query4ProductDto = query4ProductDto;
    }
}
