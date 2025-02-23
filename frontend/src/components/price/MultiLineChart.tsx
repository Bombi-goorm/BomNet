import { useState } from "react";
import {
  LineChart,
  Line,
  CartesianGrid,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
} from "recharts";
import { dayPrices, monthPrices, yearPrices, liveAuctionPrices, REGIONS, ITEM_VARIETY_MAP } from "../../data_sample";

interface SelectedData {
  smallId: string;
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
  const [timeRange, setTimeRange] = useState<"year" | "month" | "day">("month");

  //  선택된 범위에 맞는 가격 데이터 가져오기
  const getPriceData = () => {
    switch (timeRange) {
      case "year":
        return yearPrices;
      case "month":
        return monthPrices;
      default:
        return dayPrices;
    }
  };

  //  가격 데이터 필터링
  const filteredPriceData = getPriceData().filter((price) =>
    selections.some(
      (sel) => sel.smallId === price.smallId && sel.regionId === price.regionId
    )
  );

  //  X축 데이터 생성 (년, 월, 일 선택에 따라)
  const generateXAxisData = () => {
    const now = new Date();
    const xAxisData = [];

    if (timeRange === "year") {
      for (let i = 10; i >= 0; i--) {
        xAxisData.push((now.getFullYear() - i).toString());
      }
    } else if (timeRange === "month") {
      for (let i = 11; i >= 0; i--) {
        const date = new Date(now.getFullYear(), now.getMonth() - i, 1);
        xAxisData.push(date.toISOString().split("T")[0].slice(0, 7)); // "YYYY-MM"
      }
    } else {
      for (let i = 19; i >= 0; i--) {
        const date = new Date(now);
        date.setDate(now.getDate() - i);
        xAxisData.push(date.toISOString().split("T")[0]); // "YYYY-MM-DD"
      }
    }
    return xAxisData;
  };

  const xAxisDates = generateXAxisData();

   // 차트 데이터 변환
   const chartData = xAxisDates.map((date) => ({
    date,
    ...selections.reduce((acc, sel) => {
      const priceEntry = filteredPriceData.find(
        (p) => p.date === date && p.smallId === sel.smallId
      );
      return {
        ...acc,
        [`price_${sel.smallId}_${sel.regionId}`]: priceEntry?.price || null,
      };
    }, {}),
  }));

  // 실시간 경락가 데이터 변환
  const generateTimeAxis = () => {
    const timePoints = [];
    const now = new Date();
    now.setMinutes(0, 0, 0);

    for (let i = 12; i >= 0; i--) {
      const timePoint = new Date(now);
      timePoint.setHours(now.getHours() - i);
      timePoints.push(timePoint);
    }
    return timePoints;
  };

  // 이전 12시간 데이터 추출
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

  const reconstructLiveData = () => {
    const filteredData = getLast12HoursData();

    return timeAxisPoints.map((timePoint) => {
      const hourStart = new Date(timePoint);
      const hourEnd = new Date(timePoint);
      hourEnd.setHours(hourEnd.getHours() + 1);

      const dataForHour = selections.reduce((acc, sel) => {
        const relevantData = filteredData.filter(
          (price) =>
            price.smallId === sel.smallId &&
            price.regionId === sel.regionId &&
            new Date(price.datetime) >= hourStart &&
            new Date(price.datetime) < hourEnd
        );

        if (relevantData.length > 0) {
          const avgPrice =
            relevantData.reduce((sum, item) => sum + item.price, 0) /
            relevantData.length;
          acc[`price_${sel.smallId}_${sel.regionId}`] = avgPrice;
        } else {
          acc[`price_${sel.smallId}_${sel.regionId}`] = null;
        }

        return acc;
      }, {} as Record<string, number | null>);

      return {
        datetime: timePoint.toISOString(),
        ...dataForHour,
      };
    });
  };

  const timeAxisPoints = generateTimeAxis();
  const liveChartData = reconstructLiveData();
  //  품종 및 지역 이름 가져오기
  const getVarietyName = (smallId: string) =>
    ITEM_VARIETY_MAP.find((v) => v.smallId === smallId)?.smallName ||
    "Unknown Variety";

  const getRegionName = (regionId: number) =>
    REGIONS.find((r) => r.regionId === regionId)?.name || "Unknown Region";


  return (
    <div className="bg-white p-6 rounded-lg shadow">
      {/* 📈 가격 추이 차트 */}
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-semibold">📊 가격 추이</h2>
        <select
          className="border p-2 rounded-md cursor-pointer"
          value={timeRange}
          onChange={(e) => setTimeRange(e.target.value as "year" | "month" | "day")}
        >
          <option value="year">10년</option>
          <option value="month">12개월</option>
          <option value="day">20일</option>
        </select>
      </div>
      {chartData.length > 0 ? (
         <ResponsiveContainer width="100%" height={400}>
          <LineChart data={chartData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="date" />
            <YAxis />
            <Tooltip />
            {selections.map((sel, index) => (
              <Line
                key={index}
                type="monotone"
                dataKey={`price_${sel.smallId}_${sel.regionId}`}
                name={`${getVarietyName(sel.smallId)}, ${getRegionName(sel.regionId)}`}
                stroke={`hsl(${index * 72}, 70%, 50%)`}
                strokeWidth={2}
                connectNulls
              />
            ))}
          </LineChart>
        </ResponsiveContainer>
      ) : (
        <p className="text-gray-500 text-center">선택한 기간 내 가격 데이터가 없습니다.</p>
      )}

      {/* ⏳ 실시간 경락가 차트 */}
      <h2 className="text-xl font-semibold mt-8">⏳ 실시간 경락가</h2>
      <ResponsiveContainer width="100%" height={400}>
        <LineChart data={liveChartData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis
            dataKey="datetime"
            tickFormatter={(tick) => new Date(tick).getHours() + ":00"}
          />
          <YAxis />
          <Tooltip />
          {selections.map((sel, index) => (
            <Line
              key={index}
              type="monotone"
              dataKey={`price_${sel.smallId}_${sel.regionId}`}
              name={`${getVarietyName(sel.smallId)}, ${getRegionName(sel.regionId)}`}
              stroke={`hsl(${index * 72 + 180}, 70%, 50%)`}
              strokeWidth={2}
              connectNulls
            />
          ))}
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
};

export default MultiLineChart;
