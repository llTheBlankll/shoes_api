package com.asharia.dss.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO for {@link com.asharia.dss.models.entities.Feature}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FeatureTransactionDTO implements Serializable {
	private Integer productId;
	private String name;
	private String value;
}