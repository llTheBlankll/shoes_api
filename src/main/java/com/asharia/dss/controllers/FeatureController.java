package com.***REMOVED***.dss.controllers;

import com.***REMOVED***.dss.services.FeatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/features")
@Tag(name = "Feature", description = "Feature API")
public class FeatureController {

	private final FeatureService featureService;

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
}