package com.***REMOVED***.dss.controllers;

import com.***REMOVED***.dss.models.dto.FeatureDTO;
import com.***REMOVED***.dss.models.dto.MessageDTO;
import com.***REMOVED***.dss.models.entities.Feature;
import com.***REMOVED***.dss.models.enums.CodeStatus;
import com.***REMOVED***.dss.services.FeatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/features")
@Tag(name = "Feature", description = "Feature API")
@Log4j2
public class FeatureController {

	private final FeatureService featureService;
	private final ModelMapper mapper = new ModelMapper();

	public FeatureController(FeatureService featureService) {
		this.featureService = featureService;
	}

	@GetMapping("/all")
	@Operation(
		summary = "List All Features",
		description = "This will list all distinct features for every product. Listing features is not recommended as they came from all products.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
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
	public ResponseEntity<?> listAllFeatures(@PageableDefault(sort = "id", direction = Sort.Direction.ASC ) Pageable page) {
		return ResponseEntity.ok(featureService.listAllFeatures(page));
	}

	@PostMapping("/update")
	public ResponseEntity<MessageDTO> updateFeature(
		@RequestBody FeatureDTO feature) {
		CodeStatus status = featureService.updateFeature(mapper.map(feature, Feature.class));
		switch (status) {
			case OK -> {
				return ResponseEntity.ok(new MessageDTO("Feature updated successfully", CodeStatus.OK));
			}
			case INVALID -> {
				return ResponseEntity.badRequest().body(new MessageDTO("Feature cannot be empty", CodeStatus.INVALID));
			}
			default -> {
				return ResponseEntity.badRequest().body(new MessageDTO("Feature not found", CodeStatus.NOT_FOUND));
			}
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MessageDTO> deleteFeature(@PathVariable Integer id) {
		// Check if the feature id is valid
		if (id <= 0) {
			log.debug("Feature id is null or invalid, cannot delete the feature");
			return ResponseEntity.badRequest().body(new MessageDTO("Feature id is null or invalid, cannot delete the feature", CodeStatus.INVALID));
		}

		CodeStatus status = featureService.deleteFeature(id);
		switch (status) {
			case OK -> {
				return ResponseEntity.ok(new MessageDTO("Feature deleted successfully", CodeStatus.OK));
			}
			case INVALID -> {
				return ResponseEntity.badRequest().body(new MessageDTO("Feature cannot be empty", CodeStatus.INVALID));
			}
			default -> {
				return ResponseEntity.badRequest().body(new MessageDTO("Feature not found", CodeStatus.NOT_FOUND));
			}
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getFeature(@PathVariable Integer id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().body(new MessageDTO("Feature id is null or invalid", CodeStatus.INVALID));
		}

		Optional<Feature> featureOptional = featureService.getFeature(id);
		if (featureOptional.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageDTO("Feature not found", CodeStatus.NOT_FOUND));
		}

		return ResponseEntity.ok(featureOptional.get());
	}
}