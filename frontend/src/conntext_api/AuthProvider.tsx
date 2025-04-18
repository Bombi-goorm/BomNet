import { useQueryClient } from "@tanstack/react-query";
import { createContext, useContext, useState, ReactNode, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { logoutMember, renewAccess } from "../api/auth_api";

type AuthContextType = {
  isAuthenticated: boolean; // 인증 여부
  isLoading: boolean; // 로딩 여부 추가
  login: (userData: { memberId: string; pnu: string }) => void;
  logout: () => void;
};

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const navigate = useNavigate();
  const location = useLocation();
  const queryClient = useQueryClient();

  const [bomnetUser, setBomnetUser] = useState<{ memberId: string | null; pnu: string | null }>({
    memberId: sessionStorage.getItem("bomnet_user") || null,  
    pnu: sessionStorage.getItem("bomnet_pnu") || null,  
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
            sessionStorage.setItem("bomnet_user", response.data.memberId); 
            sessionStorage.setItem("bomnet_pnu", response.data.pnu || "");  
            setBomnetUser({
              memberId: response.data.memberId,
              pnu: response.data.pnu || null,
            });
            setIsAuthenticated(true);
          } else {
            setIsAuthenticated(false);
          }
        }
      } catch (error) {
        setIsAuthenticated(false);
      } finally {
        setIsLoading(false); // 로딩 종료
      }
    };

    restoreAuthentication();
  }, []); // 최초 한 번 실행

  const handleUnauthorized = async () => {
    sessionStorage.removeItem("bomnet_user");
    sessionStorage.removeItem("bomnet_pnu");
    setBomnetUser({ memberId: null, pnu: null });
    setIsAuthenticated(false);
    // 이미 /login 경로라면 navigate 호출하지 않음
    if (location.pathname !== "/login") {
      navigate("/login");
    }
  };

  const login = (userData: { memberId: string; pnu: string }) => {
    sessionStorage.setItem("bomnet_user", userData.memberId); 
    sessionStorage.setItem("bomnet_pnu", userData.pnu); 
    setBomnetUser({ memberId: userData.memberId, pnu: userData.pnu });
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