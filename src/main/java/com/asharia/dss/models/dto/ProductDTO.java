package com.***REMOVED***.dss.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
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
	private FeatureDTO features;
	private ImageDTO images;
	private String availability;
	private Set<RatingDTO> ratings = new LinkedHashSet<>();
}