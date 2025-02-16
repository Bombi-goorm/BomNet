import json


def write_to_jsonl(data, output_path, key):
    if key not in data:
        print("Error: Invalid key in API response.")
        return

    row_data = data[key]["row"]

    with open(output_path, 'w', encoding='utf-8') as f:
        for row in row_data:
            json.dump(row, f, ensure_ascii=False)
            f.write('\n')

    print(f"Data saved to {output_path}")
