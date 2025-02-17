import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import App from './App.tsx';
import { AuthProvider } from './conntext_api/AuthProvider.tsx';
import { BrowserRouter } from 'react-router-dom';
import ReactQueryProvider from './react_query/QueryProvider.tsx';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <ReactQueryProvider>
      <BrowserRouter>      
        <AuthProvider>
          <App />
        </AuthProvider>      
      </BrowserRouter>
    </ReactQueryProvider>
  </StrictMode>
);
