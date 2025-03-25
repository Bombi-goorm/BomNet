package com.bombi.core.infrastructure.external.weather;

import java.util.Arrays;
import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum WeatherStatus {

	CLEAR(
		"맑음",
		Set.of(SkyStatus.CLEAR),
		Set.of(PrecipitationType.NONE)),
	CLOUDY(
		"흐림",
		Set.of(SkyStatus.CLOUDY, SkyStatus.FADE),
		Set.of(PrecipitationType.NONE)),
	RAIN(
		"비",
		Set.of(SkyStatus.CLOUDY, SkyStatus.FADE),
		Set.of(PrecipitationType.RAIN, PrecipitationType.RAIN_AND_SNOW, PrecipitationType.SHOWER)),
	SNOW(
		"눈",
		Set.of(SkyStatus.CLOUDY, SkyStatus.FADE),
		Set.of(PrecipitationType.SNOW, PrecipitationType.RAIN_AND_SNOW));

	private final String description;
	private final Set<SkyStatus> skyStatuses;
	private final Set<PrecipitationType> precipitationTypes;

	public static String findDescriptionByInfo(SkyStatus skyStatus, PrecipitationType precipitationType, String temperature) {
		try {
			if (precipitationType == PrecipitationType.RAIN_AND_SNOW) {
				int temperatureInt = Integer.parseInt(temperature);
				if(temperatureInt > 0) {
					return RAIN.getDescription();
				}
				return SNOW.getDescription();
			}

			WeatherStatus weatherStatus = Arrays.stream(values())
				.filter(status -> status.getSkyStatuses().contains(skyStatus))
				.filter(status -> status.getPrecipitationTypes().contains(precipitationType))
				.findFirst()
				.orElse(CLEAR);

			return weatherStatus.getDescription();
		} catch (NumberFormatException e) {
			log.error("유효하지 않은 온도값 : {}", temperature);
			return CLEAR.getDescription();
		}
	}
}
