package com.bombi.core.domain.region.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bombi.core.domain.region.model.RegionWeather;

public interface RegionWeatherRepository extends JpaRepository<RegionWeather, Long> {

	@Query("select rw from RegionWeather rw join fetch rw.region r where r.weatherSiGunGuCode = :weatherSiGunGuCode")
	Optional<RegionWeather> findWeatherInfoByPNU(@Param("weatherSiGunGuCode") String weatherSiGunGuCode);
}
