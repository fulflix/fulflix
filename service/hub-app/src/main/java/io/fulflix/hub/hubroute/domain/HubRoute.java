package io.fulflix.hub.hubroute.domain;

import io.fulflix.common.app.jpa.audit.Auditable;
import io.fulflix.hub.hub.domain.Hub;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import static io.fulflix.common.app.jpa.audit.CommonAuditFields.DEFAULT_CONDITION;

@Entity
@Table(name = "p_hub_routes")
@Getter
@NoArgsConstructor
@SQLRestriction(DEFAULT_CONDITION)
public class HubRoute extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 출발 허브
    @ManyToOne
    @JoinColumn(name = "departure_hub_id", nullable = false)
    private Hub departureHub;

    // 도착 허브
    @ManyToOne
    @JoinColumn(name = "arrival_hub_id", nullable = false)
    private Hub arrivalHub;

    @Column
    private Long duration;

    @Column
    private Double distance;

    @Column
    private String route;

    @PrePersist
    @PreUpdate
    private void updateRoute() {
        if (this.departureHub != null && this.arrivalHub != null) {
            this.route = this.departureHub.getName() + " → " + this.arrivalHub.getName();
        }
    }

    public void setDepartureHub(Hub departureHub) {
        this.departureHub = departureHub;
    }

    public void setArrivalHub(Hub arrivalHub) {
        this.arrivalHub = arrivalHub;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    // 생성자
    @Builder
    public HubRoute(Hub departureHub, Hub arrivalHub, Long duration, Double distance, String route) {
        this.departureHub = departureHub;
        this.arrivalHub = arrivalHub;
        this.duration = duration;
        this.distance = distance;
        this.route = route;
    }
}