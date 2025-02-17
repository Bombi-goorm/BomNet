import { useState } from "react";
import { LineChart, Line, CartesianGrid, XAxis, YAxis, Tooltip } from "recharts";
import { useQueryClient } from "@tanstack/react-query";
import { BestItems, Product, ProductPrice } from "../../types/member_types";

const BestItemSlider = () => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const queryClient = useQueryClient();

  const bestItems = queryClient.getQueryData<BestItems>(["products"]);
  const products: Product[] = bestItems ? bestItems.products : [];

  if (!products || products.length === 0) {
    return <div className="text-center text-gray-500">등록된 상품이 없습니다.</div>;
  }

  const nextItem = () => {
    setCurrentIndex((prevIndex) => (prevIndex + 1) % products.length);
  };

  const currentProduct = products[currentIndex];

  const chartData = currentProduct.dayPrice.map((priceInfo: ProductPrice) => ({
    date: priceInfo.date,
    value: Number(priceInfo.price.replace(/[^0-9.]/g, "")),
  }));

  return (
    <div className="w-full max-w-4xl mx-auto bg-gradient-to-r from-green-50 to-green-50 p-6 rounded-lg shadow-lg space-y-6">
      <h2 className="text-2xl font-bold text-center text-gray-700">Best 5 농산물</h2>
      <div className="flex flex-col lg:flex-row lg:items-center lg:space-x-10 space-y-8 lg:space-y-0">
        {/* 이미지와 텍스트 영역 */}
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
            {currentProduct.dayPrice[currentProduct.dayPrice.length - 1].price}
          </p>
        </div>

        </div>

        {/* 차트 영역 */}
        
        <div className="flex-1">
          <LineChart
            width={400}
            height={200}
            data={chartData}
            className="mx-auto md:mx-0"
          >
            <Line type="monotone" dataKey="value" stroke="#34d399" strokeWidth={2} />
            <CartesianGrid stroke="#e5e7eb" strokeDasharray="5 5" />
            <XAxis dataKey="date" />
            <YAxis />
            <Tooltip />
          </LineChart>
        </div>
      </div>

      {/* 슬라이더 컨트롤 */}
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
