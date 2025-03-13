import React from "react";
import { RecommendedProduct } from "../../types/member_types";


interface RecommendedProductsProps {
  recommendedProducts?: RecommendedProduct[]; // ✅ undefined 가능성 처리
}

const RecommendedProducts: React.FC<RecommendedProductsProps> = ({ recommendedProducts = [] }) => { // ✅ 기본값 빈 배열 설정
  return (
    <div className="bg-white p-6 rounded-lg shadow text-center">
      <h2 className="text-xl font-semibold mb-4">추천 생산품</h2>
      {recommendedProducts.length === 0 ? (
        <p className="text-gray-500">추천 생산품이 없습니다.</p>
      ) : (
        <ul>
          {recommendedProducts.map((product) => (
            <li key={product.id} className="py-2 border-b">
              <strong>{product.productName}</strong> - {product.reason}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default RecommendedProducts;