package com.asharia.dss.controllers;

import com.asharia.dss.models.dto.FeatureDTO;
import com.asharia.dss.models.dto.FeatureTransactionDTO;
import com.asharia.dss.models.dto.MessageDTO;
import com.asharia.dss.models.entities.Feature;
import com.asharia.dss.models.entities.Product;
import com.asharia.dss.models.enums.CodeStatus;
import com.asharia.dss.services.FeatureService;
import com.asharia.dss.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/features")
@Tag(name = "Feature", description = "Feature API")
public class FeatureController {

	private final FeatureService featureService;
	private final ModelMapper modelMapper = new ModelMapper();
	private final ProductService productService;
	private final Logger logger = LogManager.getLogger(this.getClass());

	public FeatureController(FeatureService featureService, ProductService productService) {
		this.featureService = featureService;
		this.productService = productService;
	}

	@GetMapping("/all")
	@Operation(
		summary = "List All Features",
		description = "This will list all distinct features for every product. Listing features is not recommended as they came from all products.",
		responses = {
			@ApiResponse(
				description = "List of features",
				content = {
					@io.swagger.v3.oas.annotations.media.Content(
						mediaType = "application/json",
						schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class)
					)
				}
			)
		},
		parameters = {
			@Parameter(
				name = "page",
				description = "Page number"
			),
			@Parameter(
				name = "size",
				description = "Page size"
			),
			@Parameter(
				name = "sort",
				description = "Sort by field"
			),
			@Parameter(
				name = "direction",
				description = "Sort direction"
			)
		}
	)
	public ResponseEntity<?> listAllFeatures(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable page) {
		return ResponseEntity.ok(
			featureService.listAllFeatures(page).map((element) -> modelMapper.map(element, FeatureDTO.class))
		);
	}

	@PutMapping
	@Operation(
		summary = "Update Feature",
		description = "Update feature by providing the feature id. Provide a valid feature id.<br><br>Example: <a href=\"http://localhost:8080/api/v1/features\">http://localhost:8080/api/v1/features</a>",
		responses = {
			@ApiResponse(
				description = "Feature updated successfully",
				content = {
					@io.swagger.v3.oas.annotations.media.Content(
						mediaType = "application/json",
						schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MessageDTO.class)
					)
				},
				responseCode = "200"
			),
			@ApiResponse(
				description = "Feature not found",
				content = {
					@io.swagger.v3.oas.annotations.media.Content(
						mediaType = "application/json",
						schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MessageDTO.class)
					)
				},
				responseCode = "404"
			),
			@ApiResponse(
				description = "Feature cannot be empty, this occurs when no request body was provided leading to null value",
				content = {
					@io.swagger.v3.oas.annotations.media.Content(
						mediaType = "application/json",
						schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MessageDTO.class)
					)
				},
				responseCode = "400"
			), @ApiResponse(
			description = "Feature cannot be empty, this occurs when no request body was provided leading to null value",
			content = {
				@io.swagger.v3.oas.annotations.media.Content(
					mediaType = "application/json",
					schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MessageDTO.class)
				)
			},
			responseCode = "400"
		), @ApiResponse(
			description = "Feature cannot be empty, this occurs when no request body was provided leading to null value",
			content = {
				@io.swagger.v3.oas.annotations.media.Content(
					mediaType = "application/json",
					schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MessageDTO.class)
				)
			},
			responseCode = "400"
		), @ApiResponse(
			description = "Feature cannot be empty, this occurs when no request body was provided leading to null value",
			content = {
				@io.swagger.v3.oas.annotations.media.Content(
					mediaType = "application/json",
					schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MessageDTO.class)
				)
			},
			responseCode = "400"
		)
		},
		parameters = {
			@Parameter(
				name = "id",
				description = "Feature ID",
				required = true
			)
		}
	)
	public ResponseEntity<?> updateFeature(@RequestBody FeatureTransactionDTO featureTransactionDTO) {
		// Input validation, make sure that there is a valid request body
		if (featureTransactionDTO == null) {
			return ResponseEntity.badRequest().body(new MessageDTO(
				"Feature cannot be empty, this occurs when no request body was provided leading to null value",
				CodeStatus.INVALID
			));
		}

		CodeStatus status = this.featureService.updateFeature(
			modelMapper.map(featureTransactionDTO, Feature.class)
		);

		switch (status) {
			case OK -> {
				return ResponseEntity.ok(new MessageDTO(
					"Feature updated successfully",
					CodeStatus.OK
				));
			}
			case INVALID -> {
				return ResponseEntity.badRequest().body(new MessageDTO(
					"Feature cannot be empty, this occurs when no request body was provided leading to null value. Please provide the feature details in the request body.",
					CodeStatus.INVALID
				));
			}
			default -> {
				return ResponseEntity.status(404).body(new MessageDTO(
					"Feature not found",
					CodeStatus.NOT_FOUND
				));
			}
		}
	}

	@DeleteMapping("/{id}")
	@Operation(
		summary = "Delete Feature",
		description = "Delete feature by providing the feature id. Provide a valid feature id.<br><br>Example: <a href=\"http://localhost:8080/api/v1/features/1\">http://localhost:8080/api/v1/features/1</a>",
		responses = {
			@ApiResponse(
				description = "Feature deleted successfully",
				content = {
					@io.swagger.v3.oas.annotations.media.Content(
						mediaType = "application/json",
						schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MessageDTO.class)
					)
				},
				responseCode = "200"
			),
			@ApiResponse(
				description = "Feature not found",
				content = {
					@io.swagger.v3.oas.annotations.media.Content(
						mediaType = "application/json",
						schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MessageDTO.class)
					)
				},
				responseCode = "404"
			),
			@ApiResponse(
				description = "Feature cannot be empty, this occurs when no request body was provided leading to null value",
				content = {
					@io.swagger.v3.oas.annotations.media.Content(
						mediaType = "application/json",
						schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MessageDTO.class)
					)
				},
				responseCode = "400"
			)
		},
		parameters = {
			@Parameter(
				name = "id",
				description = "Feature ID",
				required = true
			)
		}
	)
	public ResponseEntity<?> deleteFeature(@PathVariable Integer id) {
		// Check if the feature id is valid
		if (id <= 0) {
			return ResponseEntity.badRequest().body(new MessageDTO(
				"Feature id is null or invalid, cannot delete the feature",
				CodeStatus.INVALID
			));
		}

		// Get the result of the delete operation.
		CodeStatus status = this.featureService.deleteFeature(id);
		switch (status) {
			case OK -> {
				return ResponseEntity.ok(new MessageDTO(
					"Feature deleted successfully",
					CodeStatus.OK
				));
			}
			case INVALID -> {
				return ResponseEntity.badRequest().body(new MessageDTO(
					"Feature cannot be empty, this occurs when no request body was provided leading to null value. Please provide the feature details in the request body.",
					CodeStatus.INVALID
				));
			}
			default -> {
				return ResponseEntity.status(404).body(new MessageDTO(
					"Feature not found",
					CodeStatus.NOT_FOUND
				));
			}
		}
	}

	@PutMapping("/create")
	@Operation(
		summary = "Create Feature",
		description = "Create a new feature. Please provide the feature details in the request body. The feature details are mapped to the Feature entity. Please refer to the FeatureTransactionDTO for more details.",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			required = true,
			content = @io.swagger.v3.oas.annotations.media.Content(
				mediaType = "application/json",
				schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = FeatureTransactionDTO.class)
			)
		),
		responses = {
			@ApiResponse(
				description = "Feature created successfully",
				content = {
					@io.swagger.v3.oas.annotations.media.Content(
						mediaType = "application/json",
						schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = FeatureTransactionDTO.class)
					)
				},
				responseCode = "201"
			),
			@ApiResponse(
				description = "Feature cannot be empty, this occurs when no request body was provided leading to null value",
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
	public ResponseEntity<?> createFeature(@RequestBody FeatureTransactionDTO featureTransactionDTO) {
		logger.debug("Creating a new feature for product");
		// Check if the feature id is valid
		if (featureTransactionDTO == null || featureTransactionDTO.getProductId() == null) {
			logger.error("Feature or Product cannot be empty, this occurs when no request body was provided leading to null value. Please provide the feature details in the request body.");
			return ResponseEntity.badRequest().body(new MessageDTO(
				"Feature or Product cannot be empty, this occurs when no request body was provided leading to null value. Please provide the feature details in the request body.",
				CodeStatus.INVALID
			));
		}

		// ! Get product and check for its existence.
		logger.debug("Fetching product with id: {}", featureTransactionDTO.getProductId());
		Optional<Product> product = this.productService.getProduct(featureTransactionDTO.getProductId());
		if (product.isEmpty()) {
			logger.error("Product not found");
			return ResponseEntity.status(404).body(new MessageDTO(
				"Product not found",
				CodeStatus.NOT_FOUND
			));
		}

		logger.debug("Creating feature");
		// Convert the DTO to entity
		Feature feature = new Feature(
			null,
			product.get(),
			featureTransactionDTO.getName(),
			featureTransactionDTO.getValue()
		);

		// Create the feature
		CodeStatus status = this.featureService.createFeature(
			feature
		);
		logger.debug("Feature created with status: {}", status);

		// Return the result
		if (status == CodeStatus.OK) { // ! If feature is created
			logger.debug("Feature created successfully");
			return ResponseEntity.status(200).body(new MessageDTO(
				"Feature created successfully",
				CodeStatus.OK
			));
		}

		logger.error("Feature cannot be empty, this occurs when no request body was provided leading to null value. Please provide the feature details in the request body.");
		// ! If feature cannot be created due to invalid details in the data.
		return ResponseEntity.status(400).body(new MessageDTO(
			"Feature cannot be empty, this occurs when no request body was provided leading to null value. Please provide the feature details in the request body.",
			CodeStatus.INVALID
		));
	}

	@GetMapping("/{id}")
	@Operation(
		summary = "Get Feature",
		description = "Get feature by providing the feature id. Provide a valid feature id.<br><br>Example: <a href=\"http://localhost:8080/api/v1/features/1\">http://localhost:8080/api/v1/features/1</a>",
		responses = {
			@ApiResponse(
				description = "Feature found",
				content = {
					@io.swagger.v3.oas.annotations.media.Content(
						mediaType = "application/json",
						schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Feature.class)
					)
				},
				responseCode = "200"
			),
			@ApiResponse(
				description = "Feature not found",
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
	@ApiResponses(
		value = {
			@ApiResponse(
				responseCode = "400",
				description = "Invalid ID",
				content = {
					@io.swagger.v3.oas.annotations.media.Content(
						mediaType = "application/json",
						schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MessageDTO.class)
					)
				}
			)
		}
	)
	public ResponseEntity<?> getFeature(@PathVariable Integer id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().body(new MessageDTO(
				String.format("Invalid ID %s was provided.", id),
				CodeStatus.INVALID
			));
		}
		Optional<Feature> featureOptional = this.featureService.getFeature(id);
		if (featureOptional.isPresent()) {
			return ResponseEntity.ok(
				modelMapper.map(featureOptional.get(), FeatureDTO.class)
			);
		}

		return ResponseEntity.status(404).body(new MessageDTO(
			"Feature not found",
			CodeStatus.NOT_FOUND
		));
	}
}