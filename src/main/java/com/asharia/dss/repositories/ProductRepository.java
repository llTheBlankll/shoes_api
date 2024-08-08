package com.***REMOVED***.dss.repositories;

import com.***REMOVED***.dss.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	// Used for search, using Contains or ILIKE % for searching and case-insensitive
	@Query(
		"SELECT p FROM Product as p WHERE " +
			"(COALESCE(?1, '') = '' OR p.brand ILIKE %?1%) AND " +
			"(COALESCE(?2, '') = '' OR p.model ILIKE %?2%) AND " +
			"(COALESCE(?3, '') = '' OR p.category ILIKE %?3%) AND " +
			"(COALESCE(?4, '') = '' OR p.color ILIKE %?4%) AND " +
			"(COALESCE(?5, 0) = 0 OR p.size <=?5) AND " +
			"(COALESCE(?6, '') = '' OR p.gender ILIKE %?6%) AND " +
			"(COALESCE(?7, '') = '' OR p.availability ILIKE %?7%) AND " +
			"(COALESCE(?8, 0) = 0 OR p.price <=?8) AND " +
			"(COALESCE(?9, '') = '' OR p.description ILIKE %?9%) AND " +
			"(COALESCE(?10, 0) = 0 OR p.stock >= ?10) AND " +
			"(COALESCE(?11, '') = '' OR p.releaseDate =?11)"
	)
	List<Product> searchProducts(
		String brand,
		String model,
		String category,
		String color,
		Integer size,
		String gender,
		String availability,
		BigDecimal price,
		String description,
		Integer stock,
		LocalDate releaseDate
	);
}