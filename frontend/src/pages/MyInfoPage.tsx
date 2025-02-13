import { useState } from "react";
import Header from "../components/Header";
import InterestsVarietiesSettings, { InterestItem } from "../components/myinfo/InterestsList";
import UserInformation from "../components/myinfo/UserInformation";
import { interestItems } from "../data_sample";

const MyInfoPage = () => {
  const [interests, setInterests] = useState<InterestItem[]>(interestItems);

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

  const handleInterestsDelete = (index: number) => {
    setInterests((prevInterests) =>
      prevInterests.filter((_, i) => i !== index)
    );
  };

  return (
    <>
      <Header />
      <div className="container mx-auto p-4 space-y-8 max-w-lg">
        <UserInformation />
      </div>
      <div className="container mx-auto p-4 space-y-8 max-w-[60rem]">
        <InterestsVarietiesSettings
          items={interests}
          handleCheck={handleInterestsCheck}
          handleDelete={handleInterestsDelete}
        />
      </div>
    </>
  );
};

export default MyInfoPage;
