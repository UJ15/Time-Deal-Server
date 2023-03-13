package com.uj15.timedeal.order.repository;

import com.uj15.timedeal.order.entity.Order;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("select o from Order o where o.user.id = :userId and o.product.id = :productId")
    Optional<Order> findByProductIdAndUserId(UUID productId, UUID userId);

    @Query("select o from Order o where o.user.id = :userId")
    List<Order> findByUserId(UUID userId);

    @Query("select o from Order o where o.product.id = :productId")
    List<Order> findByProductId(UUID productId);
}
