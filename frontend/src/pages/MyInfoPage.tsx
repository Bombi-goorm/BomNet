import { useState } from "react";
import Header from "../components/Header";
import UserInformation from "../components/myinfo/UserInformation";
import PriceAlertList, { PriceAlertItem } from "../components/myinfo/PriceAlertList";
import { priceAlertItems, user } from "../data_sample";
import FarmAssessment from "../components/product/FarmAssessment";
import RecommendedProducts from "../components/product/RecommendedProducts";

const MyInfoPage = () => {
  // 가격 알림 상태 (가격 정보 포함)
  const [alarms, setAlarms] = useState<PriceAlertItem[]>(priceAlertItems);


  // 가격 알림 삭제
  const handleAlarmsDelete = (index: number) => {
    setAlarms((prevAlarms) => prevAlarms.filter((_, i) => i !== index));
  };

  return (
    <>
      <Header />
      <div className="container mx-auto p-4 space-y-8 max-w-lg">
        <UserInformation user={user} />
      </div>

      <div className="container mx-auto p-4 space-y-8 max-w-[50rem]">
          {/* 내 농장 정보 */}
          <FarmAssessment />
      </div>

      <div className="container mx-auto p-4 space-y-8 max-w-[30rem]">
      <RecommendedProducts />
      </div>

     

      <div className="container mx-auto p-4 space-y-8 max-w-[60rem]">
        {/* 가격 알림 설정 (품목, 품종, 가격 정보 포함) */}
        <PriceAlertList
          alerts={alarms}
          handleDelete={handleAlarmsDelete}
        />
      </div>
    </>
  );
};

export default MyInfoPage;