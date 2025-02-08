
const news = [
  {
    title: "물가, 5개월만에 다시 2%대로…고환율·고유가 충격파",
    summary:
      "세계적 이상 기후에 따른 농산물 작황 부진 등도 한몫했다. 정부는 2월 말 만료 예정인 한시적 유류세 인하 조치를 3월 이후에도 연장할지 검토 중이다.",
    link: "#",
    date: "2025-02-06 00:39",
  },
  {
    title: "채소·과일값 안정세?…치솟은 식품·외식물가에 직장인 체감 어려워",
    summary:
      "채소와 과일 가격은 유통사 연휴 후 안정세를 보였으나, 외식 물가 상승률은 여전히 높은 수준을 유지하고 있습니다.",
    link: "#",
    date: "2025-02-06 00:39",
  },
];

const NewsList = () => {
  return (
    <div className="w-full max-w-4xl mx-auto mt-8">
      <h2 className="text-lg font-bold mb-4 text-center">뉴스</h2>
      <ul className="space-y-6">
        {news.map((article, index) => (
          <li
            key={index}
            className="bg-white shadow-md rounded-lg p-6 hover:shadow-lg transition-shadow"
          >
            <h3 className="font-semibold text-xl mb-2 text-gray-800">
              {article.title}
            </h3>
            <p className="text-sm text-gray-600 mb-4">{article.summary}</p>
            <a
              href={article.link}
              className="text-green-500 text-sm hover:underline"
            >
              [기사 더보기]
            </a>
            <p className="text-xs text-gray-400 mt-4">{article.date}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default NewsList;
