import React from "react";

interface User {
  email: string;
  pnu?: string;  
  joinDate: string; 
}

interface UserInformationProps {
  user?: User;  
}

const UserInformation: React.FC<UserInformationProps> = ({ user }) => {

  const formatDate = (isoString: string): string => {
    return isoString.split("T")[0]; 
  };

  if (!user) {
    return <div className="text-center text-gray-500">🔄 사용자 정보를 불러오는 중...</div>;
  }

  return (
    <div className="bg-white p-6 rounded-lg shadow-md">
      <h2 className="text-2xl font-semibold text-gray-700">사용자 정보</h2>
      <div className="grid grid-cols-2 gap-4">
        <div className="text-gray-600 font-medium">이메일:</div>
        <div className="text-gray-800">{user.email}</div>
        <div className="text-gray-600 font-medium">PNU코드:</div>
        <div className="text-gray-800">{user.pnu || "정보 없음"}</div>
        <div className="text-gray-600 font-medium">가입일:</div>
        <div className="text-gray-800">{formatDate(user.joinDate) || "알 수 없음"}</div>
      </div>
    </div>
  );
};

export default UserInformation;