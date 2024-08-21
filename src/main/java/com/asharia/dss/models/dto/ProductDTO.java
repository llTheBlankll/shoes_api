package com.***REMOVED***.dss.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link com.***REMOVED***.dss.models.entities.Product}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ProductDTO implements Serializable {
	private Integer id;
	private String brand;
	private String model;
	private String category;
	private String color;
	private Integer size;
	private String gender;
	private BigDecimal price;
	private String description;
	private Integer stock;
	private LocalDate releaseDate;
	private List<FeatureDTO> features;
	private String availability;
	private Set<ReviewDTO> ratings = new LinkedHashSet<>();
}