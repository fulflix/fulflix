package io.fulflix.order.repo;

import io.fulflix.order.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
    Page<Order> findByOrderQuantityGreaterThanEqual(Integer orderQuantity, Pageable pageable);
}
