// components/UserInformation.tsx

const UserInformation = () => {
  return (
    <div className="lg:col-span-4 space-y-4">
      <h2 className="text-2xl font-semibold text-gray-700">나의 정보</h2>
      <div className="bg-white border rounded-lg shadow-sm p-4">
        <div className="flex justify-between">
          <span className="text-gray-600 font-medium">Name:</span>
          <span className="text-gray-800">John Doe</span>
        </div>
        <div className="flex justify-between">
          <span className="text-gray-600 font-medium">Email:</span>
          <span className="text-gray-800">john.doe@example.com</span>
        </div>
        <div className="flex justify-between">
          <span className="text-gray-600 font-medium">Phone:</span>
          <span className="text-gray-800">+123 456 7890</span>
        </div>
        <div className="flex justify-between">
          <span className="text-gray-600 font-medium">나의 농장:</span>
          <span className="text-gray-800">경기도 성남시</span>
        </div>
      </div>
    </div>
  );
};

export default UserInformation;
