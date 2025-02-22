package com.bombi.core.domain.productionCondition.model;

import static jakarta.persistence.FetchType.*;

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

	private String overwintering;
	private String averageTemperature;
	private String minTemperature;
	private String maxTemperature;
	private String annualRainfall;
	private String sunlightHours;
	private String drainage;
	private String soilDepth;
	private String ph;



}
