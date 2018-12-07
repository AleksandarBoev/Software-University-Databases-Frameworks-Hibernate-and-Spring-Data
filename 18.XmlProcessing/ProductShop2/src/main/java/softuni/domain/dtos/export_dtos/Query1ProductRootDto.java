package softuni.domain.dtos.export_dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "products")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Query1ProductRootDto {
    @XmlElement(name = "product")
    private Query1ProductDto[] productDtos;

    public Query1ProductDto[] getProductDtos() {
        return this.productDtos;
    }

    public void setProductDtos(Query1ProductDto[] productDtos) {
        this.productDtos = productDtos;
    }
}
