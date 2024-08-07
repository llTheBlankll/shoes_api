package com.***REMOVED***.dss.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO for {@link com.***REMOVED***.dss.models.entities.Image}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ImageDTO implements Serializable {
	private Integer id;
	private String url;
}