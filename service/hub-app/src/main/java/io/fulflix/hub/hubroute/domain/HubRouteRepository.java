package io.fulflix.hub.hubroute.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRouteRepository extends JpaRepository<HubRoute, Long> {
    Page<HubRoute> findByRouteContaining(String keyword, Pageable pageable);
}
