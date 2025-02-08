import React from "react";
import {
  LineChart,
  Line,
  CartesianGrid,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
} from "recharts";
import { prices, liveAuctionPrices, regions, varieties } from "../../data_sample";

interface SelectedData {
  itemId: number;
  varietyId: number;
  regionId: number;
}

interface MultiLineChartProps {
  selections: SelectedData[];
  startDate: string;
  endDate: string;
}

const MultiLineChart: React.FC<MultiLineChartProps> = ({
  selections,
  startDate,
  endDate,
}) => {
  const xAxisDates = (() => {
    const start = new Date(startDate);
    const end = new Date(endDate);
    const dates = [];
    while (start <= end) {
      dates.push(start.toISOString().split("T")[0]);
      start.setDate(start.getDate() + 1);
    }
    return dates;
  })();

  const filteredPriceData = selections.flatMap((sel) =>
    prices
      .filter(
        (price) =>
          price.varietyId === sel.varietyId &&
          price.regionId === sel.regionId &&
          new Date(price.date) >= new Date(startDate) &&
          new Date(price.date) <= new Date(endDate)
      )
      .map((price) => ({
        date: price.date,
        price: price.price,
        varietyId: price.varietyId,
        regionId: price.regionId,
      }))
  );

  const chartData = xAxisDates.map((date) => ({
    date,
    ...selections.reduce((acc, sel) => {
      const priceEntry = filteredPriceData.find(
        (p) => p.date === date && p.varietyId === sel.varietyId
      );
      return {
        ...acc,
        [`price_${sel.varietyId}_${sel.regionId}`]: priceEntry?.price || null,
      };
    }, {}),
  }));

  // 현재 시간부터 12시간 전까지의 시간축 생성
  const generateTimeAxis = () => {
    const timePoints = [];
    const now = new Date();
    now.setMinutes(0, 0, 0); // 현재 시간의 분, 초를 0으로 설정
    
    for (let i = 12; i >= 0; i--) {
      const timePoint = new Date(now);
      timePoint.setHours(now.getHours() - i);
      timePoints.push(timePoint);
    }
    return timePoints;
  };

  // 시간축 데이터 생성
  const timeAxisPoints = generateTimeAxis();

  // 경락가 데이터 필터링
  const getLast12HoursData = () => {
    const now = new Date();
    const twelveHoursAgo = new Date(now);
    twelveHoursAgo.setHours(now.getHours() - 12);

    return liveAuctionPrices.filter(
      (price) =>
        new Date(price.datetime) >= twelveHoursAgo &&
        new Date(price.datetime) <= now
    );
  };

  // 시간축에 맞춰 데이터 재구성
  const reconstructLiveData = () => {
    const filteredData = getLast12HoursData();
    
    return timeAxisPoints.map(timePoint => {
      const hourStart = new Date(timePoint);
      const hourEnd = new Date(timePoint);
      hourEnd.setHours(hourEnd.getHours() + 1);

      const dataForHour = selections.reduce((acc, sel) => {
        const relevantData = filteredData.filter(
          price =>
            price.varietyId === sel.varietyId &&
            price.regionId === sel.regionId &&
            new Date(price.datetime) >= hourStart &&
            new Date(price.datetime) < hourEnd
        );

        // 해당 시간대의 평균 가격 계산
        if (relevantData.length > 0) {
          const avgPrice = relevantData.reduce((sum, item) => sum + item.price, 0) / relevantData.length;
          acc[`price_${sel.varietyId}_${sel.regionId}`] = avgPrice;
        } else {
          acc[`price_${sel.varietyId}_${sel.regionId}`] = null;
        }

        return acc;
      }, {});

      return {
        datetime: timePoint.toISOString(),
        ...dataForHour
      };
    });
  };

  const liveChartData = reconstructLiveData();

  const getVarietyName = (varietyId: number) =>
    varieties.find((v) => v.id === varietyId)?.name || "Unknown Variety";

  const getRegionName = (regionId: number) =>
    regions.find((r) => r.id === regionId)?.name || "Unknown Region";

  return (
    <div className="bg-white p-6 rounded-lg shadow">
      {/* 가격 추이 차트 */}
      <h2 className="text-xl font-semibold mb-4 text-center">가격 추이</h2>
      {chartData.length > 0 ? (
        <div className="w-full h-[400px]">
          <ResponsiveContainer>
            <LineChart data={chartData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="date" />
              <YAxis />
              <Tooltip />
              {selections.map((sel, index) => (
                <Line
                  key={index}
                  type="monotone"
                  dataKey={`price_${sel.varietyId}_${sel.regionId}`}
                  name={`${getVarietyName(sel.varietyId)}, ${getRegionName(sel.regionId)}`}
                  stroke={`hsl(${index * 72}, 70%, 50%)`}
                  strokeWidth={2}
                  connectNulls
                />
              ))}
            </LineChart>
          </ResponsiveContainer>
        </div>
      ) : (
        <p className="text-gray-500 text-center">선택한 기간 내 가격 데이터가 없습니다.</p>
      )}

      {/* 실시간 경락가 차트 */}
      <h2 className="text-xl font-semibold mb-4 text-center mt-8">실시간 경락가</h2>
      <div className="w-full h-[400px]">
        <ResponsiveContainer>
          <LineChart data={liveChartData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis
              dataKey="datetime"
              tickFormatter={(tick) => {
                const date = new Date(tick);
                return `${date.getHours()}:00`;
              }}
            />
            <YAxis />
            <Tooltip
              labelFormatter={(label) => {
                const date = new Date(label);
                return `${date.getHours()}:00`;
              }}
            />
            {selections.map((sel, index) => (
              <Line
                key={index}
                type="monotone"
                dataKey={`price_${sel.varietyId}_${sel.regionId}`}
                name={`${getVarietyName(sel.varietyId)}, ${getRegionName(sel.regionId)}`}
                stroke={`hsl(${index * 72 + 180}, 70%, 50%)`}
                strokeWidth={2}
                connectNulls
              />
            ))}
          </LineChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
};

export default MultiLineChart;