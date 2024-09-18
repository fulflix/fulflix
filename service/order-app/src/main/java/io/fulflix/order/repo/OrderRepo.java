package io.fulflix.order.repo;

import io.fulflix.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {

}
