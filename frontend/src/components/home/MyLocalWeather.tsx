import { useQueryClient } from "@tanstack/react-query";
import { WeatherExpections, WeatherInfo } from "../../types/types";
import { WiDaySunny, WiCloudy, WiRain, WiSnow, WiThunderstorm } from "react-icons/wi";

// 날씨 상태에 따른 아이콘 반환 함수
const weatherIcon = (weather: string) => {
  if (weather.includes("맑음")) return <WiDaySunny className="text-6xl text-yellow-300" />;
  if (weather.includes("구름")) return <WiCloudy className="text-6xl text-gray-300" />;
  if (weather.includes("비")) return <WiRain className="text-6xl text-blue-400" />;
  if (weather.includes("눈")) return <WiSnow className="text-6xl text-blue-200" />;
  if (weather.includes("천둥")) return <WiThunderstorm className="text-6xl text-purple-400" />;
  return <WiDaySunny className="text-6xl text-yellow-300" />;
};

const MyLocalWeather = () => {
  const queryClient = useQueryClient();

  // "weatherExpect" 키로 캐싱된 기상 예보 데이터를 가져옵니다.
  const weatherExpectations = queryClient.getQueryData<WeatherExpections>(["weatherExpect"]);

  if (!weatherExpectations) {
    return <div>날씨 데이터를 가져올 수 없습니다.</div>;
  }

  const targetRegion = weatherExpectations?.location; // 지역 정보
  const weatherInfoArray: WeatherInfo[] = weatherExpectations?.weatherInfo || [];
  const currentWeather: WeatherInfo | undefined = weatherInfoArray.length > 0 ? weatherInfoArray[0] : undefined;
  const forecastList: WeatherInfo[] = weatherInfoArray.length > 1 ? weatherInfoArray.slice(1) : [];

  // 시간 포매팅 함수 (HH 형식)
  const formatTime = (dateTime: string) => {
    const date = new Date(dateTime);
    return date.getHours().toString().padStart(2, "0");
  };

  return (
    <div className="bg-gradient-to-r from-blue-600 to-blue-400 p-6 rounded-lg shadow-lg text-white">
      {/* 현재 날씨 */}
      <h2 className="text-2xl font-bold mb-4 text-center">{targetRegion}</h2>
      {currentWeather ? (
        <div className="text-center mb-8">
          <div className="flex items-center justify-around">
            <div className="flex items-center space-x-4">
              {weatherIcon(currentWeather.weather)}
              <div>
                <p className="text-lg">{currentWeather.weather}</p>
                <p className="text-5xl font-bold">{currentWeather.temperature}</p>
                <p className="text-sm mt-2">습도: {currentWeather.humidity} | 바람: {currentWeather.wind}</p>
              </div>
            </div>
          </div>
        </div>
      ) : (
        <p className="text-center">현재 날씨 정보를 가져올 수 없습니다.</p>
      )}

      {/* 앞으로의 예보 */}
      {forecastList.length > 0 && (
        <div className="mt-6">
          <div className="flex justify-center space-x-6">
            {forecastList.map((forecast, index) => (
              <div key={index} className="text-center">
                {weatherIcon(forecast.weather)}
                <p className="text-lg font-bold mt-2">{forecast.temperature}</p>
                <p className="text-sm text-gray-100">{formatTime(forecast.dateTime)}시</p>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
};

export default MyLocalWeather;
