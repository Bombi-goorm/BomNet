package com.bombi.core.domain.region.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bombi.core.domain.region.model.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {

	Optional<Region> findByWeatherSiDoCode(String weatherSidoCode);

	Optional<Region> findByStationNumber(String stationNumber);

	@Query("select r.siGunGuName from Region r where r.stationNumber = :stationNumber")
	Optional<Region> findSiDoNameByStationNumber(String stationNumber);
}
