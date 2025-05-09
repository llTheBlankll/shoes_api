package com.asharia.dss.repositories;

import com.asharia.dss.models.entities.Product;
import com.asharia.dss.models.enums.Fit;
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
			"(COALESCE(:name, '') = '' OR p.name ILIKE %:name%) AND " +
			"(COALESCE(:fit, '') = '' OR p.fit = :fit) AND " +
			"(COALESCE(:price, 0) = 0 OR p.price <= :price) AND " +
			"(COALESCE(:size, 0) = 0 OR p.size <= :size) AND " +
			"(COALESCE(:description, '') = '' OR p.description ILIKE %:description%) AND " +
			"(COALESCE(:releaseDate, '') = '' OR p.releaseDate = :releaseDate)"
	)
	Page<Product> searchProducts(
		@Param("brands") List<String> brand,
		@Param("name") String name,
		@Param("fit") Fit fit,
		@Param("price") BigDecimal price,
		@Param("size") Integer size,
		@Param("description") String description,
		@Param("releaseDate") LocalDate releaseDate,
		Pageable page
	);
}