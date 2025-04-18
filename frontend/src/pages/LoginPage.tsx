import Header from "../components/Header";

function LoginPage() {
  const KAKAO_AUTH_URL = "https://auth.bomnet.shop/oauth2/authorization/kakao"; 
  const handleProviderLogin = (provider: string) => {
    if (provider === "kakao") {
      window.location.href = KAKAO_AUTH_URL;
    }
  };

  return (
    <>
      <Header />
      <div className="font-sans bg-gray-50 min-h-screen flex items-center justify-center">
        <main className="max-w-md w-full p-4">
          <div className="bg-white p-8 rounded shadow-md">
            <h2 className="text-2xl font-bold mb-6 text-center">로그인</h2>
            <div className="mt-6">
              <div className="space-y-2">
                <button
                  onClick={() => handleProviderLogin("kakao")}
                  className="w-full flex items-center justify-center space-x-2 bg-yellow-400 hover:bg-yellow-500 text-black p-2 rounded"
                >
                  <span>Kakao Login</span>
                </button>
              </div>
            </div>
          </div>
        </main>
      </div>
    </>
  );
}

export default LoginPage;
