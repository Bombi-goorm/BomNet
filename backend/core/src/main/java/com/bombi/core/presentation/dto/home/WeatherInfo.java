package com.bombi.core.presentation.dto.home;

import java.time.LocalDateTime;
import java.util.List;

import com.bombi.core.fasttest.weatherforecast.ForecastInfoDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeatherInfo {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime forecastTime; // 예보 시간

	private String weather; // 날씨
	private String temperature; // 기온
	private String humidity; // 습도
	private String wind; // 풍속

	public WeatherInfo(LocalDateTime forecastTime, List<ForecastInfoDto> forecastInfo) {
		this.forecastTime = forecastTime;
		for (ForecastInfoDto dto : forecastInfo) {
			switch (dto.getForecastType()) {
				case "TMP": {
					this.temperature = dto.getForecastDescription();
					break;
				}
				case "WSD": {
					this.wind = dto.getForecastDescription();
					break;
				}
				case "SKY": {
					this.weather = dto.getForecastDescription();
					break;
				}
				case "REH": {
					this.humidity = dto.getForecastDescription();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
}
