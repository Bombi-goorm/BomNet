import React from "react";
import { WeatherNotice } from "../../types/home_types";

interface LiveWeatherTopicsProps {
  weatherNoticeData: WeatherNotice[] | undefined;
}

const LiveWeatherTopics: React.FC<LiveWeatherTopicsProps> = ({ weatherNoticeData }) => {
  if (!weatherNoticeData) {
    return <div className="text-center">로딩 중...</div>;
  }

  return (
    <div className="bg-white p-6 rounded-lg shadow-md">
      <h2 className="text-xl font-semibold mb-4 text-center">실시간 기상 토픽</h2>
      <ul className="divide-y divide-gray-200 text-center">
        {weatherNoticeData.length > 0 ? (
          weatherNoticeData.map((notice, index) => (
            <li key={index} className="text-gray-700 py-2 text-sm md:text-base">
              {notice.location}: {notice.content}{" "}
              <span className="text-xs text-gray-500">
                { notice.dateTime }
              </span>
            </li>
          ))
        ) : (
          <li className="text-gray-700 py-2 text-sm md:text-base">기상 토픽이 없습니다.</li>
        )}
      </ul>
    </div>
  );
};

export default LiveWeatherTopics;