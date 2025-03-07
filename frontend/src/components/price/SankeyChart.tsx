import React from "react";
import { Sankey, Tooltip, ResponsiveContainer } from "recharts";
import { PriceResponse } from "../../types/price_types";

interface SankeyChartProps {
  priceData: PriceResponse | null;
}

const SankeyChart: React.FC<SankeyChartProps> = ({ priceData }) => {
  return (
    <div className="mb-6">
      <h2 className="text-xl font-semibold mt-8">생산지 - 품종 - 도매시장 흐름</h2>
      <ResponsiveContainer width="100%" height={500}>
        <Sankey
          data={priceData?.sankeyData || { nodes: [], links: [] }}
          node={<CustomNode />}
          link={{ stroke: "#82ca9d", strokeOpacity: 0.5 }}
          margin={{ top: 20, right: 20, bottom: 20, left: 20 }}
        >
          <Tooltip />
        </Sankey>
      </ResponsiveContainer>
    </div>
  );
};

const CustomNode = (props: any) => {
  const { x, y, width, height, payload } = props;
  return (
    <g transform={`translate(${x},${y})`}>
      <rect width={width} height={height} fill="#8884d8" stroke="#fff" />
      <text
        x={width / 2}
        y={height / 2}
        textAnchor="middle"
        dominantBaseline="middle"
        fill="#000"
        fontSize={12}
        fontWeight="bold"
      >
        {payload.name}
      </text>
    </g>
  );
};

export default SankeyChart;