import { farmData, governmentPolicies, nationwideTradeInfo } from "../../data_sample";

const getSuitabilityColor = (condition: string) => {
  switch (condition) {
    case "적합":
      return "text-green-500 font-bold";
    case "평균":
      return "text-yellow-500 font-bold";
    case "부적합":
      return "text-red-500 font-bold";
    default:
      return "text-gray-500";
  }
};

const PersonalizedAssessment = () => {
  // 적합도 계산 (임의의 기준 설정 가능)
  const suitability = {
    soilType: "평균", // 토양 적합도
    averageTemperature: "평균", // 평균 기온 적합도
    annualRainfall: "적합", // 연간 강수량 적합도
    tradeVolume: nationwideTradeInfo.totalVolume > 1500 ? "적합" : "부적합", // 거래량 기준
    policies: governmentPolicies.subsidies.includes("사과") ? "적합" : "부적합", // 정부 보조금 정책
  };

  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-xl font-semibold mb-4">내 농장 적합도 평가</h2>
      <ul className="space-y-2">
    
        <li>
          <span>토양 유형: {farmData.soilType}</span>{" "}
          <span className={getSuitabilityColor(suitability.soilType)}>
            ({suitability.soilType})
          </span>
        </li>
        <li>
          <span>평균 기온: {farmData.averageTemperature}</span>{" "}
          <span
            className={getSuitabilityColor(suitability.averageTemperature)}
          >
            ({suitability.averageTemperature})
          </span>
        </li>
        <li>
          <span>연간 강수량: {farmData.annualRainfall}</span>{" "}
          <span className={getSuitabilityColor(suitability.annualRainfall)}>
            ({suitability.annualRainfall})
          </span>
        </li>
        <li>
          <span>거래량: {nationwideTradeInfo.totalVolume}톤</span>{" "}
          <span className={getSuitabilityColor(suitability.tradeVolume)}>
            ({suitability.tradeVolume})
          </span>
        </li>
        <li>
          <span>정책: 보조금 대상 - {governmentPolicies.subsidies.join(", ")}</span>{" "}
          <span className={getSuitabilityColor(suitability.policies)}>
            ({suitability.policies})
          </span>
        </li>
      </ul>
      <p className="mt-4 text-green-500 font-bold">평가 결과: 적합</p>
    </div>
  );
};

export default PersonalizedAssessment;
