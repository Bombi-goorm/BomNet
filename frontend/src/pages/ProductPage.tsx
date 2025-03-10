import Header from "../components/Header";
import PersonalizedAssessment from "../components/product/PersonalizedAssessment";
import ProductConditions from "../components/product/ProductConditions";
import ProductSearch from "../components/product/ProductSearch";

const ProductPage = () => {
  return (
    <div className="bg-gray-50 min-h-screen">
      <Header />
      <main className="max-w-6xl mx-auto p-4 space-y-8">

        {/* 상품 관련 정보 */}
        <div className="space-y-8">
          {/* 상품 검색 */}
          <ProductSearch onSearch={function (): void {
            throw new Error("Function not implemented.");
          } } />          
          {/* 생산 조건 */}
          <ProductConditions productId={1}/>  
          {/* 시장 데이터 */}
          {/* <MarketData /> */}
          {/* 추천 거래 지역 정보 */}
          {/* <RegionStatsTable /> */}
          {/* 개인화된 평가 */}
          <PersonalizedAssessment />     
        </div>
      </main>
    </div>
  );
};

export default ProductPage;
