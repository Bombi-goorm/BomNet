import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

interface LoginFormProps {
  reload: boolean;
  setReload: React.Dispatch<React.SetStateAction<boolean>>;
}

const TEST_USER = {
  email: "test@example.com",
  password: "password123",
  id: "12345", // 테스트용 사용자 ID
};

const LoginForm: React.FC<LoginFormProps> = ({ reload, setReload }) => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    email: TEST_USER.email,
    password: TEST_USER.password,
    terms: false,
  });

  const [errorMessage, setErrorMessage] = useState("");

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!formData.terms) {
      setErrorMessage("You must agree to the terms and conditions to log in.");
      return;
    }

    if (formData.email === TEST_USER.email && formData.password === TEST_USER.password) {
      // 로그인 성공 시 로컬 스토리지에 저장
      localStorage.setItem("email", TEST_USER.email);
      localStorage.setItem("userId", TEST_USER.id);
      setReload(!reload);
      navigate("/");
    } else {
      setErrorMessage("Invalid email or password.");
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      {errorMessage && <div className="mb-4 text-red-600 text-sm">{errorMessage}</div>}

      <div>
        <label htmlFor="email" className="block text-sm font-medium text-gray-700">
          이메일
        </label>
        <input
          type="email"
          name="email"
          id="email"
          placeholder="user@example.com"
          value={formData.email}
          onChange={handleChange}
          required
          className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-blue-500 focus:border-blue-500"
        />
      </div>

      <div>
        <label htmlFor="password" className="block text-sm font-medium text-gray-700">
          비밀번호
        </label>
        <input
          type="password"
          name="password"
          id="password"
          placeholder="••••••"
          value={formData.password}
          onChange={handleChange}
          required
          className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-blue-500 focus:border-blue-500"
        />
      </div>

      <div className="flex items-center space-x-2">
        <input
          type="checkbox"
          name="terms"
          id="terms"
          checked={formData.terms}
          onChange={handleChange}
          className="h-4 w-4 text-green-500 border-gray-300 rounded"
        />
        <label htmlFor="terms" className="text-sm text-gray-600">
          I agree to the{" "}
          <a href="#" className="text-green-500 hover:underline">Terms of Service</a> and{" "}
          <a href="#" className="text-green-500 hover:underline">Privacy Policy</a>.
        </label>
      </div>

      <button type="submit" className="w-full bg-green-500 text-white py-2 rounded-lg hover:bg-green-600 transition">
        Sign In
      </button>

      <div className="text-center">
        <button type="button" onClick={() => navigate("/SignUp")} className="text-green-500 hover:underline">
          Sign Up
        </button>
      </div>
    </form>
  );
};

export default LoginForm;
