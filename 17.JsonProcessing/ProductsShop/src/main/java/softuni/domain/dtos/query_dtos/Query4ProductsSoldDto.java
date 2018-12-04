package softuni.domain.dtos.query_dtos;

import java.util.List;

public class Query4ProductsSoldDto {
    private Integer count;
    private List<Query4ProductDto> products;

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Query4ProductDto> getProducts() {
        return this.products;
    }

    public void setProducts(List<Query4ProductDto> products) {
        this.products = products;
    }
}
