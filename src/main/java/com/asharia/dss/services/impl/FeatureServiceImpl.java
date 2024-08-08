package com.***REMOVED***.dss.services.impl;

import com.***REMOVED***.dss.models.entities.Feature;
import com.***REMOVED***.dss.models.enums.CodeStatus;
import com.***REMOVED***.dss.repositories.FeatureRepository;
import com.***REMOVED***.dss.services.FeatureService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class FeatureServiceImpl implements FeatureService {

	private final FeatureRepository featureRepository;

	public FeatureServiceImpl(FeatureRepository featureRepository) {
		this.featureRepository = featureRepository;
	}

	/**
	 * This will list all the features available in the system, the features are paginated and sorted by default.
	 *
	 * @param page Pageable object
	 * @return {@link Page < Feature >}
	 */
	@Override
	public Page<Feature> listAllFeatures(Pageable page) {
		if (page == null) {
			log.error("Pageable object is null, listing all features without pagination");
			return featureRepository.findAll(Pageable.unpaged());
		}
		log.debug("Listing all features in page {} with size {}", page.getPageNumber(), page.getPageSize());
		return featureRepository.findAll(page);
	}

	/**
	 * Create a new feature for a product
	 *
	 * @param feature Feature object
	 * @return {@link CodeStatus}
	 */
	@Override
	public CodeStatus createFeature(Feature feature) {
		if (feature == null) {
			log.debug("Feature object is null, cannot create the feature");
			return CodeStatus.INVALID;
		}

		log.debug("Creating a new feature for product {}", feature.getId());
		featureRepository.save(feature);
		return CodeStatus.OK;
	}

	/**
	 * Update a feature for a product by feature id
	 *
	 * @param featureId Feature id
	 * @return {@link CodeStatus}
	 */
	@Override
	public CodeStatus deleteFeature(Integer featureId) {
		// Check if the feature id is valid
		if (featureId <= 0) {
			log.debug("Feature id is null or invalid, cannot delete the feature");
			return CodeStatus.INVALID;
		}

		// Check if the feature exists
		Optional<Feature> feature = featureRepository.findById(featureId);

		if (feature.isEmpty()) {
			log.debug("Feature with id {} does not exist, cannot delete the feature", featureId);
			return CodeStatus.NOT_FOUND;
		}

		log.debug("Deleting the feature with id {}", featureId);
		featureRepository.delete(feature.get());
		return CodeStatus.OK;
	}

	/**
	 * Update the feature details, the feature id is used to identify the feature and update the details.
	 *
	 * @param feature Feature object
	 * @return {@link CodeStatus}
	 */
	@Override
	public CodeStatus updateFeature(Feature feature) {
		if (feature == null) {
			log.debug("Feature object is null, cannot update the feature");
			return CodeStatus.INVALID;
		}

		// Check if the feature exists
		Optional<Feature> featureOptional = featureRepository.findById(feature.getId());

		if (featureOptional.isEmpty()) {
			log.debug("Feature with id {} does not exist, cannot update the feature", feature.getId());
			return CodeStatus.NOT_FOUND;
		}

		log.debug("Updating the feature with id {}", feature.getId());
		featureRepository.save(feature);
		return CodeStatus.OK;
	}

	/**
	 * Get the feature details by feature id
	 *
	 * @param featureId Feature id
	 * @return {@link Feature}
	 */
	@Override
	public Optional<Feature> getFeature(Integer featureId) {
		return featureRepository.findById(featureId);
	}
}