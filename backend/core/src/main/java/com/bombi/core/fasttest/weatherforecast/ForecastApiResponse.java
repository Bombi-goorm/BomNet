package com.bombi.core.fasttest.weatherforecast;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ForecastApiResponse {

	@JsonProperty("response")
	private Response response;

	@Getter
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Response {
		@JsonProperty("header")
		private Header header;

		@JsonProperty("body")
		private Body body;
	}

	@Getter
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Header {
		@JsonProperty("resultCode")
		private String resultCode;

		@JsonProperty("resultMsg")
		private String resultMsg;
	}

	@Getter
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Body {
		@JsonProperty("dataType")
		private String dataType;

		@JsonProperty("items")
		private Items items;

		@JsonProperty("pageNo")
		private int pageNo;

		@JsonProperty("numOfRows")
		private int numOfRows;

		@JsonProperty("totalCount")
		private int totalCount;
	}

	@Getter
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Items {
		@JsonProperty("item")
		private List<Item> itemList;
	}

	@Getter
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Item {
		@JsonProperty("baseDate")
		private String baseDate;

		@JsonProperty("baseTime")
		private String baseTime;

		@JsonProperty("category")
		private String category;

		@JsonProperty("fcstDate")
		private String fcstDate;

		@JsonProperty("fcstTime")
		private String fcstTime;

		@JsonProperty("fcstValue")
		private String fcstValue;

		@JsonProperty("nx")
		private int nx;

		@JsonProperty("ny")
		private int ny;
	}

}
