/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",          // Vite의 기본 HTML 파일
    "./src/**/*.{js,jsx,ts,tsx}" // React 컴포넌트 파일 경로
  ],
  theme: {
    extend: {}, // 커스텀 테마를 추가할 수 있음
  },
  plugins: [],
};

