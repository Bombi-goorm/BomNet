package com.bombi.core.domain.region.model;

import static jakarta.persistence.FetchType.*;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegionWeather {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "region_weather_id")
	private Long id;

	@Comment("평균 기온")
	private int averageTemperature;

	@Comment("최저 기온")
	private int minTemperature;

	@Comment("최고 기온")
	private int maxTemperature;

	@Comment("연간 강수량")
	private int annualPrecipitation;

	@Comment("연간 일조량")
	private int annualSunlightHours;

	@Comment("관측 지점 코드")
	private String stationId;

	@Comment("관측 지점명")
	private String stationName;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "region_id")
	private Region region;

}
