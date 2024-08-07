package com.***REMOVED***.dss.controllers;

import com.***REMOVED***.dss.models.dto.MessageDTO;
import com.***REMOVED***.dss.models.dto.ProductDTO;
import com.***REMOVED***.dss.models.dto.ProductSearchDTO;
import com.***REMOVED***.dss.models.entities.Product;
import com.***REMOVED***.dss.models.enums.CodeStatus;
import com.***REMOVED***.dss.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	private final ProductService productService;
	private final ModelMapper mapper = new ModelMapper();

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PutMapping("/create")
	public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
		if (productDTO == null) {
			return ResponseEntity.badRequest().body(new MessageDTO(
				"Product cannot be empty",
				CodeStatus.INVALID
			));
		}

		return ResponseEntity.ok(productService.createProduct(
			this.mapper.map(productDTO, Product.class)
		));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
		return ResponseEntity.ok(productService.deleteProduct(id));
	}

	@PostMapping("/update")
	public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO) {
		if (productDTO == null) {
			return ResponseEntity.badRequest().body(new MessageDTO(
				"Product cannot be empty",
				CodeStatus.INVALID
			));
		}

		return ResponseEntity.ok(productService.updateProduct(
			this.mapper.map(productDTO, Product.class)
		));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProduct(@PathVariable Integer id) {
		return ResponseEntity.ok(productService.getProduct(id));
	}

	@GetMapping("/search")
	public ResponseEntity<?> searchProducts(@RequestBody ProductSearchDTO productSearch) {
		if (productSearch == null) {
			return ResponseEntity.badRequest().body(new MessageDTO(
				"Product search cannot be empty",
				CodeStatus.INVALID
			));
		}

		return ResponseEntity.ok(productService.searchProducts(productSearch));
	}
}