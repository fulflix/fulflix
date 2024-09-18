package io.fulflix.hub.hub.domain;


import io.fulflix.core.app.jpa.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;


import java.util.Objects;

import static io.fulflix.core.app.jpa.audit.CommonAuditFields.DEFAULT_CONDITION;

@Entity
@Table(name = "p_hubs")
@NoArgsConstructor
@Getter
@SQLRestriction(DEFAULT_CONDITION)
public class Hub extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    // 생성자 (필수 필드만 설정)
    public Hub(String name, String address, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hub hub = (Hub) o;
        return Objects.equals(id, hub.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
