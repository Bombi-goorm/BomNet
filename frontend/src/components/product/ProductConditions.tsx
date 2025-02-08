import { cultivationInfo } from "../../data_sample";

const ProductConditions = () => {
  const { title, description, imageUrl, conditions } = cultivationInfo;

  return (
    <div className="bg-white p-6 rounded-lg shadow flex flex-col md:flex-row">
      {/* 이미지 섹션 */}
      <div className="w-full md:w-1/3 mb-6 md:mb-0">
        <img
          src={imageUrl}
          alt={title}
          className="rounded-lg w-full h-auto"
        />
      </div>

      {/* 상세 정보 및 생산 조건 */}
      <div className="w-full md:w-2/3 pl-0 md:pl-6">
        {/* 상품 상세 정보 */}
        <h2 className="text-xl font-semibold mb-4">{title}</h2>
        <p className="mb-6">{description}</p>

        {/* 생산 조건 */}
        <div className="bg-gray-50 p-4 rounded-lg">
          <h3 className="text-lg font-semibold mb-4">생산 조건</h3>
          <ul className="list-disc list-inside space-y-2">
            <li>기온: {conditions.temperature}</li>
            <li>토양: {conditions.soil}</li>
            <li>강우량: {conditions.rainfall}</li>
            <li>일조량: {conditions.sunlight}</li>
          </ul>
        </div>
      </div>
    </div>
  );
};

export default ProductConditions;
