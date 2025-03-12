import { useState, useEffect } from "react";
import Header from "../components/Header";
import UserInformation from "../components/myinfo/UserInformation";
import PriceAlertList from "../components/myinfo/PriceAlertList";
import FarmAssessment from "../components/myinfo/FarmAssessment";
import RecommendedProducts from "../components/myinfo/RecommendedProducts";


// 타입 정의
import { InfoResponseDto } from "../types/member_types";
import { getMemberInfo } from "../api/core_api";

const MyInfoPage = () => {
  // ✅ 상태 변수: 서버에서 받은 데이터 저장
  const [userInfo, setUserInfo] = useState<InfoResponseDto | null>(null);
  const [loading, setLoading] = useState<boolean>(true);  // ✅ 로딩 상태 추가

  // ✅ 서버에서 사용자 정보 불러오기 (useEffect 사용)
  useEffect(() => {
    const fetchMemberInfo = async () => {
      try {
        const memberResponse = await getMemberInfo(); // 서버 API 호출
        setUserInfo(memberResponse.data);  // 상태 업데이트
      } catch (error) {
        console.error("사용자 정보를 불러오는 중 오류 발생:", error);
      } finally {
        setLoading(false);  // ✅ 로딩 완료
      }
    };

    fetchMemberInfo();  // 함수 실행
  }, []); // 처음 한 번 실행

  // ✅ 로딩 상태 처리
  if (loading) {
    return <div className="text-center p-4">🔄 사용자 정보를 불러오는 중...</div>;
  }

  // ✅ 데이터가 없을 경우 처리
  if (!userInfo) {
    return <div className="text-center p-4">❌ 사용자 정보를 불러올 수 없습니다.</div>;
  }

  return (
    <>
      <Header />

      {/* 사용자 정보 영역 */}
      <div className="container mx-auto p-4 space-y-8 max-w-lg">
        <UserInformation user={userInfo} />
      </div>

      {/* 내 농장 정보 영역 */}
      <div className="container mx-auto p-4 space-y-8 max-w-[50rem]">
        <FarmAssessment farmData={userInfo.myFarm} />
      </div>

      {/* 추천 생산품 영역 */}
      <div className="container mx-auto p-4 space-y-8 max-w-[30rem]">
        <RecommendedProducts recommendedProducts={userInfo.recommendedProducts} />
      </div>

      {/* 가격 조건 목록 영역 */}
      <div className="container mx-auto p-4 space-y-8 max-w-[60rem]">
        <PriceAlertList alerts={userInfo.notificationConditions} handleDelete={() => {}} />
      </div>
    </>
  );
};

export default MyInfoPage;