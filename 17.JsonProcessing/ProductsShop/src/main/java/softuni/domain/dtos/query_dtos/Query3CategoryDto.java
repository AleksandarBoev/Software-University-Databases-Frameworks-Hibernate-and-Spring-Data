package softuni.domain.dtos.query_dtos;

import java.math.BigDecimal;

public class Query3CategoryDto {
    private String name;
    private Long productsCount;
    private BigDecimal averagePrice;
    private BigDecimal totalRevenue;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProductsCount() {
        return this.productsCount;
    }

    public void setProductsCount(Long productsCount) {
        this.productsCount = productsCount;
    }

    public BigDecimal getAveragePrice() {
        return this.averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getTotalRevenue() {
        return this.totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
