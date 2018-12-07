package softuni.domain.dtos.create_dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "products")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ProductRootCreateDto {
    @XmlElement(name = "product")
    private ProductCreateDto[] productCreateDtos;

    public ProductCreateDto[] getProductCreateDtos() {
        return this.productCreateDtos;
    }

    public void setProductCreateDtos(ProductCreateDto[] productCreateDtos) {
        this.productCreateDtos = productCreateDtos;
    }
}
