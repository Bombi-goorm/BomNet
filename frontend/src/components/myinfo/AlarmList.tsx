// components/AlarmList.tsx
import React from "react";

interface Alarm {
  name: string;
  alertType: string;
  isSelected: boolean;
}

interface AlarmListProps {
  alarms: Alarm[];
  handleCheck: (index: number) => void;
  handleDelete: (index: number) => void;
}

const AlarmList: React.FC<AlarmListProps> = ({ alarms, handleDelete }) => {
  return (
    <div>
      <h2 className="text-2xl font-semibold text-gray-700">알림 목록</h2>
      <div className="bg-white border p-4 rounded-lg shadow-sm">
        <table className="w-full table-auto">
          <tbody>
            {alarms.map((alarm, index) => (
              <tr key={index} className="border-t">
                <td className="py-2 px-4 text-gray-800">{index + 1}</td>
                <td className="py-2 px-4 text-gray-800">{alarm.name}</td>
                <td className="py-2 px-4 text-gray-800">{alarm.alertType}</td>
                
                <td className="py-2 px-4 text-center">
                  <button
                    onClick={() => handleDelete(index)}
                    className="text-red-500 hover:text-red-700"
                  >
                    <span className="text-lg">-</span> 삭제
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default AlarmList;
