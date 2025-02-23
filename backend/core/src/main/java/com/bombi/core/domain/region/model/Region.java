package com.bombi.core.domain.region.model;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "region_id")
	private Long id;

	@Column(columnDefinition = "VARCHAR(20) NOT NULL")
	@Comment("기상청 시도 코드")
	private String weatherSiDoCode;

	@Column(columnDefinition = "VARCHAR(20) NOT NULL")
	@Comment("기상청 시군구 코드")
	private String weatherSiGunGuCode;

	@Column(columnDefinition = "VARCHAR(30) NOT NULL")
	@Comment("시군구 코드")
	private String siGunGuCode;

	@Column(columnDefinition = "VARCHAR(30) NOT NULL")
	@Comment("시군구 이름")
	private String siGunGuName;

	@Column(columnDefinition = "VARCHAR(10) NOT NULL")
	@Comment("관측지점 번호")
	private String stationNumber;

	@Column(columnDefinition = "VARCHAR(20) NOT NULL")
	@Comment("관측 지점명")
	private String stationName;

	@Column(columnDefinition = "VARCHAR(20) NOT NULL")
	@Comment("예보 구역코드")
	private String forecastZoneCode;

	@Column(columnDefinition = "VARCHAR(20) NOT NULL")
	@Comment("특보 구역코드")
	private String specialZoneCode;

	@Column(columnDefinition = "VARCHAR(40) NOT NULL")
	@Comment("특보 구역명")
	private String specialZoneName;

	@Column(columnDefinition = "VARCHAR(5) NOT NULL")
	@Comment("위도")
	private String x;

	@Column(columnDefinition = "VARCHAR(5) NOT NULL")
	@Comment("경도")
	private String y;


}
