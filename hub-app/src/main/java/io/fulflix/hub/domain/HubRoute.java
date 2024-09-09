package io.fulflix.hub.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_hub_routes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HubRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "departure_hub_id", nullable = false)
    private Hub departureHub;

    @ManyToOne
    @JoinColumn(name = "arrival_hub_id", nullable = false)
    private Hub arrivalHub;

    @Column(nullable = false)
    private Integer duration;

    @Column
    private String route;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;
}