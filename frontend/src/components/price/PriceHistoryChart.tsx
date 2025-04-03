import React, { useState } from "react";
import { LineChart, Line, CartesianGrid, XAxis, YAxis, Tooltip, ResponsiveContainer, Legend } from "recharts";
import { PriceResponse } from "../../types/price_types";

interface PriceHistoryChartProps {
  priceData: PriceResponse | null;
}

const PriceHistoryChart: React.FC<PriceHistoryChartProps> = ({ priceData }) => {
  const [timeRange, setTimeRange] = useState<"year" | "month" | "day">("day");

  const getPriceData = () => {
    if (!priceData) return [];
    switch (timeRange) {
      case "year":
        return priceData.annual;
      case "month":
        return priceData.monthly;
      default:
        return priceData.daily;
    }
  };

  const rawPriceData = getPriceData();

  const transformedData = rawPriceData.reduce((acc, entry) => {
    const existingEntry = acc.find((item) => item.dateTime === entry.dateTime);

    if (existingEntry) {
      existingEntry[entry.variety] = entry.price; // Store each variety under its name
    } else {
      acc.push({ dateTime: entry.dateTime, [entry.variety]: entry.price });
    }

    return acc;
  }, [] as Record<string, any>[]);

  transformedData.sort(
    (a, b) => new Date(a.dateTime).getTime() - new Date(b.dateTime).getTime()
  );
  
  const uniqueVarieties = [...new Set(rawPriceData.map((item) => item.variety))];

  return (
    <div className="mb-6">
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-semibold">📈 기간별 가격 추이</h2>
        <div className="flex items-center">
          <label htmlFor="time-range" className="text-sm text-gray-700 mr-2">시간 범위:</label>
          <select
            id="time-range"
            className="border p-2 rounded-md"
            value={timeRange}
            onChange={(e) => setTimeRange(e.target.value as "year" | "month" | "day")}
          >
            <option value="year">10년</option>
            <option value="month">12개월</option>
            <option value="day">30일</option>
          </select>
        </div>
      </div>

      <ResponsiveContainer width="100%" height={400}>
        <LineChart data={transformedData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="dateTime" tick={{ fontSize: 12 }} angle={-45} />
          <YAxis />
          <Tooltip />
          <Legend />
          {uniqueVarieties.map((variety, index) => (
            <Line
              key={variety}
              type="monotone"
              dataKey={variety}
              name={variety}
              stroke={`hsl(${index * 72}, 70%, 50%)`}
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

export default PriceHistoryChart;