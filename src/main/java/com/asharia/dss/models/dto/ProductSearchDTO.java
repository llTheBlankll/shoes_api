package com.asharia.dss.models.dto;

import com.asharia.dss.models.enums.Fit;
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

	private List<String> brands;
	private String name;
	private Integer size;
	private Fit fit;
	private BigDecimal price;
	private String description;
	private LocalDate releaseDate;
	private List<FeatureFilterDTO> featureFilters;
}