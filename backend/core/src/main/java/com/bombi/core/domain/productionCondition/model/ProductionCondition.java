package com.bombi.core.domain.productionCondition.model;

import static jakarta.persistence.FetchType.*;

import org.hibernate.annotations.Comment;

import com.bombi.core.domain.base.model.BaseEntity;
import com.bombi.core.domain.cultivation.model.Cultivation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class ProductionCondition extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "production_condition_id")
	private Long id;

	@JoinColumn(name = "cultivation_id")
	@OneToOne(fetch = LAZY)
	private Cultivation cultivation;

	@Comment("월동여부")
	private String overwintering;

	private String averageTemperature;
	private String minTemperature;
	private String maxTemperature;
	private String annualRainfall;
	private String sunlightHours;

	@Comment("배수 등급")
	private String drainage; // 배수 등급

	@Comment("유효 토심")
	private String soilDepth; // 유효토심

	@Comment("경사도")
	private String slopeDegree; // 경사도

	private String ph; // pH

	@Comment("토성")
	private String soilTexture; // 토성

	@Comment("유기물")
	private String organicMatterGPerKg; // 유기물

	@Comment("유효인산")
	private String avPMgPerKg; // 유효인산

	@Comment("칼륨")
	private String kMgPerKg; // 칼륨

	@Comment("칼슘")
	private String caMgPerKg; // 칼슘

	@Comment("마그네슘")
	private String mgMgPerKg; // 마그네슘

}
