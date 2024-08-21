package com.***REMOVED***.dss.repositories;

import com.***REMOVED***.dss.models.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	// Used for search, using Contains or ILIKE % for searching and case-insensitive
	@Query(
		"SELECT p FROM Product as p WHERE " +
			"(:brands IS NULL OR p.brand IN :brands) AND " +
			"(COALESCE(:model, '') = '' OR p.model ILIKE %:model%) AND " +
			"(COALESCE(:category, '') = '' OR p.category ILIKE %:category%) AND " +
			"(COALESCE(:color, '') = '' OR p.color ILIKE %:color%) AND " +
			"(COALESCE(:size, 0) = 0 OR p.size <= :size) AND " +
			"(COALESCE(:gender, '') = '' OR p.gender ILIKE %:gender%) AND " +
			"(COALESCE(:availability, '') = '' OR p.availability ILIKE %:availability%) AND " +
			"(COALESCE(:price, 0) = 0 OR p.price <= :price) AND " +
			"(COALESCE(:description, '') = '' OR p.description ILIKE %:description%) AND " +
			"(COALESCE(:stock, 0) = 0 OR p.stock >= :stock) AND " +
			"(COALESCE(:releaseDate, '') = '' OR p.releaseDate = :releaseDate)"
	)
	Page<Product> searchProducts(
		@Param("brands") List<String> brand,
		@Param("model") String model,
		@Param("category") String category,
		@Param("color") String color,
		@Param("size") Integer size,
		@Param("gender") String gender,
		@Param("availability") String availability,
		@Param("price") BigDecimal price,
		@Param("description") String description,
		@Param("stock") Integer stock,
		@Param("releaseDate") LocalDate releaseDate,
		Pageable page
	);
}