import React from "react";


interface RecommendedProductsProps {
  recommendedProducts: {
    id: number;
    name: string;
    reason: string;
  }[];
}

const RecommendedProducts: React.FC<RecommendedProductsProps> = ({ recommendedProducts }) => {
  return (
    <div className="bg-white p-6 rounded-lg shadow text-center">
      <h2 className="text-xl font-semibold mb-4">추천 생산품</h2>
      <ul>
        {recommendedProducts.map((product) => (
          <li key={product.id} className="py-2 border-b">
            <strong>{product.name}</strong> - {product.reason}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default RecommendedProducts;