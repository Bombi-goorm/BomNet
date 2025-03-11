import React, { useState } from "react";
import { LineChart, Line, CartesianGrid, XAxis, YAxis, Tooltip, ResponsiveContainer, Legend } from "recharts";
import { PriceResponse } from "../../types/price_types";

interface QualityChartProps {
  priceData: PriceResponse | null;
}

const QualityChart: React.FC<QualityChartProps> = ({ priceData }) => {
  const rawQualityData = priceData?.qualityChartData || [];

  const uniqueVarieties = [...new Set(rawQualityData.map((item) => item.variety))];

  const [selectedVariety, setSelectedVariety] = useState<string>(uniqueVarieties[0] || "");

  const transformedData = rawQualityData
    .filter((entry) => entry.variety === selectedVariety)
    .reduce((acc, entry) => {
      const existingEntry = acc.find((item) => item.date === entry.date);

      if (existingEntry) {
        existingEntry["특"] = entry.특;
        existingEntry["상"] = entry.상;
        existingEntry["보통"] = entry.보통;
        existingEntry["등외"] = entry.등외;
      } else {
        acc.push({
          date: entry.date,
          "특": entry.특,
          "상": entry.상,
          "보통": entry.보통,
          "등외": entry.등외,
        });
      }

      return acc;
    }, [] as Record<string, any>[]);

  return (
    <div className="mb-6">
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-semibold">📈 품질별 가격 추이</h2>
        <div className="flex items-center">
          <label htmlFor="variety-select" className="text-sm text-gray-700 mr-2">품종 선택:</label>
          <select
            id="variety-select"
            className="border p-2 rounded-md"
            value={selectedVariety}
            onChange={(e) => setSelectedVariety(e.target.value)}
          >
            {uniqueVarieties.map((variety) => (
              <option key={variety} value={variety}>
                {variety}
              </option>
            ))}
          </select>
        </div>
      </div>

      <ResponsiveContainer width="100%" height={400}>
        <LineChart data={transformedData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="date" />
          <YAxis />
          <Tooltip />
          <Legend />

          <Line type="monotone" dataKey="특" name="특" stroke="#FF0000" strokeWidth={2} />
          <Line type="monotone" dataKey="상" name="상" stroke="#00FF00" strokeWidth={2} />
          <Line type="monotone" dataKey="보통" name="보통" stroke="#0000FF" strokeWidth={2} />
          <Line type="monotone" dataKey="등외" name="등외" stroke="#FFA500" strokeWidth={2} />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
};

export default QualityChart;