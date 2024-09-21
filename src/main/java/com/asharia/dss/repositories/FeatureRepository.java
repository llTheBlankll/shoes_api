package com.asharia.dss.repositories;

import com.asharia.dss.models.entities.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<Feature, Integer> {
}