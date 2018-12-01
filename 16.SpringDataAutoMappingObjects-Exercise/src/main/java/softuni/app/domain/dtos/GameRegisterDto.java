package softuni.app.domain.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

//used to validate info on a game and register it in the db
public class GameRegisterDto {
    private String title;
    private String trailerUrl;
    private String imageThumbNail;
    private Double size;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrailerUrl() {
        return this.trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getImageThumbNail() {
        return this.imageThumbNail;
    }

    public void setImageThumbNail(String imageThumbNail) {
        this.imageThumbNail = imageThumbNail;
    }

    public Double getSize() {
        return this.size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
