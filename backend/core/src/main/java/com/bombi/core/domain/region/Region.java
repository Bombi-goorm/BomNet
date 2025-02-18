package com.bombi.core.domain.region;

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
	@Comment("시도 이름")
	private String sidoName;

	@Column(columnDefinition = "VARCHAR(30) NOT NULL")
	@Comment("시군구 이름")
	private String destrictName;

	@Column(columnDefinition = "VARCHAR(20) NOT NULL")
	@Comment("시도 코드")
	private String sidoCode;

	@Column(columnDefinition = "VARCHAR(20) NOT NULL")
	@Comment("기상청 시도 코드")
	private String kmaSidoCode;

	@Column(columnDefinition = "VARCHAR(20) NOT NULL")
	@Comment("기상청 시군구 코드")
	private String kmaDestrictCode;

	@Column(columnDefinition = "VARCHAR(10) NOT NULL")
	@Comment("관측지점 번호")
	private String stationId;

	@Column(columnDefinition = "VARCHAR(20) NOT NULL")
	@Comment("관측 지점명")
	private String stationName;

	@Column(columnDefinition = "VARCHAR(20) NOT NULL")
	@Comment("예보 구역명")
	private String forecastRegionId;

	@Column(columnDefinition = "VARCHAR(20) NOT NULL")
	@Comment("특보 구역코드")
	private String warningRegionId;

	@Column(columnDefinition = "VARCHAR(40) NOT NULL")
	@Comment("특보 구역명")
	private String warningRegionName;


}
