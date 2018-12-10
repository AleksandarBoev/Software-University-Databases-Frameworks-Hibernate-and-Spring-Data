package alararestaurant.domain.dtos.orders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "orders")
public class OrderImportRootDto {
    @XmlElement(name = "order")
    private OrderImportDto[] orderImportDtos;

    public OrderImportDto[] getOrderImportDtos() {
        return this.orderImportDtos;
    }

    public void setOrderImportDtos(OrderImportDto[] orderImportDtos) {
        this.orderImportDtos = orderImportDtos;
    }
}


/*
<orders>
  <order> --> order
    <customer>Garry</customer> --> field of order
    <employee>Maxwell Shanahan</employee> --> field of order
    <date-time>21/08/2017 13:22</date-time> --> field of order
    <type>ForHere</type> --> field of order
    <items> --> orderItems
      <item> --> orderItem1
        <name>Quarter Pounder</name> --> name of item in order item. One orderItem can have 1 item!
        <quantity>2</quantity> --> quantity of item
      </item>
      <item>
        <name>Premium chicken sandwich</name>
        <quantity>2</quantity>
      </item>
      <item>
        <name>Chicken Tenders</name>
        <quantity>4</quantity>
      </item>
      <item>
        <name>Just Lettuce</name>
        <quantity>4</quantity>
      </item>
    </items>
  </order>
 */