const MyLocalWeather = () => {
    const todayWeather = {
      region: "서울",
      temperature: "2°C",
      condition: "맑음",
      humidity: "50%",
      wind: "5 m/s",
    };
  
    return (
      <div className="bg-white p-6 rounded-lg shadow-md">
        <h2 className="text-xl font-semibold mb-4 text-center">내 지역의 오늘 날씨</h2>
        <div className="space-y-2 text-gray-700">
          <p>지역: <span className="font-bold">{todayWeather.region}</span></p>
          <p>기온: <span className="font-bold">{todayWeather.temperature}</span></p>
          <p>날씨: <span className="font-bold">{todayWeather.condition}</span></p>
          <p>습도: <span className="font-bold">{todayWeather.humidity}</span></p>
          <p>바람: <span className="font-bold">{todayWeather.wind}</span></p>
        </div>
      </div>
    );
  };
  
  export default MyLocalWeather;
  