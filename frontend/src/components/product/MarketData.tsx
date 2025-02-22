import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from "recharts";

const marketData = [
  { name: "판매량", value: 1200 },
  { name: "수입량", value: 800 },
  { name: "수출량", value: 600 },
];

const MarketData = () => {
  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-xl font-semibold mb-4">시장 데이터</h2>
      <div className="w-full h-[300px]">
        <ResponsiveContainer>
          <BarChart data={marketData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="value" fill="#82ca9d" />
          </BarChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
};

export default MarketData;
