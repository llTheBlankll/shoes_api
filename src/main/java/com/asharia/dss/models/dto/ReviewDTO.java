package com.asharia.dss.models.dto;

import com.asharia.dss.models.entities.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO for {@link Review}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ReviewDTO implements Serializable {
	private Integer id;
	private Integer rating;
}