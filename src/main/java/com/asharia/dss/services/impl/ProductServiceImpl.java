package com.***REMOVED***.dss.services.impl;

import com.***REMOVED***.dss.models.dto.FeatureDTO;
import com.***REMOVED***.dss.models.dto.ProductSearchDTO;
import com.***REMOVED***.dss.models.entities.Product;
import com.***REMOVED***.dss.models.enums.CodeStatus;
import com.***REMOVED***.dss.repositories.ProductRepository;
import com.***REMOVED***.dss.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

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
	public List<Product> searchProducts(ProductSearchDTO productSearch) {
		// Return an empty list if the search criteria is null
		if (productSearch == null) {
			return List.of();
		}

		List<Product> products = this.productRepository.searchProducts(
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
			productSearch.getReleaseDate()
		);

		// Iterate through the features and filter the products
		if (productSearch.getFeatures() != null) {
			// Filter the products by the features if the feature is not null.
			for (FeatureDTO feature : productSearch.getFeatures()) {
				products.removeIf(product -> product.getFeatures().stream().noneMatch(productFeature -> productFeature.getName().contains(feature.getName())));
			}
		}

		return products;
	}
}