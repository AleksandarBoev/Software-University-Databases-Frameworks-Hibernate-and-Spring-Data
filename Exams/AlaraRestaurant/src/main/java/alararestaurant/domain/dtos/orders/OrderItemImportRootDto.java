package alararestaurant.domain.dtos.orders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "items")
public class OrderItemImportRootDto {
    @XmlElement(name = "item")
    private OrderItemImportDto[] orderItemImportDtos;

    public OrderItemImportDto[] getOrderItemImportDtos() {
        return this.orderItemImportDtos;
    }

    public void setOrderItemImportDtos(OrderItemImportDto[] orderItemImportDtos) {
        this.orderItemImportDtos = orderItemImportDtos;
    }
}
