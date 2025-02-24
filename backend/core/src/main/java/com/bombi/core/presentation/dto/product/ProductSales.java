package com.bombi.core.presentation.dto.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductSales {
	private String distributionQuantityTon; // 지역별 유통량
	private String domesticSalesTon; // 국내 총 판매량(총 유통량)
	private String exportsTon; // 수출량
	private String importsTon; // 수입량
}
