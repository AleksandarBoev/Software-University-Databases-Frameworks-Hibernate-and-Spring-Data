package app.root_entities;

import app.contracts.Label;

import javax.persistence.*;

@Entity
@Table(name = "labels")
public class BasicLabel implements Label {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", length = 30)
    private String title;

    @Column(name = "sub_title", length = 60)
    private String subTitle;

    @OneToOne(mappedBy = "label", targetEntity = BasicShampoo.class, cascade = CascadeType.ALL)
    private BasicShampoo shampoo;

    public BasicLabel() {
    }

    public BasicLabel(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getSubtitle() {
        return this.subTitle;
    }

    @Override
    public void setSubtitle(String subtitle) {
        this.subTitle = subtitle;
    }
}
