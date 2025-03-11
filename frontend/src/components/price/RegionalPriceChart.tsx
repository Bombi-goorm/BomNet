import React, { useState } from "react";
import { BarChart, Bar, CartesianGrid, XAxis, YAxis, Tooltip, ResponsiveContainer, Legend } from "recharts";
import { PriceResponse } from "../../types/price_types";

interface RegionalPriceChartProps {
  priceData: PriceResponse | null;
}

const RegionalPriceChart: React.FC<RegionalPriceChartProps> = ({ priceData }) => {
  const rawRegionalData = priceData?.regionalChartData || [];

  
  const uniqueVarieties = [...new Set(rawRegionalData.map((item) => item.variety))];


  const [selectedVariety, setSelectedVariety] = useState<string>(uniqueVarieties[0] || "");

  
  const filteredData = rawRegionalData.filter((entry) => entry.variety === selectedVariety);

  return (
    <div className="mb-6">
      
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-semibold">🏢 지역별 품종 가격</h2>
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
        <BarChart data={filteredData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="region" />
          <YAxis />
          <Tooltip />
          <Legend />
          <Bar dataKey="price" name="현재 가격" fill="#8884d8" />
        </BarChart>
      </ResponsiveContainer>
    </div>
  );
};

export default RegionalPriceChart;