package com.***REMOVED***.dss.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "images")
public class Image {
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "url")
	private String url;
}