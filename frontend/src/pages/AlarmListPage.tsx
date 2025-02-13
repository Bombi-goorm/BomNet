// pages/AlarmListPage.tsx
import React, { useState } from "react";
import Header from "../components/Header";
import AlarmList from "../components/myinfo/AlarmList";

interface Alarm {
  name: string;
  alertType: string;
  isSelected: boolean;
}

// 샘플 알림 데이터 (필요에 따라 최대 10개까지 추가 가능)
const sampleAlarms: Alarm[] = [
  { name: "기상 특보", alertType: "화산분화", isSelected: false },
  { name: "기상 특보", alertType: "태풍", isSelected: true },
  { name: "가격 알림", alertType: "사과(홍옥) - 가격 1,700원", isSelected: false },
  { name: "가격 알림", alertType: "감자 - 가격 500원", isSelected: false },
];

const AlarmListPage: React.FC = () => {
  const [alarms, setAlarms] = useState<Alarm[]>(sampleAlarms);

  // 알림 선택 토글 핸들러
  const handleCheck = (index: number) => {
    setAlarms((prevAlarms) => {
      const newAlarms = [...prevAlarms];
      newAlarms[index].isSelected = !newAlarms[index].isSelected;
      return newAlarms;
    });
  };

  // 알림 삭제 핸들러
  const handleDelete = (index: number) => {
    setAlarms((prevAlarms) => prevAlarms.filter((_, i) => i !== index));
  };

  return (
    <>
      <Header />
      <div className="container mx-auto p-4">
        <AlarmList
          alarms={alarms}
          handleCheck={handleCheck}
          handleDelete={handleDelete}
        />
      </div>
    </>
  );
};

export default AlarmListPage;
