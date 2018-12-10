package alararestaurant.domain.dtos.orders;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "order")
public class OrderImportDto {
    @XmlElement(name = "customer")
    private String customerName;

    @XmlElement(name = "employee")
    private String employeeName;

    @XmlElement(name = "date-time")
    private String dateTime;

    @XmlElement(name = "type")
    private String type;

    @XmlElement(name = "items")
    private OrderItemImportRootDto orderItemImportRootDto;


    //•	customer – text (required)
    @NotNull
    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    //•	employee – The employee who will process the order (required)
    @Size(min = 3, max = 30)
    @NotNull
    public String getEmployeeName() {
        return this.employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    //•	dateTime – date and time of the order (required)
    @NotNull
    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    //•	type – OrderType enumeration with possible values:
    // “ForHere, ToGo (default: ForHere)” (required)
    @NotNull
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OrderItemImportRootDto getOrderItemImportRootDto() {
        return this.orderItemImportRootDto;
    }

    public void setOrderItemImportRootDto(OrderItemImportRootDto orderItemImportRootDto) {
        this.orderItemImportRootDto = orderItemImportRootDto;
    }
}
