package com.***REMOVED***.dss.repositories;

import com.***REMOVED***.dss.models.entities.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;

public interface FeatureRepository extends JpaRepository<Feature, Integer> {
}