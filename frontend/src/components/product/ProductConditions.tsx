import { sampleCultivationData, sampleProductData } from "../../data_sample";


const ProductConditions = ({ productId }: { productId: number }) => {
  // 선택한 작물 정보 찾기 (productId로 매칭)
  const cropData = sampleCultivationData.find((crop) => crop.productId === productId);

  if (!cropData) return <div>해당 작물의 정보를 찾을 수 없습니다.</div>;

  const { conditions, cultivationFeatures, managementTips } = cropData;

  // 상품 데이터에서 이미지 찾기 (productId 기준)
  const productData = sampleProductData.find((product) => product.productId === productId);
  const productImageUrl = productData
    ? productData.imgUrl
    : "https://s3.amazonaws.com/my-bucket/default_image.jpg"; // 기본 이미지

  return (
    <div className="bg-white p-6 rounded-lg shadow flex flex-col md:flex-row">
      {/* 이미지 섹션 */}
      <div className="w-full md:w-1/3 mb-6 md:mb-0">
        <img
          src={productImageUrl} // 상품 데이터에서 가져온 이미지
          alt={`${cropData.cropName} ${cropData.variety ? `(${cropData.variety})` : ""}`}
          className="rounded-lg w-full h-auto"
        />
      </div>

      {/* 상세 정보 및 생산 조건 */}
      <div className="w-full md:w-2/3 pl-0 md:pl-6">
        {/* 상품 상세 정보 */}
        <h2 className="text-xl font-semibold mb-4">
          {cropData.cropName} {cropData.variety ? `(${cropData.variety})` : ""}
        </h2>
        <p className="mb-6">{cultivationFeatures}</p>

        {/* 생산 조건 */}
        <div className="bg-gray-50 p-4 rounded-lg">
          <h3 className="text-lg font-semibold mb-4">📌 생산 조건</h3>
          <ul className="list-disc list-inside space-y-2">
            <li>🌡️ 기온: {conditions.avgTemperatureC}°C (최저 {conditions.minTemperatureC}°C, 최고 {conditions.maxTemperatureC}°C)</li>
            <li>☀️ 일조량: {conditions.sunlightHours}시간</li>
            <li>🌱 토양 pH: {conditions.pH}</li>
            <li>💧 배수: {conditions.drainage}</li>
            <li>📏 토심: {conditions.soilDepth}cm</li>
          </ul>
        </div>

        {/* 관리 팁 */}
        <div className="bg-gray-100 p-4 rounded-lg mt-4">
          <h3 className="text-lg font-semibold mb-4">🌾 관리 팁</h3>
          <p>{managementTips}</p>
        </div>
      </div>
    </div>
  );
};

export default ProductConditions;
