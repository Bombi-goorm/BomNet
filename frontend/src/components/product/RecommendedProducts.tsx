import { recommendedProducts } from "../../data_sample";

const RecommendedProducts = () => {
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