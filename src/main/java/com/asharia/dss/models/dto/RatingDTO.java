package com.***REMOVED***.dss.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO for {@link com.***REMOVED***.dss.models.entities.Rating}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RatingDTO implements Serializable {
	private Integer id;
	private Integer rating;
}