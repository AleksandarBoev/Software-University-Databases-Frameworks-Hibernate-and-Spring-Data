package softuni.domain.dtos.export_dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Query2ProductRootDto {
    @XmlElement(name = "product")
    private Query2ProductDto[] productDtos;

    public Query2ProductDto[] getProductDtos() {
        return this.productDtos;
    }

    public void setProductDtos(Query2ProductDto[] productDtos) {
        this.productDtos = productDtos;
    }
}
