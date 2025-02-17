import { useQueryClient } from "@tanstack/react-query";
import { createContext, useContext, useState, ReactNode, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { logoutMember } from "../api/auth_api";
import { renewAccess } from "../api/core_api";

type AuthContextType = {
  isAuthenticated: boolean; // 인증 여부
  isLoading: boolean; // 로딩 여부 추가
  login: (userData: { memberId: string; BJD: string }) => void;
  logout: () => void;
};

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const [bomnetUser, setBomnetUser] = useState<{ memberId: string | null; BJD: string | null }>({
    memberId: sessionStorage.getItem("bomnet_user")
      ? JSON.parse(sessionStorage.getItem("bomnet_user")!)
      : null,
    BJD: sessionStorage.getItem("bomnet_bjd")
      ? JSON.parse(sessionStorage.getItem("bomnet_bjd")!)
      : null,
  });

  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false); // 초기값: false
  const [isLoading, setIsLoading] = useState<boolean>(true); // 로딩 상태 추가

  useEffect(() => {
    const restoreAuthentication = async () => {
      setIsLoading(true); // 로딩 시작
      try {
        if (bomnetUser.memberId) {
          setIsAuthenticated(true);
        } else {
          const response = await renewAccess();
          if (response.status === "200") {
            sessionStorage.setItem(
              "bomnet_user",
              JSON.stringify(response.data.memberId)
            );
            sessionStorage.setItem(
              "bomnet_bjd",
              JSON.stringify(response.data.BJD)
            );
            setBomnetUser({
              memberId: response.data.memberId || null,
              BJD: response.data.BJD || null,
            });
            setIsAuthenticated(true);
          } else {
            setIsAuthenticated(false);
          }
        }
      } catch (error) {
        console.error("인증 상태 확인 실패:", error);
        setIsAuthenticated(false);
      } finally {
        setIsLoading(false); // 로딩 종료
      }
    };

    restoreAuthentication();
  }, [bomnetUser.memberId]);

  const handleUnauthorized = async () => {
    sessionStorage.removeItem("bomnet_user");
    sessionStorage.removeItem("bomnet_bjd");
    setBomnetUser({ memberId: null, BJD: null });
    setIsAuthenticated(false);
    navigate("/login");
  };

  const login = (userData: { memberId: string; BJD: string }) => {
    sessionStorage.setItem("bomnet_user", JSON.stringify(userData.memberId));
    sessionStorage.setItem("bomnet_bjd", JSON.stringify(userData.BJD));
    setBomnetUser({ memberId: userData.memberId, BJD: userData.BJD });
    setIsAuthenticated(true);
    queryClient.invalidateQueries({ queryKey: ["userInfo"] });
  };

  const logout = async () => {
    try {
      const response = await logoutMember();
      if (response.status === "200") {
        handleUnauthorized();
      } else {
        alert("로그아웃 실패! 잠시 후 다시 시도하세요.");
      }
    } catch (error) {
      console.error("로그아웃 처리 실패:", error);
      alert("로그아웃 처리 중 문제가 발생했습니다.");
    }
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, isLoading, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};
