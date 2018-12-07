package softuni.domain.dtos.export_dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Query2UserDto {
    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlElement(name = "sold-products")
    private Query2ProductRootDto query2ProductRootDto;

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Query2ProductRootDto getQuery2ProductRootDto() {
        return this.query2ProductRootDto;
    }

    public void setQuery2ProductRootDto(Query2ProductRootDto query2ProductRootDto) {
        this.query2ProductRootDto = query2ProductRootDto;
    }
}
