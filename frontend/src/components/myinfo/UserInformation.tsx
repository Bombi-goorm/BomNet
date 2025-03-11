import React from "react";

interface User {
  email: string;
  pnuCode: string;
  joinDate: string;
}

interface UserInformationProps {
  user: User;
}

const UserInformation: React.FC<UserInformationProps> = ({ user }) => {
  return (
    <div className="bg-white p-6 rounded-lg shadow-md">
      <h2 className="text-2xl font-semibold text-gray-700">사용자 정보</h2>
      <div className="grid grid-cols-2 gap-4">
        <div className="text-gray-600 font-medium">이메일:</div>
        <div className="text-gray-800">{user.email}</div>
        <div className="text-gray-600 font-medium">PNU코드:</div>
        <div className="text-gray-800">{user.pnuCode}</div>
        <div className="text-gray-600 font-medium">가입일:</div>
        <div className="text-gray-800">{user.joinDate}</div>
      </div>
    </div>
  );
};

export default UserInformation;