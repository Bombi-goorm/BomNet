package com.bombi.core.application.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.bombi.core.infrastructure.external.price.chart.client.PriceChartLinkApiClient;
import com.bombi.core.infrastructure.external.price.chart.client.PriceChartNodeApiClient;
import com.bombi.core.infrastructure.external.price.chart.dto.ChartLinkInfo;
import com.bombi.core.infrastructure.external.price.chart.dto.ChartNodeInfo;
import com.bombi.core.presentation.dto.price.chart.LinkInformation;
import com.bombi.core.presentation.dto.price.chart.NodeInformation;
import com.bombi.core.presentation.dto.price.chart.SankeyDataResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SankeyChartPriceService {

	private final PriceChartNodeApiClient priceChartNodeApiClient;
	private final PriceChartLinkApiClient priceChartLinkApiClient;

	public SankeyDataResponseDto findSankeyChartInfo(String item) {
		String yesterday = LocalDate.now().minusDays(1).toString();

		// 전체 노드 정보
		List<ChartNodeInfo> nodeInfos = priceChartNodeApiClient.getNodes(item, yesterday);

		// 전체 링크 정보
		List<ChartLinkInfo> linkInfos = priceChartLinkApiClient.getLinks(item, yesterday);

		// 링크에서 노드 추출
		Set<Long> sourceNodeIds = extractSourceNodeFromLink(linkInfos);
		Set<Long> targetNodeIds = extractTargetNodeFromLink(linkInfos);

		// 작물 노드 추출
		HashSet<Long> productNodeIds = new HashSet<>(sourceNodeIds);
		productNodeIds.removeAll(targetNodeIds);

		List<ChartNodeInfo> productNodeInfos = nodeInfos.stream()
			.filter(node -> productNodeIds.contains(node.getId()))
			.collect(Collectors.toList());

		// 생산지 노드 추출
		HashSet<Long> areaNodeIds = new HashSet<>(sourceNodeIds);
		areaNodeIds.retainAll(targetNodeIds);

		List<ChartNodeInfo> areaNodeInfos = nodeInfos.stream()
			.filter(node -> areaNodeIds.contains(node.getId()))
			.collect(Collectors.toList());

		// 시장 노드 추출
		HashSet<Long> marketNodeIds = new HashSet<>(targetNodeIds);
		marketNodeIds.removeAll(sourceNodeIds);

		List<ChartNodeInfo> marketNodeInfos = nodeInfos.stream()
			.filter(node -> marketNodeIds.contains(node.getId()))
			.collect(Collectors.toList());

		// 노드 정보 합치기
		List<ChartNodeInfo> sortedNodeInfo = Stream.of(productNodeInfos, areaNodeInfos, marketNodeInfos)
			.flatMap(List::stream)
			.collect(Collectors.toList());

		// sankey chart를 위한 인덱싱 처리
		updateIndex(sortedNodeInfo, linkInfos);

		List<NodeInformation> nodeInformations = createTotalNodeInfo(productNodeInfos, areaNodeInfos, marketNodeInfos);
		List<LinkInformation> linkInformations = createTotalLinkInfo(linkInfos);

		return new SankeyDataResponseDto(nodeInformations, linkInformations);
	}

	private Set<Long> extractSourceNodeFromLink(List<ChartLinkInfo> linkInfos) {
		return linkInfos.stream()
			.map(ChartLinkInfo::getSource)
			.collect(Collectors.toSet());
	}

	private Set<Long> extractTargetNodeFromLink(List<ChartLinkInfo> linkInfos) {
		return linkInfos.stream()
			.map(ChartLinkInfo::getTarget)
			.collect(Collectors.toSet());
	}

	private void updateIndex(List<ChartNodeInfo> nodeInfos, List<ChartLinkInfo> linkInfos) {
		Map<Long, Integer> indexConvertMap = new LinkedHashMap<>();

		for (int index = 0; index < nodeInfos.size(); index++) {
			ChartNodeInfo chartNodeInfo = nodeInfos.get(index);
			indexConvertMap.put(chartNodeInfo.getId(), index);

			chartNodeInfo.updateId(index);
		}

		for (ChartLinkInfo linkInfo : linkInfos) {
			long sourceId = linkInfo.getSource();
			long targetId = linkInfo.getTarget();

			if(indexConvertMap.containsKey(sourceId)) {
				linkInfo.updateSource(indexConvertMap.get(sourceId));
			}

			if(indexConvertMap.containsKey(targetId)) {
				linkInfo.updateTarget(indexConvertMap.get(targetId));
			}
		}
	}

	private List<NodeInformation> createTotalNodeInfo(
		List<ChartNodeInfo> productNodeInfos,
		List<ChartNodeInfo> areaNodeInfos,
		List<ChartNodeInfo> marketNodeInfos)
	{
		List<NodeInformation> productNodes = productNodeInfos.stream()
			.map(nodeInfo -> new NodeInformation(nodeInfo.getId(), nodeInfo.getName(), 0))
			.collect(Collectors.toList());
		List<NodeInformation> areaNodes = areaNodeInfos.stream()
			.map(nodeInfo -> new NodeInformation(nodeInfo.getId(), nodeInfo.getName(), 1))
			.collect(Collectors.toList());
		List<NodeInformation> marketNodes = marketNodeInfos.stream()
			.map(nodeInfo -> new NodeInformation(nodeInfo.getId(), nodeInfo.getName(), 2))
			.collect(Collectors.toList());

		return Stream.of(productNodes, areaNodes, marketNodes)
			.flatMap(List::stream)
			.toList();
	}

	private List<LinkInformation> createTotalLinkInfo(List<ChartLinkInfo> linkInfos) {
		return linkInfos.stream()
			.map(link -> new LinkInformation(link.getSource(), link.getTarget(), link.getValue()))
			.toList();
	}
}
