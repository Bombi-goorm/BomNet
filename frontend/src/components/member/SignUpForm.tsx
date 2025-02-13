import React, { useState } from 'react';
import axios from 'axios';

interface SignUpFormProps {
  reload: boolean;
  setReload: React.Dispatch<React.SetStateAction<boolean>>;
}

const SignUpForm: React.FC<SignUpFormProps> = ({ reload, setReload }) => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    phone: '',
    birthdate: '',
    farmAddress: '',
    terms: false,
  });

  const [errorMessage, setErrorMessage] = useState('');

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === 'checkbox' ? checked : value,
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!formData.terms) {
      setErrorMessage('You must agree to the terms and conditions.');
      return;
    }
    try {
      const response = await axios.post('', {
        email: formData.email,
        password: formData.password,
        name: formData.name,
        phone: formData.phone,
        birthday: formData.birthdate,
        farmAddress: formData.farmAddress,
      });
      alert('회원가입이 완료되었습니다!');
      console.log('Success:', response.data);
      setReload(!reload);
    } catch (error) {
      if (axios.isAxiosError(error)) {
        setErrorMessage(error.response?.data?.message || 'Failed to register. Please try again.');
      } else {
        setErrorMessage('There was an error with the registration.');
      }
      console.error('Error:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      {errorMessage && <div className="text-red-600 text-sm">{errorMessage}</div>}     
      <div>
        <label htmlFor="farmAddress" className="block text-gray-700">농지 법정동 코드(Option)</label>
        <input type="text" name="farmAddress" id="farmAddress" placeholder="Enter your farm address" value={formData.farmAddress} onChange={handleChange} required className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
      </div>
      <div>
        <label htmlFor="farmAddress" className="block text-gray-700">농지 PNU 코드(Option)</label>
        <input type="text" name="farmAddress" id="farmAddress" placeholder="Enter your farm address" value={formData.farmAddress} onChange={handleChange} required className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
      </div>
      <div>
        <input type="checkbox" name="terms" id="terms" checked={formData.terms} onChange={handleChange} className="mr-2" />
        <label htmlFor="terms" className="text-sm text-gray-700">
          I agree to the{' '}
          <a href="#" className="text-green-600 hover:underline">Terms of Service</a>{' '}
          and{' '}
          <a href="#" className="text-green-600 hover:underline">Privacy Policy</a>.
        </label>
      </div>
      <div className="mt-4">
        <button type="submit" className="w-full bg-green-500 text-white py-2 rounded-md hover:bg-green-600 focus:outline-none focus:ring-2 focus:ring-green-500">
          Sign Up
        </button>
      </div>
    </form>
  );
};

export default SignUpForm;
