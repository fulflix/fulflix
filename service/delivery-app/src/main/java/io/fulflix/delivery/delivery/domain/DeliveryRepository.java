package io.fulflix.delivery.delivery.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Page<Delivery> findByDeliveryAddressContaining(String keyword, Pageable pageable);
}
