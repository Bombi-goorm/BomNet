package com.bombi.core.fasttest.pricechart;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product/chart")
public class PriceChartController {

	private final PriceChartNodeApiClient priceChartNodeApiClient;
	private final PriceChartLinkApiClient priceChartLinkApiClient;

	@GetMapping("/node")
	public ResponseEntity<?> priceNodeChart() {
		// PriceChartNodeResponse response = priceChartNodeApiClient.sendChartNode("후지");
		return null;
	}

	@GetMapping
	public ResponseEntity<?> priceLinkAndNodeChart() {
		List<String> productNames = List.of("미얀마", "미시마", "후지");
		String date = "2025-03-07";

		// 품종 id 찾기
		List<ChartNodeInfo> productNodeInfos = priceChartNodeApiClient.getNodeInfoByNodeNames(productNames);
		productNodeInfos.sort(Comparator.comparing(ChartNodeInfo::getId, Comparator.comparing(Integer::parseInt)));

		// 품종 - 생산지
		List<String> productNodeIds = productNodeInfos.stream()
			.map(ChartNodeInfo::getId)
			.toList();
		List<SankeyLinkDto> linksFromProductToProductionArea = priceChartLinkApiClient.getChartLinkByNodeIds(productNodeIds, date);

		// 생산지 - 시장
		List<String> productionAreaNodeIds = linksFromProductToProductionArea.stream()
			.map(SankeyLinkDto::getTarget)
			.distinct()
			.toList();
		List<SankeyLinkDto> linksFromProductAreaToMarket = priceChartLinkApiClient.getChartLinkByNodeIds(productionAreaNodeIds, date);

		// 생산지 노드 정보 가져오기
		List<ChartNodeInfo> productionAreaNodeInfos = linksFromProductAreaToMarket.stream()
			.map(sankeyLinkDto -> new ChartNodeInfo(sankeyLinkDto.getSource(), sankeyLinkDto.getName()))
			.distinct()
			.collect(Collectors.toList());

		// 시장 노드 정보 가져오기
		List<String> marketIds = linksFromProductAreaToMarket.stream().map(SankeyLinkDto::getTarget).distinct().toList();
		List<ChartNodeInfo> marketNodeInfos = priceChartNodeApiClient.getNodeInfoByNodeIds(marketIds);

		List<TotalNodeInfo> totalNodeInfoResponse = createTotalNodeInfoResponse(productNodeInfos,
			productionAreaNodeInfos, marketNodeInfos);

		List<LinkInformation> linkInformationResponse = createLinkInformationResponse(linksFromProductToProductionArea,
			linksFromProductAreaToMarket);

		return ResponseEntity.ok(new SankeyDataResponseDto(totalNodeInfoResponse,  linkInformationResponse));
	}

	private List<TotalNodeInfo> createTotalNodeInfoResponse(
		List<ChartNodeInfo> productNodeInfos,
		List<ChartNodeInfo> productionAreaNodeInfos,
		List<ChartNodeInfo> marketNodeInfos) {
		productNodeInfos.sort(Comparator.comparing(ChartNodeInfo::getId, Comparator.comparing(Integer::parseInt)));
		productionAreaNodeInfos.sort(Comparator.comparing(ChartNodeInfo::getId, Comparator.comparing(Integer::parseInt)));
		marketNodeInfos.sort(Comparator.comparing(ChartNodeInfo::getId, Comparator.comparing(Integer::parseInt)));

		return Stream.of(productNodeInfos, productionAreaNodeInfos, marketNodeInfos)
			.flatMap(List::stream)
			.map(chartNodeInfo -> new TotalNodeInfo(chartNodeInfo.getName()))
			.toList();
	}

	private List<LinkInformation> createLinkInformationResponse(
		List<SankeyLinkDto> linksFromProductToProductionArea,
		List<SankeyLinkDto> linksFromProductAreaToMarket) {

		return Stream.concat(linksFromProductToProductionArea.stream(),
				linksFromProductAreaToMarket.stream())
			.map(LinkInformation::of)
			.toList();
	}



}
