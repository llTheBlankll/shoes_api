package com.***REMOVED***.dss.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "brand")
	private String brand;

	@Column(name = "model")
	private String model;

	@Column(name = "category")
	private String category;

	@Column(name = "color")
	private String color;

	@Column(name = "size")
	private Integer size;

	@Column(name = "gender")
	private String gender;

	@Column(name = "price", precision = 10, scale = 2)
	private BigDecimal price;

	@Lob
	@Column(name = "description")
	private String description;

	@Column(name = "stock")
	private Integer stock;

	@Column(name = "release_date")
	private LocalDate releaseDate;

	@Column(name = "availability")
	private String availability;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private List<Feature> features;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private List<Image> images;

	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
	private Set<Rating> ratings = new LinkedHashSet<>();

}