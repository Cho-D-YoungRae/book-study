import urllib.request

# 실제 코드는 text 를 다운로드하여 사용
def fetch_text_data() -> str:
    url = (
        "https://raw.githubusercontent.com/rasbt/"
        "LLMs-from-scratch/main/ch02/01_main-chapter-code/"
        "the-verdict.txt"
    )
    with urllib.request.urlopen(url) as response:
        return response.read().decode('utf-8')
