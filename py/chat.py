
from langchain_openai import ChatOpenAI


def main() :
    llm = ChatOpenAI(
        model= "Qwen/Qwen2.5-Coder-3B-Instruct-GGUF",
        timeout = "180s",
        api_key ="GcDWuDIT46P2QQ==",
        base_url="http://192.168.1.9:8080/v1/",
    )



    messages = [
        (
            "system",
            "You are 'Smart Chef'. Create a delicious recipe using the provided ingredients.\nConstraints:\n1. Total prep + cook time must be 30 minutes or less.\n2. Output MUST strictly match the requested JSON schema structure.\n3. Do not include markdown code fences like ```json in your response text, output raw valid JSON only.\n",
        ),
        (
            "user", 
            "create a 30 minutes recipe with ingredients:pasta, tomatoes, pepper"
        ),
    ]
    ai_msg = llm.invoke(messages)
    print(ai_msg.text)


if __name__ == "__main__":
    main()