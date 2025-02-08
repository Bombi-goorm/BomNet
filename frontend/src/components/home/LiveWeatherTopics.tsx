const LiveWeatherTopics = () => {
  const topics = [
    "2025년 02월 06일 13:00 | 경기도 한파경보 발령",
    "2025년 02월 05일 13:00 | 울릉도 한파경보 발령",
    "2025년 02월 04일 13:00 | 경상도 한파경보 발령",
  ];

  return (
    <div className="bg-white p-6 rounded-lg shadow-md">
      <h2 className="text-xl font-semibold mb-4 text-center">실시간 기상 토픽</h2>
      <ul className="divide-y divide-gray-200">
        {topics.map((topic, index) => (
          <li key={index} className="text-gray-700 py-2 text-sm md:text-base">
            {topic}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default LiveWeatherTopics;
