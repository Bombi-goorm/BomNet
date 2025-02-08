import { useState } from "react";
import { LineChart, Line, CartesianGrid, XAxis, YAxis, Tooltip } from "recharts";
import strawberryImage from "/src/assets/strawberry.jpg";
import appleImage from "/src/assets/apple.jpg";
import potatoImage from "/src/assets/potato.jpg";
import goguImage from "/src/assets/gogu.jpg";
import pearImage from "/src/assets/pear.jpg";

// 품목 데이터
const items = [
  {
    name: "딸기",
    price: "28,537원",
    image: strawberryImage,
    data: [
      { date: "01/31", value: 50 },
      { date: "02/01", value: 70 },
      { date: "02/02", value: 65 },
      { date: "02/03", value: 40 },
      { date: "02/04", value: 30 },
    ],
  },
  {
    name: "사과",
    price: "22,000원",
    image: appleImage,
    data: [
      { date: "01/31", value: 80 },
      { date: "02/01", value: 75 },
      { date: "02/02", value: 78 },
      { date: "02/03", value: 85 },
      { date: "02/04", value: 90 },
    ],
  },
  {
    name: "배",
    price: "18,000원",
    image: pearImage,
    data: [
      { date: "01/31", value: 30 },
      { date: "02/01", value: 35 },
      { date: "02/02", value: 40 },
      { date: "02/03", value: 38 },
      { date: "02/04", value: 32 },
    ],
  },
  {
    name: "감자",
    price: "12,000원",
    image: potatoImage,
    data: [
      { date: "01/31", value: 55 },
      { date: "02/01", value: 60 },
      { date: "02/02", value: 58 },
      { date: "02/03", value: 62 },
      { date: "02/04", value: 50 },
    ],
  },
  {
    name: "고구마",
    price: "15,000원",
    image: goguImage,
    data: [
      { date: "01/31", value: 45 },
      { date: "02/01", value: 50 },
      { date: "02/02", value: 48 },
      { date: "02/03", value: 55 },
      { date: "02/04", value: 60 },
    ],
  },
];

const BestItemSlider = () => {
  const [currentIndex, setCurrentIndex] = useState(0);

  const nextItem = () => {
    setCurrentIndex((prevIndex) => (prevIndex + 1) % items.length);
  };

  const currentItem = items[currentIndex];

  return (
    <div className="w-full max-w-4xl mx-auto bg-white shadow-lg rounded-lg p-6 space-y-6">
      <h2 className="text-2xl font-semibold text-center text-gray-800">
        Best 5 농산물
      </h2>
      <div className="flex flex-col md:flex-row md:items-center md:gap-8">
        {/* 이미지와 텍스트 */}
        <div className="flex flex-col items-center md:items-start space-y-4">
          <img
            src={currentItem.image}
            alt={currentItem.name}
            className="w-32 h-32 object-cover rounded-full shadow-md"
          />
          <div className="text-center md:text-left">
            <p className="text-2xl font-bold text-gray-800">{currentItem.name}</p>
            <p className="text-lg text-green-500">{currentItem.price}</p>
          </div>
        </div>

        {/* 그래프 */}
        <div className="flex-1">
          <LineChart
            width={400}
            height={200}
            data={currentItem.data} // 품목별 데이터 적용
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

      {/* 버튼 */}
      <div className="text-center">
        <button
          onClick={nextItem}
          className="bg-green-500 hover:bg-green-600 text-white font-medium px-6 py-3 rounded-full transition duration-200"
        >
          다음 품목 보기
        </button>
      </div>
    </div>
  );
};

export default BestItemSlider;
