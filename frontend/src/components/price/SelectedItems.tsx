const SelectedItems = ({
  items,
  onRemoveItem,
}: {
  items: { item: string; variety: string; region: string }[];
  onRemoveItem: (index: number) => void;
}) => {
  return (
    <div className="bg-white p-4 rounded-lg shadow mb-6">
      <h3 className="text-lg font-bold mb-4">임시 저장 품목 (최대 5개)</h3>
      <ul className="space-y-2">
        {items.map((item, index) => (
          <li
            key={index}
            className="flex justify-between items-center border-b pb-2"
          >
            <span>
              {item.item} ({item.variety}, {item.region})
            </span>
            <button
              onClick={() => onRemoveItem(index)}
              className="text-red-500 hover:underline"
            >
              제거
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default SelectedItems;
