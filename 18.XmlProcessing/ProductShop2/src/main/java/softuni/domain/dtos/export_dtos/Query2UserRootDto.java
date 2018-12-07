package softuni.domain.dtos.export_dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class Query2UserRootDto {
    @XmlElement(name = "user")
    private Query2UserDto[] query2UserDto;

    public Query2UserDto[] getQuery2UserDto() {
        return this.query2UserDto;
    }

    public void setQuery2UserDto(Query2UserDto[] query2UserDto) {
        this.query2UserDto = query2UserDto;
    }
}
