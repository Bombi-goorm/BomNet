import { user } from "../../data_sample";

const UserInformation = () => {
  return (
    <div className="space-y-4">
      <h2 className="text-2xl font-semibold text-gray-700">나의 정보</h2>
      <div className="bg-white border rounded-lg shadow-sm p-4">
        <div className="grid grid-cols-2 gap-4">
          <div className="text-gray-600 font-medium">이메일:</div>
          <div className="text-gray-800">{user.email}</div>
          <div className="text-gray-600 font-medium">법정동코드:</div>
          <div className="text-gray-800">{user.legalCode}</div>
          <div className="text-gray-600 font-medium">PNU코드:</div>
          <div className="text-gray-800">{user.pnuCode}</div>
          <div className="text-gray-600 font-medium">가입일:</div>
          <div className="text-gray-800">{user.joinDate}</div>
        </div>
      </div>
    </div>
  );
};

export default UserInformation;
