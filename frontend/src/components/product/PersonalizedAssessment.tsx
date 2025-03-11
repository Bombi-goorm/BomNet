
import React from "react";
import { FarmSuitability } from "../../types/product_types";

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

interface PersonalizedAssessmentProps {
  farmSuitability: FarmSuitability;
}

const PersonalizedAssessment: React.FC<PersonalizedAssessmentProps> = ({ farmSuitability }) => {
  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-xl font-semibold mb-4">내 농장 적합도 평가</h2>
      <ul className="space-y-2">
        {farmSuitability.anayize.map((analysis, index) => (
          <li key={index}>
            <span>{analysis.reason}: </span>
            <span className={getSuitabilityColor(analysis.suitability)}>
              ({analysis.suitability})
            </span>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default PersonalizedAssessment;