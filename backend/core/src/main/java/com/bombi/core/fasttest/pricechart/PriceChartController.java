package com.bombi.core.fasttest.pricechart;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.infrastructure.external.chart.client.PriceChartLinkApiClient;
import com.bombi.core.infrastructure.external.chart.client.PriceChartNodeApiClient;
import com.bombi.core.infrastructure.external.chart.dto.ChartLinkInfo;
import com.bombi.core.infrastructure.external.chart.dto.ChartNodeInfo;
import com.bombi.core.presentation.dto.price.chart.LinkInformation;
import com.bombi.core.presentation.dto.price.chart.SankeyDataResponseDto;
import com.bombi.core.presentation.dto.price.chart.TotalNodeInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product/chart")
public class PriceChartController {

	private final PriceChartNodeApiClient priceChartNodeApiClient;
	private final PriceChartLinkApiClient priceChartLinkApiClient;

	@GetMapping("/node")
	ResponseEntity<?> priceNode() {
		List<ChartNodeInfo> response = priceChartNodeApiClient.getNodes("사과", "2025-03-07");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/link")
	ResponseEntity<?> priceLink() {
		List<ChartLinkInfo> response = priceChartLinkApiClient.getLinks("사과", "2025-03-07");
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<?> priceLinkAndNodeChart() {
		String date = "2025-03-07";
		String item = "사과";

		// 전체 노드 정보
		List<ChartNodeInfo> nodeInfos = priceChartNodeApiClient.getNodes(item, date);
		// 전체 링크 정보
		List<ChartLinkInfo> linkInfos = priceChartLinkApiClient.getLinks(item, date);

		// 작물 노드 정보
		List<String> productNames = List.of("미얀마", "미시마", "후지");
		List<ChartNodeInfo> productNodeInfos = nodeInfos.stream()
			.filter(node -> productNames.contains(node.getName()))
			.sorted(Comparator.comparing(ChartNodeInfo::getId))
			.toList();
		List<Long> productNodeIds = productNodeInfos.stream().map(ChartNodeInfo::getId).toList();

		// 작물 - 생산지 링크
		List<ChartLinkInfo> linksFromProductToArea = linkInfos.stream()
			.filter(link -> productNodeIds.contains(link.getSource()))
			.collect(Collectors.toList());
		List<Long> areaNodeIds = linksFromProductToArea.stream().map(ChartLinkInfo::getTarget).distinct().toList();

		// 생산지 노드 정보
		List<ChartNodeInfo> areaNodeInfos = nodeInfos.stream()
			.filter(node -> areaNodeIds.contains(node.getId()))
			.sorted(Comparator.comparing(ChartNodeInfo::getId))
			.toList();

		// 생산지 - 시장 링크 정보
		List<ChartLinkInfo> linksFromAreaToMarket = linkInfos.stream()
			.filter(link -> areaNodeIds.contains(link.getSource()))
			.collect(Collectors.toList());
		List<Long> marketNodeIds = linksFromAreaToMarket.stream().map(ChartLinkInfo::getTarget).distinct().toList();

		// 시장 노드 정보
		List<ChartNodeInfo> marketNodeInfos = nodeInfos.stream()
			.filter(node -> marketNodeIds.contains(node.getId()))
			.sorted(Comparator.comparing(ChartNodeInfo::getId))
			.toList();

		List<TotalNodeInfo> totalNodeInfos = createTotalNodeIfno(productNodeInfos, areaNodeInfos, marketNodeInfos);
		List<LinkInformation> linkInformations = createLinkInfo(linksFromProductToArea, linksFromAreaToMarket);

		SankeyDataResponseDto response = new SankeyDataResponseDto(totalNodeInfos, linkInformations);
		return ResponseEntity.ok(response);
	}

	private List<TotalNodeInfo> createTotalNodeIfno(
		List<ChartNodeInfo> productNodeInfos,
		List<ChartNodeInfo> areaNodeInfos,
		List<ChartNodeInfo> marketNodeInfos)
	{
		return Stream.of(productNodeInfos, areaNodeInfos, marketNodeInfos)
			.flatMap(List::stream)
			.map(nodeInfo -> new TotalNodeInfo(nodeInfo.getId(), nodeInfo.getName()))
			.toList();
	}

	private List<LinkInformation> createLinkInfo(List<ChartLinkInfo> linksFromProductToArea,
		List<ChartLinkInfo> linksFromAreaToMarket) {
		linksFromProductToArea.addAll(linksFromAreaToMarket);
		linksFromProductToArea.addAll(linksFromAreaToMarket);
		return linksFromProductToArea.stream()
			.map(link -> new LinkInformation(link.getSource(), link.getTarget(), link.getValue()))
			.toList();
	}

}
