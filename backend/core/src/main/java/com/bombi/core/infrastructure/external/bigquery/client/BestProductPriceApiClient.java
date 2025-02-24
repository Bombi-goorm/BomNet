package com.bombi.core.infrastructure.external.bigquery.client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bombi.core.presentation.dto.home.ProductPriceInfo;
import com.bombi.core.presentation.dto.home.ProductPriceResponse;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BestProductPriceApiClient {

	private final BigQuery bigQuery;

	public List<ProductPriceResponse> callBestProductPrice(List<String> productCodes) {

		String query = "SELECT"
			+ "    SALEDATE,"
			+ "    avg_cost_per_kg AS price,"
			+ "    MIDNAME"
			+ " FROM `goorm-bomnet.mafra.trans_auction`"
			+ " WHERE MIDNAME IN ('사과', '블루베리', '딸기', '참외', '오이')"
			+ " ORDER BY SALEDATE;";

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			.setUseLegacySql(false) // 표준 SQL 사용
			.build();

		try {
			TableResult result = bigQuery.query(queryConfig);

			List<ProductPriceResponse> responses = convertToResponse(result);

			return responses;
		} catch (InterruptedException e) {
			throw new RuntimeException("BigQuery 쿼리 실행 중 오류 발생", e);
		}
	}

	private List<ProductPriceResponse> convertToResponse(TableResult result) {
		Map<String, List<ProductPriceInfo>> resultMap = new HashMap<>();

		for (FieldValueList row : result.iterateAll()) {

			String productName = row.get(2).getStringValue();
			String price = row.get(1).getStringValue();
			String date = row.get(0).getStringValue();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			LocalDate localDate = LocalDate.parse(date, formatter);

			ProductPriceInfo productPriceInfo = new ProductPriceInfo(localDate, price);
			resultMap.computeIfAbsent(productName, k -> new ArrayList<>()).add(productPriceInfo);
		}

		List<ProductPriceResponse> responses = new ArrayList<>();

		for (Map.Entry<String, List<ProductPriceInfo>> entry : resultMap.entrySet()) {
			ProductPriceResponse productPriceResponse = new ProductPriceResponse(entry.getKey(), entry.getValue());
			responses.add(productPriceResponse);
		}
		return responses;
	}

}
