import React from "react";
import { News } from "../../types/home_types";

interface NewsListProps {
  newsData: News[] | undefined;
}

const NewsList: React.FC<NewsListProps> = ({ newsData }) => {
  if (!newsData || newsData.length === 0) {
    return (
      <div className="text-center text-gray-500">
        뉴스 데이터를 가져올 수 없습니다.
      </div>
    );
  }

  return (
    <div className="w-full max-w-4xl mx-auto mt-8">
      <ul className="space-y-6">
        {newsData.map((article, index) => (
          <li
            key={index}
            className={`shadow-md rounded-lg p-6 hover:shadow-lg transition-shadow ${
              index % 2 !== 1 ? "bg-gray-50" : "bg-white"
            }`}
          >
            <h3 className="font-semibold text-xl mb-2 text-gray-800">
              {article.title}
            </h3>
            <p className="text-sm text-gray-600 mb-4">{article.content}</p>
            <a
              href={article.newsLink}
              target="_blank"
              rel="noopener noreferrer"
              className="text-green-500 text-sm hover:underline"
            >
              [기사 더보기]
            </a>
            <p className="text-xs text-gray-400 mt-4">
              {new Date(article.dateTime).toLocaleDateString()}{" "}
              {new Date(article.dateTime).toLocaleTimeString()}
            </p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default NewsList;