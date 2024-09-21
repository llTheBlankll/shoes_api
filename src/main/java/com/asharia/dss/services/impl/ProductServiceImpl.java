package com.asharia.dss.services.impl;

import com.asharia.dss.models.dto.FeatureFilterDTO;
import com.asharia.dss.models.dto.ProductSearchDTO;
import com.asharia.dss.models.entities.Feature;
import com.asharia.dss.models.entities.Product;
import com.asharia.dss.models.enums.CodeStatus;
import com.asharia.dss.repositories.ProductRepository;
import com.asharia.dss.services.ProductService;
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

		logger.debug("Searching products with criteria: {}", productSearch);
		Page<Product> products = this.productRepository.searchProducts(productSearch.getBrands(), productSearch.getName(), productSearch.getFit(), productSearch.getPrice(), productSearch.getSize(), productSearch.getDescription(), productSearch.getReleaseDate(), page);
		for (Product product : products) {
			logger.debug("Found product: {}", product.toString());
		}
		List<Product> modifiableProduct = products.getContent();

		// Filter the products
		List<Product> filteredProducts = filterProductsByFeatures(modifiableProduct, productSearch.getFeatureFilters());
		logger.debug("Found {} products", filteredProducts.size());
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

		return products.stream().map(Product::getBrand).distinct().collect(Collectors.toList());
	}

	private List<Product> filterProductsByFeatures(List<Product> products, List<FeatureFilterDTO> filters) {
		// If there are no filters, return the products since no filtering is needed
		if (filters == null || filters.isEmpty()) {
			logger.warn("No filters provided, returning all products");
			return products;
		}

		// Filter the products
		logger.debug("Filtering products by features: {}", filters);
		return products.stream().filter(product -> filters.stream().allMatch(filter -> filterData(filter, product))).collect(Collectors.toList());
	}


	/**
	 * Filter data based on feature filters and product features
	 *
	 * @param filter  The {@link FeatureFilterDTO} object containing the filter name, operator, and value
	 * @param product The {@link Product} object containing the features to be filtered
	 * @return {@link boolean} true if the product passes the filter, false otherwise
	 */
	private boolean filterData(FeatureFilterDTO filter, Product product) {
		for (Feature feature : product.getFeatures()) {
			/*
			 If the feature name matches the filter name, compare the feature value with the filter value using the operator.
			 If the comparison is true, return true.
			 If the comparison is false, continue to the next feature.
			 If there are no more features, return false.
			 */
			if (feature.getName().equals(filter.getName())) {
				logger.debug("Matching feature: {} with filter: {}", feature, filter);
				return switch (filter.getOperator()) {
					case "==" -> feature.getValue().equals(filter.getValue());
					case "<" -> {
						try {
							logger.debug("{} < {}", Double.parseDouble(feature.getValue()), Double.parseDouble(filter.getValue().toString()));
							boolean result = Double.parseDouble(feature.getValue()) < Double.parseDouble(filter.getValue().toString());
							logger.debug("Result: {}", result);
							yield result;
						} catch (NumberFormatException | ClassCastException e) {
							logger.debug("Is not a number", e);
							yield false;
						}
					}
					case ">" -> {
						try {
							logger.debug("{} > {}", Double.parseDouble(feature.getValue()), Double.parseDouble(filter.getValue().toString()));
							boolean result = Double.parseDouble(feature.getValue()) > Double.parseDouble(filter.getValue().toString());
							logger.debug("Result: {}", result);
							yield result;
						} catch (NumberFormatException | ClassCastException e) {
							logger.debug("Is not a number", e);
							yield false;
						}
					}
					case "<=" -> {
						try {
							logger.debug("{} <= {}", Double.parseDouble(feature.getValue()), Double.parseDouble(filter.getValue().toString()));
							boolean result = Double.parseDouble(feature.getValue()) <= Double.parseDouble(filter.getValue().toString());
							logger.debug("Result: {}", result);
							yield result;
						} catch (NumberFormatException | ClassCastException e) {
							logger.debug("Is not a number", e);
							yield false;
						}
					}
					case ">=" -> {
						try {
							logger.debug("{} >= {}", Double.parseDouble(feature.getValue()), Double.parseDouble(filter.getValue().toString()));
							boolean result = Double.parseDouble(feature.getValue()) >= Double.parseDouble(filter.getValue().toString());
							logger.debug("Result: {}", result);
							yield result;
						} catch (NumberFormatException | ClassCastException e) {
							logger.debug("Is not a number", e);
							yield false;
						}
					}
					default -> {
						logger.debug("Unsupported operator: {}", filter.getOperator());
						yield false;
					}
				};
			} else {
				logger.debug("Feature name {} does not match filter name {}", feature.getName(), filter.getName());
			}
		}
		return false;
	}
}