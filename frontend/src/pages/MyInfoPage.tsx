import { useState } from "react";
import Header from "../components/Header";
import InterestItemsSettings, { InterestItem } from "../components/myinfo/InterestsList";
import UserInformation from "../components/myinfo/UserInformation";
import PriceAlertList, { PriceAlertItem } from "../components/myinfo/PriceAlertList";
import { interestItems, priceAlertItems, user } from "../data_sample";
import FarmAssessment from "../components/product/FarmAssessment";
import RecommendedProducts from "../components/product/RecommendedProducts";

const MyInfoPage = () => {
  // 관심 품목 상태 (가격 정보 없이)
  const [interests, setInterests] = useState<InterestItem[]>(interestItems);
  // 가격 알림 상태 (가격 정보 포함)
  const [alarms, setAlarms] = useState<PriceAlertItem[]>(priceAlertItems);

  // 관심 품목 선택 토글 (최대 5개 선택 제한)
  const handleInterestsCheck = (index: number) => {
    setInterests((prevInterests) => {
      const newItems = prevInterests.map((item) => ({ ...item }));
      if (newItems[index].isSelected) {
        newItems[index].isSelected = false;
      } else {
        const selectedCount = newItems.filter((item) => item.isSelected).length;
        if (selectedCount >= 5) {
          alert("최대 5개까지 선택 가능합니다.");
          return prevInterests;
        }
        newItems[index].isSelected = true;
      }
      return newItems;
    });
  };

  // 관심 품목 삭제
  const handleInterestsDelete = (index: number) => {
    setInterests((prevInterests) =>
      prevInterests.filter((_, i) => i !== index)
    );
  };

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
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8 max-w-[60rem] mx-auto">
          {/* 내 농장 정보 */}
          <FarmAssessment />
          {/* 추천 생산품 */}
          <RecommendedProducts />
      </div>
      
      <div className="container mx-auto p-4 space-y-8 max-w-[60rem]">
        {/* 관심 품목 설정 (가격 정보 없이) */}
        <InterestItemsSettings
          items={interests}
          handleCheck={handleInterestsCheck}
          handleDelete={handleInterestsDelete}
        />
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