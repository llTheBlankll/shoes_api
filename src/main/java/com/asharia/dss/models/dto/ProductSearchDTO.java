package com.***REMOVED***.dss.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ProductSearchDTO {

	private List<String> brand;
	private String model;
	private String category;
	private String color;
	private Integer size;
	private String gender;
	private String availability;
	private BigDecimal price;
	private String description;
	private Integer stock;
	private LocalDate releaseDate;
	private List<FeatureFilterDTO> featureFilters;
}