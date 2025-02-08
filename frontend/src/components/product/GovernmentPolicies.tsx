import { governmentPolicies } from "../../data_sample";

const GovernmentPolicies = () => {
  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-xl font-semibold mb-4">정부 정책 정보</h2>
      <p><strong>보조금 대상 품목:</strong> {governmentPolicies.subsidies.join(", ")}</p>
      <p><strong>금지 품목:</strong> {governmentPolicies.restrictions.join(", ")}</p>
    </div>
  );
};

export default GovernmentPolicies;
