package com.***REMOVED***.dss.services;

import com.***REMOVED***.dss.models.entities.Feature;
import com.***REMOVED***.dss.models.enums.CodeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FeatureService {

	/**
	 * This will list all the features available in the system, the features are paginated and sorted by default.
	 *
	 * @param page Pageable object
	 * @return {@link Page<Feature>}
	 */
	Page<Feature> listAllFeatures(Pageable page);

	/**
	 * Create a new feature for a product
	 *
	 * @param feature Feature object
	 * @return {@link CodeStatus}
	 */
	CodeStatus createFeature(Feature feature);

	/**
	 * Update a feature for a product by feature id
	 *
	 * @param featureId Feature id
	 * @return {@link CodeStatus}
	 */
	CodeStatus deleteFeature(Integer featureId);

	/**
	 * Update the feature details, the feature id is used to identify the feature and update the details.
	 *
	 * @param feature Feature object
	 * @return {@link CodeStatus}
	 */
	CodeStatus updateFeature(Feature feature);

	/**
	 * Get the feature details by feature id
	 *
	 * @param featureId Feature id
	 * @return {@link Feature}
	 */
	Optional<Feature> getFeature(Integer featureId);
}