package softuni.domain.entities;

import javax.persistence.*;

@MappedSuperclass
public class BaseEntity {
    private Long id;

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
