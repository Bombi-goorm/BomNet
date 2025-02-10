// src/pages/SignUpPage.tsx

import React, { useState } from 'react';
import Header from '../components/Header';  // Import Header component
import SignUpForm from '../components/member/SignUpForm'; // Import the SignUpForm component

const SignUpPage: React.FC = () => {
  const [reload, setReload] = useState(false); // Managing reload state with useState

  return (
    <>
      <Header />  {/* Header component */}
      <div className="flex items-center justify-center min-h-screen bg-gray-100">
        <div className="w-full max-w-md bg-white p-6 rounded-lg shadow-md">
          <h3 className="text-2xl font-semibold text-center text-gray-700 mb-4">
            Get Started
          </h3>
          {/* Passing reload and setReload as props to the SignUpForm */}
          <SignUpForm reload={reload} setReload={setReload} />
        </div>
      </div>
    </>
  );
};

export default SignUpPage;
