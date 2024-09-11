package io.fulflix.hub.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import static io.fulflix.common.app.jpa.audit.CommonAuditFields.DEFAULT_CONDITION;

@Entity
@Table(name = "p_hub_routes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE p_hub_routes SET is_deleted = true WHERE id = ?")
@SQLRestriction(DEFAULT_CONDITION)
public class HubRoute {

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

    @Column(nullable = false)
    private Integer duration;

    @Column
    private String route;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @PrePersist
    @PreUpdate
    private void updateRoute() {
        if (this.departureHub != null && this.arrivalHub != null) {
            this.route = this.departureHub.getName() + " → " + this.arrivalHub.getName();
        }
    }
}