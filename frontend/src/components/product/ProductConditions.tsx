import React from "react";
import { ProductResponseDto } from "../../types/product_types";

interface ProductConditionsProps {
  productData: ProductResponseDto;
}

const ProductConditions: React.FC<ProductConditionsProps> = ({ productData }) => {
  const { product, cultivationInfo } = productData;
  const { conditions, cultivationFeatures, cultivationTips } = cultivationInfo;

  return (
    <div className="bg-white p-6 rounded-lg shadow flex flex-col md:flex-row">
      {/* 이미지 섹션 */}
      <div className="w-full md:w-1/3 mb-6 md:mb-0">
        <img
          src={product.imageUrl}
          alt={`${cultivationInfo.cropName} ${cultivationInfo.variety ? `(${cultivationInfo.variety})` : ""}`}
          className="rounded-lg w-full h-auto"
        />
      </div>
      {/* 상세 정보 */}
      <div className="w-full md:w-2/3 pl-0 md:pl-6">
        <h2 className="text-xl font-semibold mb-4">
          {cultivationInfo.cropName} {cultivationInfo.variety ? `(${cultivationInfo.variety})` : ""}
        </h2>
        <p className="mb-6">{cultivationFeatures}</p>
        {/* 생산 조건 */}
        <div className="bg-gray-50 p-4 rounded-lg">
          <h3 className="text-lg font-semibold mb-4">📌 생산 조건</h3>
          <ul className="list-disc list-inside space-y-2">
            <li>🌡️ 기온: {conditions.avgTemperatureC}°C (최저 {conditions.minTemperatureC}°C, 최고 {conditions.maxTemperatureC}°C)</li>
            <li>☀️ 일조량: {conditions.sunlightHours}시간</li>
            <li>🌱 토양 pH: {conditions.ph}</li>
            <li>💧 배수: {conditions.drainage}</li>
            <li>📏 토심: {conditions.soilDepth}cm</li>
          </ul>
        </div>
        {/* 관리 팁 */}
        <div className="bg-gray-100 p-4 rounded-lg mt-4">
          <h3 className="text-lg font-semibold mb-4">🌾 관리 팁</h3>
          <p>{cultivationTips}</p>
        </div>
      </div>
    </div>
  );
};

export default ProductConditions;