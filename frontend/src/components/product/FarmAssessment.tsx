import { farmData } from "../../data_sample";

const FarmAssessment = () => {
  return (
    <div className="bg-white p-6 rounded-lg shadow-md">
      
      <h2 className="text-xl font-semibold mb-4">내 농장 정보</h2>
      
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        {/* 기상 정보 */}
        <div>
          <h3 className="text-lg font-semibold mb-2">기상 (Meteorological)</h3>
          <ul className="list-disc pl-5">
            <li>월동 여부: {farmData.overwintering}</li>
            <li>평균 기온: {farmData.averageTemperature}</li>
            <li>최저 기온: {farmData.minTemperature}</li>
            <li>최고 기온: {farmData.maxTemperature}</li>
            <li>연간 강수량: {farmData.annualRainfall}</li>
            <li>일조 시간: {farmData.sunlightHours}</li>
          </ul>
        </div>

        {/* 물리 정보 */}
        <div>
          <h3 className="text-lg font-semibold mb-2">물리 (Physical)</h3>
          <ul className="list-disc pl-5">
            <li>배수 등급: {farmData.drainage}</li>
            <li>유효 토심: {farmData.soilDepth}</li>
            <li>경사도: {farmData.slopeDegree}</li>
            <li>토성: {farmData.soilTexture}</li>
          </ul>
        </div>

        {/* 화학 정보 */}
        <div>
          <h3 className="text-lg font-semibold mb-2">화학 (Chemical)</h3>
          <ul className="list-disc pl-5">
            <li>산도: {farmData.ph}</li>
            <li>유기물: {farmData.organicMatterGPerKg}</li>
            <li>유효인산: {farmData.avPMgPerKg}</li>
            <li>칼륨: {farmData.kMgPerKg}</li>
            <li>칼슘: {farmData.caMgPerKg}</li>
            <li>마그네슘: {farmData.mgMgPerKg}</li>
          </ul>
        </div>
      </div>
    </div>
    
  );
};

export default FarmAssessment;