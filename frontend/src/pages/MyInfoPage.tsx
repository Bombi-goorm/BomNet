import { useState, useEffect } from "react";
import Header from "../components/Header";
import UserInformation from "../components/myinfo/UserInformation";
import PriceAlertList from "../components/myinfo/PriceAlertList";
import FarmAssessment from "../components/myinfo/FarmAssessment";


// 타입 정의
import { InfoResponseDto } from "../types/member_types";
import { getMemberInfo } from "../api/core_api";

const MyInfoPage = () => {
  const [userInfo, setUserInfo] = useState<InfoResponseDto | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchMemberInfo = async () => {
      try {
        const memberResponse = await getMemberInfo();

        console.log(memberResponse.data)

        setUserInfo(memberResponse.data);
      } catch (error) {
        console.error("사용자 정보를 불러오는 중 오류 발생:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchMemberInfo();
  }, []);

  if (loading) {
    return (
      <>
        <Header />
        <div className="flex items-center justify-center h-screen">
          <div className="spinner"></div>
        </div>
      </>
    );
  }

  if (!userInfo) {
    return (
      <>
        <Header />
        <div className="text-center p-4">❌ 사용자 정보를 불러올 수 없습니다.</div>
      </>
    );
  }

  return (
    <>
      <Header />

      {/* 사용자 정보 영역 */}
      <div className="container mx-auto p-4 space-y-8 max-w-lg">
        <UserInformation user={{ email: userInfo.email, pnu: userInfo.pnu, joinDate: userInfo.joinDate }} /> 
      </div>

      {/* 내 농장 정보 영역 */}
      <div className="container mx-auto p-4 space-y-8 max-w-[50rem]">
        <FarmAssessment farmData={userInfo.myFarm} />
      </div>

      {/* 추천 생산품 영역 */}
      {/* <div className="container mx-auto p-4 space-y-8 max-w-[30rem]">
        <RecommendedProducts recommendedProducts={userInfo.recommendedProducts || []} />
      </div> */}

      {/* 가격 조건 목록 영역 */}
      <div className="container mx-auto p-4 space-y-8 max-w-[60rem]">
        <PriceAlertList initialAlerts={userInfo.notificationConditions} />
      </div>
    </>
  );
};

export default MyInfoPage;
