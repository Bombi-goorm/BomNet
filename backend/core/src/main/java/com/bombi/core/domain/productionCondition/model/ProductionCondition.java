package com.bombi.core.domain.productionCondition.model;

import static jakarta.persistence.FetchType.*;

import java.util.Arrays;
import java.util.List;

import org.hibernate.annotations.Comment;

import com.bombi.core.domain.base.model.BaseEntity;
import com.bombi.core.domain.cultivation.model.Cultivation;

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

	@Comment("산도")
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

	public boolean isPHSuitable(String soilPH) {
		double soilPHValue = Double.parseDouble(soilPH);
		double pHValue = Double.parseDouble(this.ph);

		return soilPHValue >= pHValue - 0.1 && soilPHValue <= pHValue + 0.1;
	}

	public boolean isOrganicMatterSuitable(String soilOrganicMatterGPerKg) {
		List<Double> orgnicMatterList = Arrays.stream(this.organicMatterGPerKg.split("-"))
			.map(Double::parseDouble)
			.toList();

		Double minOrganicMatterGPerKg = orgnicMatterList.get(0);
		Double maxOrganicMatterGPerKg = orgnicMatterList.get(1);

		double soilOrganicMatterValue = Double.parseDouble(soilOrganicMatterGPerKg);

		return soilOrganicMatterValue >= minOrganicMatterGPerKg && soilOrganicMatterValue <= maxOrganicMatterGPerKg;
	}

	public boolean isPhosphorusSuitable(String soilPhosphorus) {
		List<Integer> phosphorusList = Arrays.stream(this.avPMgPerKg.split("-"))
			.map(Integer::parseInt)
			.toList();

		Integer minPhosphorus = phosphorusList.get(0);
		Integer maxPhosphorus = phosphorusList.get(1);

		int soilPhosphorusValue = (int) (Double.parseDouble(soilPhosphorus));

		return soilPhosphorusValue >= minPhosphorus && soilPhosphorusValue <= maxPhosphorus;
	}

	public boolean isPotassiumSuitable(String soilKCMolPerKg) {
		List<Double> kMgPerKgList = Arrays.stream(this.kMgPerKg.split("-"))
			.map(Double::parseDouble)
			.toList();

		Double minPotassium = kMgPerKgList.get(0);
		Double maxPotassium = kMgPerKgList.get(1);

		double soilPotassiumValue = Double.parseDouble(soilKCMolPerKg);

		return soilPotassiumValue >= minPotassium && soilPotassiumValue <= maxPotassium;
	}

	public boolean isCalciumSuitable(String soilCaCMolPerKg) {
		List<Double> caMgPerKgList = Arrays.stream(this.caMgPerKg.split("-"))
			.map(Double::parseDouble)
			.toList();

		Double minCalcium = caMgPerKgList.get(0);
		Double maxCalcium = caMgPerKgList.get(1);

		double soilCalciumValue = Double.parseDouble(soilCaCMolPerKg);

		return soilCalciumValue >= minCalcium && soilCalciumValue <= maxCalcium;
	}

	public boolean isMagnesiumSuitable(String soilMgCMolPerKg) {
		List<Double> mgMgPerKgList = Arrays.stream(this.mgMgPerKg.split("-"))
			.map(Double::parseDouble)
			.toList();

		Double minMagnesium = mgMgPerKgList.get(0);
		Double maxMagnesium = mgMgPerKgList.get(1);

		double soilMagnesiumValue = Double.parseDouble(soilMgCMolPerKg);

		return soilMagnesiumValue >= minMagnesium && soilMagnesiumValue <= maxMagnesium;
	}

	public boolean isTemperatureSuitable(int soilAverageTemperature, int soilMaxTemperature, int soilMinTemperature) {
		int productMinTemperature = (int)Double.parseDouble(this.minTemperature);
		int productMaxTemperature = (int)Double.parseDouble(this.maxTemperature);

		return soilMinTemperature >= productMinTemperature && soilMaxTemperature <= productMaxTemperature;
	}

	public boolean isRainfallSuitable(int soilAnnualPrecipitation) {
		return soilAnnualPrecipitation >= (int)(Double.parseDouble(this.annualRainfall));
	}

	public boolean isSunlightSuitable(int soilAnnualSunlightHours) {
		return soilAnnualSunlightHours >= (int) (Double.parseDouble(this.sunlightHours));
	}
}
