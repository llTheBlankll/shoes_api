package com.***REMOVED***.dss.repositories;

import com.***REMOVED***.dss.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}