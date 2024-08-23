package com.***REMOVED***.dss.services.impl;

import com.***REMOVED***.dss.models.dto.FeatureDTO;
import com.***REMOVED***.dss.models.dto.FeatureFilterDTO;
import com.***REMOVED***.dss.models.dto.ProductSearchDTO;
import com.***REMOVED***.dss.models.entities.Product;
import com.***REMOVED***.dss.models.enums.CodeStatus;
import com.***REMOVED***.dss.repositories.ProductRepository;
import com.***REMOVED***.dss.services.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final Logger logger = LogManager.getLogger(this.getClass());

	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public Page<Product> listAllProducts(Pageable page) {
		return this.productRepository.findAll(page);
	}

	/**
	 * Create product
	 *
	 * @param product The product that will be created
	 * @return {@link CodeStatus}
	 */
	@Override
	public CodeStatus createProduct(Product product) {
		this.productRepository.save(product);
		return CodeStatus.OK;
	}

	/**
	 * Delete product by their Product ID
	 *
	 * @param productId The product ID
	 * @return {@link CodeStatus}
	 */
	@Override
	public CodeStatus deleteProduct(int productId) {
		// Checks if the product exists.
		if (this.productRepository.existsById(productId)) {
			this.productRepository.deleteById(productId);
			return CodeStatus.OK;
		}

		return CodeStatus.NOT_FOUND;
	}

	/**
	 * Update product, the value of the `id` will be used which product will be updated
	 *
	 * @param product The product that will be updated
	 * @return {@link CodeStatus}
	 */
	@Override
	public CodeStatus updateProduct(Product product) {
		// Checks if the product exists.
		if (this.productRepository.existsById(product.getId())) {
			this.productRepository.save(product);
			return CodeStatus.OK;
		}

		return CodeStatus.NOT_FOUND;
	}

	/**
	 * Get the information of the product by their Product ID
	 *
	 * @param productId The product ID
	 * @return {@link Product}
	 */
	@Override
	public Optional<Product> getProduct(int productId) {
		return this.productRepository.findById(productId);
	}

	/**
	 * Search products
	 *
	 * @param productSearch The search criteria
	 * @return {@link List<Product>}
	 */
	@Override
	public Page<Product> searchProducts(ProductSearchDTO productSearch, Pageable page) {
		// Return an empty list if the search criteria is null
		if (productSearch == null) {
			return Page.empty();
		}
		// Check if the brand is empty and set it to null
		if (productSearch.getBrand() == null || productSearch.getBrand().isEmpty()) {
			productSearch.setBrand(null);
		}

		Page<Product> products = this.productRepository.searchProducts(
			productSearch.getBrand(),
			productSearch.getModel(),
			productSearch.getCategory(),
			productSearch.getColor(),
			productSearch.getSize(),
			productSearch.getGender(),
			productSearch.getAvailability(),
			productSearch.getPrice(),
			productSearch.getDescription(),
			productSearch.getStock(),
			productSearch.getReleaseDate(),
			page
		);

		// Filter the products
		List<Product> productList = filterProductsByFeatures(products.getContent(), productSearch.getFeatureFilters());
		return new PageImpl<>(productList, page, products.getTotalElements());
	}

	/**
	 * Get all brands
	 *
	 * @return {@link List<String>}
	 */
	@Override
	public List<String> getAllBrands() {
		List<Product> products = this.productRepository.findAll();

		return products.stream()
			.map(Product::getBrand)
			.distinct()
			.collect(Collectors.toList());
	}

	private List<Product> filterProductsByFeatures(List<Product> products, List<FeatureFilterDTO> filters) {
		Iterator<Product> iterator = products.iterator();
		while (iterator.hasNext()) {
			Product iteratedProduct = iterator.next();
			for (FeatureFilterDTO filter : filters) {
				// Field that will be filtered
				Object fieldValue = getFieldValue(filter.getName(), iteratedProduct);
				if (fieldValue == null) {
					throw new IllegalArgumentException("Field not found: " + filter.getName());
				}

				boolean filtered = filterData(filter.getOperator(), fieldValue, filter.getValue());
				if (!filtered) {
					iterator.remove();
				}
			}
		}
		return products;
	}

	private boolean filterData(String operator, Object fieldValue, Object filterValue) {
		// Check if the field value is an integer or a string
		switch (operator) {
			case "==" -> {
				return fieldValue.equals(filterValue);
			}
			case "<" -> {
				if (fieldValue instanceof Integer) {
					return ((Integer) fieldValue).compareTo((Integer) filterValue) < 0;
				} else if (fieldValue instanceof Long) {
					return ((Long) fieldValue).compareTo((Long) filterValue) < 0;
				}

				return false;
			}
			case ">" -> {
				if (fieldValue instanceof Integer) {
					return ((Integer) fieldValue).compareTo((Integer) filterValue) > 0;
				} else if (fieldValue instanceof Long) {
					return ((Long) fieldValue).compareTo((Long) filterValue) > 0;
				}

				return false;
			}
			default -> {
				return false;
			}
		}
	}

	private Field getFieldValue(String fieldName, Product product) {
		try {
			return product.getClass().getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			return null;
		}
	}
}