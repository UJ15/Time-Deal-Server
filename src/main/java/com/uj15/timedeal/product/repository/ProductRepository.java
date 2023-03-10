package com.uj15.timedeal.product.repository;

import com.uj15.timedeal.product.entity.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID> {

}
