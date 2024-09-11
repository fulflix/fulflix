package io.fulflix.hub.hub.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRepository extends JpaRepository<Hub, Long> {
    Page<Hub> findByNameContaining(String keyword, Pageable pageable);
}
