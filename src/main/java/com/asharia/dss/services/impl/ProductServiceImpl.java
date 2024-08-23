package com.***REMOVED***.dss.services.impl;

import com.***REMOVED***.dss.models.dto.FeatureFilterDTO;
import com.***REMOVED***.dss.models.dto.ProductSearchDTO;
import com.***REMOVED***.dss.models.entities.Feature;
import com.***REMOVED***.dss.models.entities.Product;
import com.***REMOVED***.dss.models.enums.CodeStatus;
import com.***REMOVED***.dss.repositories.ProductRepository;
import com.***REMOVED***.dss.services.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
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
		if (productSearch.getBrands() == null || productSearch.getBrands().isEmpty()) {
			productSearch.setBrands(null);
		}

		logger.info("Searching products with criteria: {}", productSearch);
		Page<Product> products = this.productRepository.searchProducts(
			productSearch.getBrands(),
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
		List<Product> modifiableProduct = products.getContent();

		// Filter the products
		List<Product> filteredProducts = filterProductsByFeatures(modifiableProduct, productSearch.getFeatureFilters());
		logger.info("Found {} products", filteredProducts.size());
		return new PageImpl<>(filteredProducts, page, products.getTotalElements());
//		return products;
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
		// If there are no filters, return the products since no filtering is needed
		if (filters.isEmpty()) {
			return products;
		}

		// Filter the products
		return products.stream()
			.filter(product -> filters.stream()
				.allMatch(filter -> filterData(filter, product)))
			.collect(Collectors.toList());
	}

	private boolean filterData(FeatureFilterDTO filter, Product product) {
		for (Feature feature : product.getFeatures())

			if (feature.getName().equals(filter.getName())) {
				return switch (filter.getOperator()) {
					case "==" -> feature.getValue().equals(filter.getValue());
					case "<" -> {
						// Check if the feature.getValue() is a number
						logger.info("Checking if {} is less than {}", feature.getValue(), filter.getValue());
						try {
							yield Double.parseDouble(feature.getValue()) < Double.parseDouble(filter.getValue().toString());
						} catch (NumberFormatException | ClassCastException e) {
							logger.error("Error while checking if {} is less than {}", feature.getValue(), filter.getValue());
							yield false;
						}
					}
					case ">" -> {
						// Check if the feature.getValue() is a number
						logger.info("Checking if {} is greater than {}", feature.getValue(), filter.getValue());
						try {
							yield Double.parseDouble(feature.getValue()) > Double.parseDouble(filter.getValue().toString());
						} catch (NumberFormatException | ClassCastException e) {
							logger.error("Error while checking if {} is greater than {}", feature.getValue(), filter.getValue());
							yield false;
						}
					}
					default -> false;
				};
			}
		return false;
	}
}