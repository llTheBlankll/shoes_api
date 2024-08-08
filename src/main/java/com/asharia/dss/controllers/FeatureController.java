package com.***REMOVED***.dss.controllers;

import com.***REMOVED***.dss.services.FeatureService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
	public ResponseEntity<?> listAllFeatures(@PageableDefault(sort = "id", direction = Sort.Direction.ASC ) Pageable page) {
		return ResponseEntity.ok(featureService.listAllFeatures(page));
	}
}