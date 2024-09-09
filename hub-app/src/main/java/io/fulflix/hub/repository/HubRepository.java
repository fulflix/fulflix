package io.fulflix.hub.repository;

import io.fulflix.hub.domain.Hub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRepository extends JpaRepository<Hub, Long> {
}
