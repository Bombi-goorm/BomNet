package com.bombi.core.domain.region.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bombi.core.domain.region.model.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {

	Optional<Region> findByWeatherSiGunGuCode(String weatherSiGunGuCode);

	Optional<Region> findByStationName(String stationName);
}
