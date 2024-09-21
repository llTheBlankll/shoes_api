package com.asharia.dss.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "features")
@AllArgsConstructor
@NoArgsConstructor
public class Feature {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@Size(max = 255)
	@Column(name = "name")
	private String name;

	@Size(max = 255)
	@Column(name = "value")
	private String value;

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
			"id = " + id + ", " +
			"product = " + product.toString() + ", " +
			"name = " + name + ", " +
			"value = " + value + ")";
	}
}