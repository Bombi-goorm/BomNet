import { useState, useEffect } from "react";
import { LineChart, Line, CartesianGrid, XAxis, YAxis, Tooltip, ResponsiveContainer } from "recharts";
import { useQueryClient } from "@tanstack/react-query";
import { BestItems, HomeProduct } from "../../types/home_types";

const BestItemSlider = () => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [isMobile, setIsMobile] = useState(window.innerWidth < 768); // ✅ 초기 화면 크기 확인
  const queryClient = useQueryClient();

  const bestItems = queryClient.getQueryData<BestItems>(["products"]);
  const products: HomeProduct[] = bestItems ? bestItems.products : [];

  if (!products || products.length === 0) {
    return <div className="text-center text-gray-500">📢 등록된 인기 상품이 없습니다.</div>;
  }

  const nextItem = () => {
    setCurrentIndex((prevIndex) => (prevIndex + 1) % products.length);
  };

  const currentProduct = products[currentIndex];

  // 📌 **화면 크기 변경 감지하여 모바일 여부 업데이트**
  useEffect(() => {
    const handleResize = () => setIsMobile(window.innerWidth < 768);
    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, []);

  // 📌 **X축 날짜 생성 (누락된 날짜 포함)**
  const generateXAxisData = () => {
    const start = new Date(currentProduct.dayPrice[0].date);
    const end = new Date(currentProduct.dayPrice[currentProduct.dayPrice.length - 1].date);
    const dates = [];

    while (start <= end) {
      const formattedDate = start.toISOString().split("T")[0].slice(5); // ✅ "MM-dd" 형식
      dates.push(formattedDate);
      start.setDate(start.getDate() + 1);
    }

    return dates;
  };

  const xAxisDates = generateXAxisData();

  // 📌 **X축 기준 차트 데이터 생성**
  const chartData = xAxisDates.map((date) => {
    const priceEntry = currentProduct.dayPrice.find((p) => p.date.slice(5) === date);
    return {
      date,
      value: priceEntry ? priceEntry.price : null, // ✅ 가격이 없으면 null
    };
  });

  // 📌 **화면 크기에 따라 X축 날짜 포맷 변경**
  const formatXAxisTick = (tick: string) => {
    return isMobile ? tick.split("-")[1] : tick; // ✅ 작은 화면: "dd", 큰 화면: "MM-dd"
  };

  return (
    <div className="w-full max-w-4xl mx-auto bg-gradient-to-r from-green-50 to-green-50 p-6 rounded-lg shadow-lg space-y-6">
      <h2 className="text-2xl font-bold text-center text-gray-700">🔥 Best 5 농산물</h2>

      <div className="flex flex-col lg:flex-row lg:items-center lg:space-x-10 space-y-8 lg:space-y-0">
        {/* 이미지 및 텍스트 */}
        <div className="flex flex-col items-center space-y-6 text-center lg:text-left lg:w-1/3">
          <img
            src={currentProduct.imgUrl}
            alt={currentProduct.productName}
            className="w-40 h-40 object-cover rounded-full shadow-md"
          />
          <div className="flex flex-col items-center">
            <p className="text-3xl font-semibold text-gray-800 text-center w-full break-words">
              {currentProduct.productName}
            </p>
            <p className="text-lg text-green-600 mt-2 text-center w-full">
              {currentProduct.dayPrice[currentProduct.dayPrice.length - 1].price}원
            </p>
          </div>
        </div>

        {/* 가격 추이 차트 */}
        <div className="flex-1">
          <ResponsiveContainer width="100%" height={200}>
            <LineChart data={chartData} className="mx-auto md:mx-0">
              <Line type="monotone" dataKey="value" stroke="#34d399" strokeWidth={2} />
              <CartesianGrid stroke="#e5e7eb" strokeDasharray="5 5" />
              <XAxis
                dataKey="date"
                tickFormatter={formatXAxisTick}
                interval="preserveStartEnd"
                minTickGap={15} // ✅ X축 간격 조정
              />
              <YAxis />
              <Tooltip />
            </LineChart>
          </ResponsiveContainer>
        </div>
      </div>

      {/* 슬라이더 버튼 */}
      <div className="flex justify-center items-center space-x-4 mt-6">
        <button
          onClick={nextItem}
          className="bg-green-500 hover:bg-green-600 text-white font-medium px-6 py-3 rounded-full shadow transition duration-200"
        >
          다음 품목
        </button>
      </div>
    </div>
  );
};

export default BestItemSlider;
