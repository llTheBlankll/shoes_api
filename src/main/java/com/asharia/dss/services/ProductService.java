package com.***REMOVED***.dss.services;

import com.***REMOVED***.dss.models.dto.ProductSearchDTO;
import com.***REMOVED***.dss.models.entities.Product;
import com.***REMOVED***.dss.models.enums.CodeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

	Page<Product> listAllProducts(Pageable page);

	/**
	 * Create product
	 *
	 * @param product The product that will be created
	 * @return {@link CodeStatus}
	 */
	CodeStatus createProduct(Product product);

	/**
	 * Delete product by their Product ID
	 *
	 * @param productId The product ID
	 * @return {@link CodeStatus}
	 */
	CodeStatus deleteProduct(int productId);

	/**
	 * Update product, the value of the `id` will be used which product will be updated
	 *
	 * @param product The product that will be updated
	 * @return {@link CodeStatus} {@link CodeStatus} {@link CodeStatus} {@link CodeStatus}
	 */
	CodeStatus updateProduct(Product product);

	/**
	 * Get the information of the product by their Product ID
	 *
	 * @param productId The product ID
	 * @return {@link Product}
	 */
	Optional<Product> getProduct(int productId);

	/**
	 * Search products
	 *
	 * @param productSearch The search criteria
	 * @return {@link Page<Product>}
	 */
	List<Product> searchProducts(ProductSearchDTO productSearch);
}