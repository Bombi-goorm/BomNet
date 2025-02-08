import Header from "../components/Header";
import FarmAssessment from "../components/product/FarmAssessment";
import GovernmentPolicies from "../components/product/GovernmentPolicies";
import MarketData from "../components/product/MarketData";
import PersonalizedAssessment from "../components/product/PersonalizedAssessment";
import ProductConditions from "../components/product/ProductConditions";
import ProductSearch from "../components/product/ProductSearch";
import RecommendedProducts from "../components/product/RecommendedProducts";
import RegionStatsTable from "../components/product/RegionStatsTable";

const ProductPage = () => {
  return (
    <div className="bg-gray-50 min-h-screen">
      <Header />
      <main className="max-w-6xl mx-auto p-4 space-y-8">

        {/* 검색창 및 추천 상품 */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          {/* 내 농장 정보 */}
          <FarmAssessment />
          {/* 추천 생산품 */}
          <RecommendedProducts />
        </div>
   

        {/* 상품 관련 정보 */}
        <div className="space-y-8">
          {/* 상품 검색 */}
          <ProductSearch />   
          {/* 정부 정책 정보 */}
          <GovernmentPolicies />         
          {/* 생산 조건 */}
          <ProductConditions />  
          {/* 시장 데이터 */}
          <MarketData />
          {/* 추천 거래 지역 정보 */}
          <RegionStatsTable />
          {/* 개인화된 평가 */}
          <PersonalizedAssessment />     
        </div>
      </main>
    </div>
  );
};

export default ProductPage;
