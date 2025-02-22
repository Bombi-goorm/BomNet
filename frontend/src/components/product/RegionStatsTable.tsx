import { regionStats } from "../../data_sample";

const RegionStatsTable = () => {
  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-xl font-semibold mb-4">추천 지역 거래 정보</h2>
      {/* 테이블 */}
      <table className="w-full text-left border-collapse">
        <thead>
          <tr>
            <th className="border-b py-2">지역</th>
            <th className="border-b py-2">거래량</th>
            <th className="border-b py-2">거래금액</th>
          </tr>
        </thead>
        <tbody>
          {regionStats.map((stat) => (
            <tr key={stat.id}>
              <td className="py-2">{stat.region}</td>
              <td className="py-2">{stat.tradeVolume} 톤</td>
              <td className="py-2">{(stat.tradeValue / 100000000).toFixed(1)}억 원</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default RegionStatsTable;
