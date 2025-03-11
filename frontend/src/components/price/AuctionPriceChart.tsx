import React from "react";
import { LineChart, Line, CartesianGrid, XAxis, YAxis, Tooltip, ResponsiveContainer, Legend } from "recharts";
import { PriceResponse } from "../../types/price_types";

interface AuctionPriceChartProps {
  priceData: PriceResponse | null;
}

const AuctionPriceChart: React.FC<AuctionPriceChartProps> = ({ priceData }) => {
  const rawLiveChartData = priceData?.realTime || [];

  
  const transformedData = rawLiveChartData.reduce((acc, entry) => {
    const existingEntry = acc.find((item) => item.datetime === entry.dateTime);

    if (existingEntry) {
      existingEntry[entry.variety] = entry.price;
    } else {
      acc.push({ datetime: entry.dateTime, [entry.variety]: entry.price });
    }

    return acc;
  }, [] as Record<string, any>[]);

  
  const uniqueVarieties = [...new Set(rawLiveChartData.map((item) => item.variety))];

  return (
    <div className="mb-6">
      
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-semibold">⏳ 실시간 경락가</h2>
      </div>

      <ResponsiveContainer width="100%" height={400}>
        <LineChart data={transformedData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis
            dataKey="datetime"
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