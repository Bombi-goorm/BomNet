import React, { useState } from "react";
import { LineChart, Line, CartesianGrid, XAxis, YAxis, Tooltip, ResponsiveContainer, Legend } from "recharts";
import { PriceResponse } from "../../types/price_types";

interface PriceHistoryChartProps {
  priceData: PriceResponse | null;
}

const PriceHistoryChart: React.FC<PriceHistoryChartProps> = ({ priceData }) => {
  const [timeRange, setTimeRange] = useState<"year" | "month" | "day">("day");

  // Select data based on the chosen time range
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

  // 🛠 **Transform data to group by `dateTime`**
  const transformedData = rawPriceData.reduce((acc, entry) => {
    const existingEntry = acc.find((item) => item.dateTime === entry.dateTime);

    if (existingEntry) {
      existingEntry[entry.variety] = entry.price; // Store each variety under its name
    } else {
      acc.push({ dateTime: entry.dateTime, [entry.variety]: entry.price });
    }

    return acc;
  }, [] as Record<string, any>[]);

  // Extract unique varieties dynamically
  const uniqueVarieties = [...new Set(rawPriceData.map((item) => item.variety))];

  return (
    <div className="mb-6">
      <div className="flex justify-end items-center mb-4">
        <label htmlFor="time-range" className="text-sm text-gray-700">시간 범위:</label>
        <select
          id="time-range"
          className="border p-2 rounded-md ml-2"
          value={timeRange}
          onChange={(e) => setTimeRange(e.target.value as "year" | "month" | "day")}
        >
          <option value="year">10년</option>
          <option value="month">12개월</option>
          <option value="day">30일</option>
        </select>
      </div>
      <ResponsiveContainer width="100%" height={400}>
        <LineChart data={transformedData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="dateTime" tick={{ fontSize: 12 }} />
          <YAxis />
          <Tooltip />
          <Legend />
          {uniqueVarieties.map((variety, index) => (
            <Line
              key={variety}
              type="monotone"
              dataKey={variety} // Dynamically use variety name as key
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