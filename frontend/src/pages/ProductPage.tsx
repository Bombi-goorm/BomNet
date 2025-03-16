import { useState } from "react";
import Header from "../components/Header";
import ProductSearch from "../components/product/ProductSearch";
import ProductConditions from "../components/product/ProductConditions";
import PersonalizedAssessment from "../components/product/PersonalizedAssessment";
import { productInfo } from "../api/core_api";
import { CommonResponseDto } from "../types/member_types";
import { ProductRequestDto, ProductResponseDto } from "../types/product_types";


const ProductPage = () => {
  const [productData, setProductData] = useState<ProductResponseDto | null>(null);
  const [loading, setLoading] = useState<boolean>(false);

  // 검색 조건을 받아 API 호출하여 데이터를 가져오는 함수
  const handleSearch = async (searchCriteria: { item: string; variety: string; pnu: string }) => {
    setLoading(true);
    try {
      const requestData: ProductRequestDto = {
        item: searchCriteria.item,
        variety: searchCriteria.variety,
        pnu: searchCriteria.pnu
      };
      // API 호출 (상품 정보 조회)
      const response: CommonResponseDto<ProductResponseDto> = await productInfo(requestData);


      if (response.status === "200") {
        setProductData(response.data);
      } else {
        alert("데이터 조회 실패: " + response.message);
      }
    } catch (error) {
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="bg-gray-50 min-h-screen">
      <Header />
      <main className="max-w-6xl mx-auto p-4 space-y-8">
        {/* 검색 조건 입력 */}
        <ProductSearch onSearch={handleSearch} />

        {loading && <p>데이터 로딩 중...</p>}

        {/* 받아온 데이터가 있을 경우, 재배조건과 적합도 평가 컴포넌트에 전달 */}
        {productData && (
          <>
            <ProductConditions productData={productData} />
            <PersonalizedAssessment farmSuitability={productData.farmSuitability} />
          </>
        )}
      </main>
    </div>
  );
};

export default ProductPage;