import { QueryClient, QueryClientProvider } from "@tanstack/react-query";


const queryClient = new QueryClient({
    defaultOptions: {
      queries: {
        gcTime: Infinity, // 캐싱 무제한 유지
        staleTime: Infinity, // 데이터가 만료되지 않도록 설정
      },
    },
  });
  

const ReactQueryProvider = ({ children }: { children: React.ReactNode }) => {
  return (
    <QueryClientProvider client={queryClient}>
      {children}
    </QueryClientProvider>
  );
};

export default ReactQueryProvider;
