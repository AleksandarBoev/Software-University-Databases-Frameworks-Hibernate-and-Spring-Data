package mostwanted.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "races")
public class Race extends BaseEntity {
    private Integer laps;
    private District district;

    @Column(nullable = false, columnDefinition = "INT(11) DEFAULT 0")
    public Integer getLaps() {
        return this.laps;
    }

    public void setLaps(Integer laps) {
        this.laps = laps;
    }

    @ManyToOne
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    public District getDistrict() {
        return this.district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
}
