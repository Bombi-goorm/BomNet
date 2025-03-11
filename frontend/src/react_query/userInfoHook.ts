import { useQuery } from "@tanstack/react-query";
import { CommonResponseDto, InfoResponseDto } from "../types/member_types";
import { getMemberInfo } from "../api/core_api";

export const userInfoHook = () => {
  return useQuery<InfoResponseDto, Error>({
    queryKey: ["userInfo"],
    queryFn: async () => {
      const response: CommonResponseDto<InfoResponseDto> = await getMemberInfo();
      return response.data; 
    }
  });
};
