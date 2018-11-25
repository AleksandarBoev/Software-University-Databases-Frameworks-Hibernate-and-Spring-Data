package app.models;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "albums")
public class Album implements Identifiable {
    private Long id;
    private String name;
    private String backgroundColor;
    private Boolean isPublic;
    private User owner;
    private Set<Picture> pictures;

    public Album() {
        this.pictures = new LinkedHashSet<>(); //i guess the time of adding a picture should be kept
    }

    public Album(String name, String backgroundColor, Boolean isPublic, User owner) {
        this();
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.isPublic = isPublic;
        this.owner = owner;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "background_color")
    public String getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Column(name = "is_public")
    public Boolean getPublic() {
        return this.isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "albums_pictures",
            joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "picture_id", referencedColumnName = "id")
    )
    public Set<Picture> getPictures() {
        return this.pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    public void addPicture(Picture picture) {
        this.pictures.add(picture);
    }
}
