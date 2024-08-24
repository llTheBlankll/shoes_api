package com.***REMOVED***.dss.controllers;

import com.***REMOVED***.dss.models.dto.MessageDTO;
import com.***REMOVED***.dss.models.dto.ProductDTO;
import com.***REMOVED***.dss.models.dto.ProductSearchDTO;
import com.***REMOVED***.dss.models.entities.Product;
import com.***REMOVED***.dss.models.enums.CodeStatus;
import com.***REMOVED***.dss.services.ProductService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Product", description = "Product API")
public class ProductController {

	private final ProductService productService;
	private final ModelMapper mapper = new ModelMapper();
	private final Logger logger = LoggerFactory.getLogger(ProductController.class);

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/all")
	@Operation(
		summary = "List All Products",
		description = "List all products available in the system. The products are paginated and sorted by default. You can provide the page number and size to get the desired results.<br><br>Example: <a href=\"http://localhost:8080/api/v1/products/all?page=0&size=10\">http://localhost:8080/api/v1/products/all?page=0&size=10</a>",
		responses = {
			@ApiResponse(
				description = "List of products",
				content = {
					@io.swagger.v3.oas.annotations.media.Content(
						mediaType = "application/json",
						schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class)
					)
				},
				responseCode = "200"
			)
		}
	)
	public ResponseEntity<PagedModel<EntityModel<ProductDTO>>> listAllProducts(@PageableDefault(sort = "id") Pageable page, @Parameter(hidden = true) PagedResourcesAssembler<ProductDTO> assembler) {
		// ! This will return all products available in the system.
		return ResponseEntity.ok(
			assembler.toModel(
				this.convertToProductDTO(
					productService.listAllProducts(
						page
					)
				)
			)
		);
	}

	@PutMapping("/create")
	@Operation(
		summary = "Create Product",
		description = "Create a new product. Please provide the product details in the request body. The product details are mapped to the Product entity. Please refer to the ProductDTO for more details.",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			required = true,
			content = @io.swagger.v3.oas.annotations.media.Content(
				mediaType = "application/json",
				schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ProductDTO.class)
			)
		),
		responses = {
			@ApiResponse(
				description = "Product created successfully",
				content = {
					@io.swagger.v3.oas.annotations.media.Content(
						mediaType = "application/json",
						schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ProductDTO.class)
					)
				},
				responseCode = "201"
			),
			@ApiResponse(
				description = "Product cannot be empty, this occurs when no request body was provided leading to null value",
				content = {
					@io.swagger.v3.oas.annotations.media.Content(
						mediaType = "application/json",
						schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MessageDTO.class)
					)
				},
				responseCode = "400"
			)
		}
	)
	public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
		if (productDTO == null) {
			return ResponseEntity.badRequest().body(new MessageDTO(
				"Product cannot be empty",
				CodeStatus.INVALID
			));
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(
			this.mapper.map(productDTO, Product.class)
		));
	}

	@PostMapping("/update")
	public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO) {
		// ! Validate the product
		if (productDTO == null) {
			return ResponseEntity.badRequest().body(new MessageDTO(
				"Product cannot be empty",
				CodeStatus.INVALID
			));
		}

		// ! Update the product
		return ResponseEntity.ok(productService.updateProduct(
			this.mapper.map(productDTO, Product.class)
		));
	}

	@PostMapping("/search")
	@Operation(
		summary = "Search Product",
		description = "Search products by providing the search criteria. Please refer to the ProductSearchDTO for more details. The values can be null and will be replaced by default values or empty strings.",
		tags = "Product",
		method = "POST",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Product search criteria. This will help you filter the products. Please refer to the ProductSearchDTO for more details.",
			content = @io.swagger.v3.oas.annotations.media.Content(
				schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ProductSearchDTO.class)
			),
			required = true
		)
	)
	public ResponseEntity<?> filterProducts(@RequestBody ProductSearchDTO productSearch,
	                                        @RequestParam(required = false, defaultValue = "0") int page,
																					@RequestParam(required = false, defaultValue = "10") int size,
	                                        PagedResourcesAssembler<ProductDTO> assembler) {
		// * Check if the product search is not null.
		if (productSearch == null) {
			return ResponseEntity.badRequest().body(new MessageDTO(
				"Product search cannot be empty",
				CodeStatus.INVALID
			));
		}

		logger.info("Searching products with criteria: {}", productSearch);
		return ResponseEntity.ok(
			assembler.toModel( // Assembler toModel converts Page<ProductDTO> to PagedModel<EntityModel<ProductDTO>>
				convertToProductDTO(
					productService.searchProducts(productSearch, PageRequest.of(page, size))
				)
			)
		);
	}

	@DeleteMapping("/{id}")
	@Operation(
		summary = "Delete Product By ID",
		description = "Delete product by providing the product id. Provide a valid product id.<br><br>Example: <a href=\"http://localhost:8080/api/v1/products/1\">http://localhost:8080/api/v1/products/1</a>",
		parameters = {
			@Parameter(
				name = "id",
				description = "Product ID",
				required = true
			)
		},
		responses = {
			@ApiResponse(
				description = "Product deleted successfully",
				content = {
					@io.swagger.v3.oas.annotations.media.Content(
						mediaType = "application/json",
						schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MessageDTO.class)
					)
				},
				responseCode = "200"
			),
			@ApiResponse(
				description = "Product not found",
				content = {
					@io.swagger.v3.oas.annotations.media.Content(
						mediaType = "application/json",
						schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MessageDTO.class)
					)
				},
				responseCode = "404"
			)
		}
	)
	public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body(new MessageDTO(
				"Product ID cannot be empty, /products/<PRODUCT ID> must be provided",
				CodeStatus.INVALID
			));
		}

		// Get the status of the product
		CodeStatus status = this.productService.deleteProduct(id);
		switch (status) {
			case NOT_FOUND -> { // ! If the product is not found, send a 404 response with a message
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new MessageDTO(
						"Product not found",
						CodeStatus.NOT_FOUND
					)
				);
			}
			case OK -> { // ! If the product is deleted, send a 200 response with a message
				return ResponseEntity.ok(
					new MessageDTO(
						"Product deleted successfully",
						CodeStatus.OK
					)
				);
			}
			default -> { // ! If the product could not be deleted, send a 400 response with a message (This will never happen)
				return ResponseEntity.badRequest().body(
					new MessageDTO(
						"Product could not be deleted",
						CodeStatus.FAILED
					)
				);
			}
		}
	}

	@GetMapping("/{id}")
	@Operation(
		summary = "Delete Product",
		description = "Delete product by providing the product id. Provide a valid product id.<br><br>Example: <a href=\"http://localhost:8080/api/v1/products/delete/1\">http://localhost:8080/api/v1/products/delete/1</a>",
		parameters = {
			@Parameter(
				name = "id",
				description = "Product ID",
				required = true
			)
		},
		responses = {
			@ApiResponse(
				description = "Product deleted successfully",
				content = {
					@io.swagger.v3.oas.annotations.media.Content(
						mediaType = "application/json",
						schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ProductDTO.class)
					)
				},
				responseCode = "200"
			),
			@ApiResponse(
				description = "Product not found",
				content = {
					@io.swagger.v3.oas.annotations.media.Content(
						mediaType = "application/json",
						schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MessageDTO.class)
					)
				},
				responseCode = "404"
			)
		}
	)
	public ResponseEntity<?> getProduct(@PathVariable Integer id) {
		Optional<Product> productOptional = productService.getProduct(id);

		// Check if the product exists
		if (productOptional.isPresent()) {
			return ResponseEntity.ok(productService.getProduct(id)); // ! If the product exists, send a 200 response with the product
		}

		// Product not found
		return ResponseEntity.badRequest().body( // ! If the product does not exist, send a 404 response with a message
			new MessageDTO(
				"Product not found",
				CodeStatus.NOT_FOUND
			)
		);
	}

	@GetMapping("/brands/all")
	@Operation(
		summary = "Get All Product Brands",
		description = "Get all product brands",
		responses = {
			@ApiResponse(
				description = "All product brands",
				content = {
					@io.swagger.v3.oas.annotations.media.Content(
						mediaType = "application/json",
						schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = List.class)
					)
				},
				responseCode = "200"
			)
		}
	)
	public ResponseEntity<?> getAllBrands() {
		// ! Get all brands
		return ResponseEntity.ok(productService.getAllBrands());
	}

	private Page<ProductDTO> convertToProductDTO(Page<Product> products) {
		return products.map(product -> mapper.map(product, ProductDTO.class)); // ! Convert Page<Product> to Page<ProductDTO>
	}
}