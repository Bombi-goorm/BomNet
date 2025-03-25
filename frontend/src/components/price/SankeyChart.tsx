import React, { useEffect, useRef, useState } from "react";
import { Sankey, Tooltip, ResponsiveContainer } from "recharts";
import { PriceResponse } from "../../types/price_types";

interface SankeyChartProps {
  priceData: PriceResponse | null;
}

const SankeyChart: React.FC<SankeyChartProps> = ({ priceData }) => {
  const levelLabels = ["품종", "생산지", "유통지역"];

  const chartRef = useRef<HTMLDivElement>(null);
  const [chartWidth, setChartWidth] = useState<number>(800); // 기본값 800px


  // const linkData = priceData?.sankeyData.links ?? [];

  // const convertedLinks: SankeyLink[] = linkData.map(link => ({
  //   source: Number(link.source),
  //   target: Number(link.target),
  //   value: Number(link.value),
  // }));

  // 🔹 차트 컨테이너의 크기를 감지하여 업데이트
  useEffect(() => {
    const updateSize = () => {
      if (chartRef.current) {
        setChartWidth(chartRef.current.offsetWidth);
      }
    };

    updateSize(); // 초기 사이즈 설정
    window.addEventListener("resize", updateSize); // 윈도우 크기 변경 감지

    return () => window.removeEventListener("resize", updateSize);
  }, []);

  return (
    <div ref={chartRef} className="mb-6">
      <h2 className="text-xl font-semibold mt-8">생산-유통 흐름</h2>
      <ResponsiveContainer width="100%" height={500}>
        <Sankey
          data={priceData?.sankeyData || { nodes: [], links: [] }}
          node={<CustomNode />}
          link={{ stroke: "#82ca9d", strokeOpacity: 0.5 }}
          margin={{ top: 40, right: 45, bottom: 20, left: 45 }}
        >
          <Tooltip />
          {levelLabels.map((label, index) => (
            <text
              key={index}
              x={(chartWidth / levelLabels.length) * index + chartWidth / (levelLabels.length * 2)}
              y={30} // 최상단에 위치
              textAnchor="middle"
              fontSize={16}
              fontWeight="bold"
              fill="#333"
            >
              {label}
            </text>
          ))}
        </Sankey>
      </ResponsiveContainer>
    </div>
  );
};

const CustomNode = (props: any) => {
  const { x, y, width, height, payload } = props;
  return (
    <g transform={`translate(${x},${y})`}>
      {/* 노드 박스 */}
      <rect width={width} height={height} fill="#8884d8" stroke="#fff" />

      {/* 노드 이름 */}
      <text
        x={width / 2}
        y={height / 2}
        textAnchor="middle"
        dominantBaseline="middle"
        fill="#000"
        fontSize={14}
        fontWeight="bold"
      >
        {payload.name}
      </text>
    </g>
  );
};

export default SankeyChart;