package app.models;

import javax.persistence.*;

@Entity
@Table(name = "pictures")
public class Picture implements Identifiable{
    private Long id;
    private String title;
    private String caption;
    private String pathToFile;

    public Picture() {
    }

    public Picture(String title, String caption, String pathToFile) {
        this.title = title;
        this.caption = caption;
        this.pathToFile = pathToFile;
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
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column
    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Column(name = "path_to_file")
    public String getPathToFile() {
        return this.pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }
}
