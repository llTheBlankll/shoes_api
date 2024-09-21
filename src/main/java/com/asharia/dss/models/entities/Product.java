package com.asharia.dss.models.entities;

import com.asharia.dss.models.enums.Fit;
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

	@Column(name = "size")
	private Integer size;

	@Column(name = "name")
	private String name;

	@Column(name = "fit")
	@Enumerated(EnumType.STRING)
	private Fit fit;

	@Column(name = "price", precision = 10, scale = 2)
	private BigDecimal price;

	@Column(name = "description")
	private String description;

	@Column(name = "release_date")
	private LocalDate releaseDate;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private List<Feature> features;

	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
	private Set<Review> reviews = new LinkedHashSet<>();

	@Override
	public String toString() {
		return "Product{" +
			"id=" + id +
			", brand='" + brand + '\'' +
			", size=" + size +
			", name='" + name + '\'' +
			", fit=" + fit +
			", price=" + price +
			", description='" + description + '\'' +
			", releaseDate=" + releaseDate +
			'}';
	}
}