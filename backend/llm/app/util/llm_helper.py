from openai import OpenAI

client = OpenAI(api_key="YOUR_OPENAI_API_KEY")

def query_llm(prompt: str, max_tokens=50, temperature=0.3) -> str:
    """
    ✅ OpenAI GPT-4를 사용하여 최적화된 응답을 반환하는 함수
    """
    response = client.chat.completions.create(
        model="gpt-4-turbo",
        messages=[{"role": "system", "content": "응답을 2~3문장으로 요약하세요."},
                  {"role": "user", "content": prompt}],
        max_tokens=max_tokens,
        temperature=temperature
    )
    return response.choices[0].message.content.strip()