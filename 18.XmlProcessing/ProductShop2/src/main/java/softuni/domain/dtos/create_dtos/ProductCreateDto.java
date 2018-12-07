package softuni.domain.dtos.create_dtos;

import softuni.domain.entities.Category;
import softuni.domain.entities.User;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(value = XmlAccessType.FIELD)
public class ProductCreateDto {

    @XmlElement
    private String name;

    @XmlElement
    private BigDecimal price;

    @XmlTransient
    private User buyer;

    @XmlTransient
    private User seller;

    @XmlTransient
    private List<Category> categories;

    public ProductCreateDto() {
        this.categories = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public User getBuyer() {
        return this.buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return this.seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public List<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
