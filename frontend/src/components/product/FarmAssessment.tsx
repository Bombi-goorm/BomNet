import { farmData } from "../../data_sample";

const FarmAssessment = () => {
  return (
    <div className="bg-white p-6 rounded-lg shadow-md">
      <h2 className="text-xl font-semibold mb-4">내 농장 평가</h2>
      <ul className="space-y-2">
        <li>위치: {farmData.location}</li>
        <li>토양 유형: {farmData.soilType}</li>
        <li>평균 기온: {farmData.averageTemperature}</li>
        <li>연간 강수량: {farmData.annualRainfall}</li>
      </ul>
    </div>
  );
};

export default FarmAssessment;
