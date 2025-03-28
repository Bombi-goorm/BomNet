import React, { useState, useMemo } from "react";
import {
  LineChart,
  Line,
  CartesianGrid,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
  Legend,
} from "recharts";
import { PriceResponse } from "../../types/price_types";

interface AuctionPriceChartProps {
  priceData: PriceResponse | null;
}

const AuctionPriceChart: React.FC<AuctionPriceChartProps> = ({ priceData }) => {
  const [selectedMarket, setSelectedMarket] = useState<string>("전체");

  const rawLiveChartData = priceData?.realTime || [];

  // 고유한 시장 목록 추출
  const marketList = useMemo(() => {
    const markets = Array.from(new Set(rawLiveChartData.map((item) => item.market)));
    return ["전체", ...markets];
  }, [rawLiveChartData]);

  // 시장 필터 적용
  const filteredData = useMemo(() => {
    return selectedMarket === "전체"
      ? rawLiveChartData
      : rawLiveChartData.filter((item) => item.market === selectedMarket);
  }, [rawLiveChartData, selectedMarket]);

  // 차트에 맞게 데이터 변환
  const transformedData = useMemo(() => {
    const grouped = filteredData.reduce((acc, entry) => {
      const existing = acc.find((item) => item.datetime === entry.dateTime);
      if (existing) {
        existing[entry.variety] = entry.price;
      } else {
        acc.push({ datetime: entry.dateTime, [entry.variety]: entry.price });
      }
      return acc;
    }, [] as Record<string, any>[]);

    return grouped.sort((a, b) => new Date(a.datetime).getTime() - new Date(b.datetime).getTime());
  }, [filteredData]);

  const uniqueVarieties = [...new Set(filteredData.map((item) => item.variety))];

  return (
    <div className="mb-6">
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-semibold">⏳ 실시간 경락가</h2>
        <select
          value={selectedMarket}
          onChange={(e) => setSelectedMarket(e.target.value)}
          className="border border-gray-300 rounded px-2 py-1 text-black"
        >
          {marketList.map((market) => (
            <option key={market} value={market}>
              {market}
            </option>
          ))}
        </select>
      </div>

      <ResponsiveContainer width="100%" height={400}>
        <LineChart data={transformedData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis
            dataKey="datetime"
            angle={-45}
            tickFormatter={(tick) => new Date(tick).getHours() + ":00"}
          />
          <YAxis />
          <Tooltip />
          <Legend />
          {uniqueVarieties.map((variety, index) => (
            <Line
              key={variety}
              type="monotone"
              dataKey={variety}
              name={variety}
              stroke={`hsl(${index * 72 + 180}, 70%, 50%)`}
              strokeWidth={2}
              dot={{ r: 3 }}
              connectNulls
            />
          ))}
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
};

export default AuctionPriceChart;