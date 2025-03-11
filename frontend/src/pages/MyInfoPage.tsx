import { useState } from "react";
import Header from "../components/Header";
import UserInformation from "../components/myinfo/UserInformation";
import PriceAlertList, { PriceAlertItem } from "../components/myinfo/PriceAlertList";
import FarmAssessment from "../components/myinfo/FarmAssessment";
import RecommendedProducts from "../components/myinfo/RecommendedProducts";

// 샘플 데이터 (data_sample.ts 에서 export된 예시 데이터)
import { user, farmData, recommendedProducts, priceAlertItems } from "../data_sample";

const MyInfoPage = () => {
  // 가격 알림 상태 (샘플 데이터)
  const [alarms, setAlarms] = useState<PriceAlertItem[]>(priceAlertItems);

  // 가격 알림 삭제 함수
  const handleAlarmsDelete = (index: number) => {
    setAlarms((prevAlarms) => prevAlarms.filter((_, i) => i !== index));
  };

  return (
    <>
      <Header />

      {/* 사용자 정보 영역 (최대 너비: 640px) */}
      <div className="container mx-auto p-4 space-y-8 max-w-lg">
        <UserInformation user={user} />
      </div>

      {/* 내 농장 정보 영역 (최대 너비: 800px) */}
      <div className="container mx-auto p-4 space-y-8 max-w-[50rem]">
        <FarmAssessment farmData={farmData} />
      </div>

      {/* 추천 생산품 영역 (최대 너비: 480px) */}
      <div className="container mx-auto p-4 space-y-8 max-w-[30rem]">
        <RecommendedProducts recommendedProducts={recommendedProducts} />
      </div>

      {/* 가격 조건 목록 영역 (최대 너비: 960px) */}
      <div className="container mx-auto p-4 space-y-8 max-w-[60rem]">
        <PriceAlertList alerts={alarms} handleDelete={handleAlarmsDelete} />
      </div>
    </>
  );
};

export default MyInfoPage;