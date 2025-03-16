import React from "react";
import { FarmSuitability } from "../../types/product_types";

const getSuitabilityColor = (condition: string) => {
  switch (condition) {
    case "적합":
      return "text-green-500 font-bold";
    case "보통":
      return "text-yellow-500 font-bold";
    case "부적합":
      return "text-red-500 font-bold";
    default:
      return "text-gray-500";
  }
};

interface PersonalizedAssessmentProps {
  farmSuitability: FarmSuitability;
}

const PersonalizedAssessment: React.FC<PersonalizedAssessmentProps> = ({ farmSuitability }) => {
  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-xl font-semibold mb-4">내 농장 적합도 평가</h2>

      <div className="space-y-4">
        {/* 기후 적합도 */}
        <div>
          <h3 className="text-lg font-semibold">기후 적합성</h3>
          <p className={getSuitabilityColor(farmSuitability.climateSuitability.suitability)}>
            {farmSuitability.climateSuitability.suitability}
          </p>
        </div>

        {/* 토양 화학적 적합도 */}
        <div>
          <h3 className="text-lg font-semibold">토양 화학적 적합성</h3>
          <p className={getSuitabilityColor(farmSuitability.soilChemicalSuitability.suitability)}>
            {farmSuitability.soilChemicalSuitability.suitability}
          </p>
          {farmSuitability.soilChemicalSuitability.unsuitableProperties.length > 0 && (
            <p className="text-red-500">
              부적합 요소: {farmSuitability.soilChemicalSuitability.unsuitableProperties.join(", ")}
            </p>
          )}
        </div>

        {/* 토양 물리적 적합도 */}
        <div>
          <h3 className="text-lg font-semibold">토양 물리적 적합성</h3>
          <p className={getSuitabilityColor(farmSuitability.soilPhysicalSuitability.suitability)}>
            {farmSuitability.soilPhysicalSuitability.suitability}
          </p>
          {farmSuitability.soilPhysicalSuitability.unsuitableProperties.length > 0 && (
            <p className="text-red-500">
              부적합 요소: {farmSuitability.soilPhysicalSuitability.unsuitableProperties.join(", ")}
            </p>
          )}
        </div>
      </div>
    </div>
  );
};

export default PersonalizedAssessment;