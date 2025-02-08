import { useState } from "react";

const getDefaultDates = () => {
  const today = new Date();
  const oneWeekAgo = new Date();
  oneWeekAgo.setDate(today.getDate() - 7);

  const formatDate = (date: Date) => date.toISOString().split("T")[0];

  return {
    today: formatDate(today),
    oneWeekAgo: formatDate(oneWeekAgo),
  };
};

const DatePicker = ({
  onDateChange,
}: {
  onDateChange: (startDate: string, endDate: string) => void;
}) => {
  const { today, oneWeekAgo } = getDefaultDates();
  const [startDate, setStartDate] = useState(oneWeekAgo);
  const [endDate, setEndDate] = useState(today);

  const handleDateChange = () => {
    onDateChange(startDate, endDate);
  };

  return (
    <div className="bg-white p-4 rounded-lg shadow mb-6 flex flex-col md:flex-row md:justify-between">
      <div className="flex items-center space-x-4">
        <div>
          <label htmlFor="start-date" className="text-sm text-gray-700">
            시작 날짜:
          </label>
          <input
            type="date"
            id="start-date"
            value={startDate}
            onChange={(e) => setStartDate(e.target.value)}
            onBlur={handleDateChange}
            className="border border-gray-300 rounded-lg px-4 py-2 ml-2"
          />
        </div>
        <span className="text-gray-600">~</span>
        <div>
          <label htmlFor="end-date" className="text-sm text-gray-700">
            종료 날짜:
          </label>
          <input
            type="date"
            id="end-date"
            value={endDate}
            onChange={(e) => setEndDate(e.target.value)}
            onBlur={handleDateChange}
            className="border border-gray-300 rounded-lg px-4 py-2 ml-2"
          />
        </div>
      </div>
    </div>
  );
};

export default DatePicker;
