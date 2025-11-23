from langchain_community.document_loaders import PyPDFLoader
from langchain_core.documents import Document
from langchain_text_splitters import RecursiveCharacterTextSplitter
from langchain_openai import OpenAIEmbeddings, ChatOpenAI
from langchain_chroma import Chroma
from langchain_core.prompts import ChatPromptTemplate
from langchain_core.output_parsers import StrOutputParser
from langchain_core.runnables import RunnablePassthrough, RunnableWithMessageHistory
from langchain_community.chat_message_histories import ChatMessageHistory
import streamlit as st
from dotenv import load_dotenv

load_dotenv()

@st.cache_resource
def process_pdf() -> list[Document]:
    import requests
    import os
    from tempfile import NamedTemporaryFile

    url = "https://raw.githubusercontent.com/langchain-kr/langchain-tutorial/refs/heads/main/Ch02.%20RAG/Data/2024_KB_%EB%B6%80%EB%8F%99%EC%82%B0_%EB%B3%B4%EA%B3%A0%EC%84%9C_%EC%B5%9C%EC%A2%85.pdf"

    resp = requests.get(url, stream=True)
    resp.raise_for_status()

    with NamedTemporaryFile(suffix=".pdf", delete=False) as tmp:
        tmp.write(resp.content)
        tmp_path = tmp.name

    try:
        loader = PyPDFLoader(tmp_path)
        documents = loader.load()
        print(f'로드된 문서 수: {len(documents)}')
        text_splitter = RecursiveCharacterTextSplitter(chunk_size=1000, chunk_overlap=200)
        return text_splitter.split_documents(documents)
    finally:
        os.remove(tmp_path)

@st.cache_resource
def initialize_vectorstore() -> Chroma:
    chunks = process_pdf()
    embeddings = OpenAIEmbeddings()

    vectorstore = Chroma.from_documents(
        documents=chunks,
        embedding=embeddings
    )

    print(f'벡터 스토어에 저장된 문서 수: {vectorstore._collection.count()}')
    return vectorstore


@st.cache_resource
def initialize_prompt() -> ChatPromptTemplate:
    template = """당신은 KB 부동산 보고서 전문가입니다. 다음 정보를 바탕으로 사용자의 질문에 답변해주세요.

    컨텍스트: {context}
    """
    prompt = ChatPromptTemplate.from_messages(
        [
            ("system", template),
            ("placeholder", "{chat_history}"),
            ("human", "{question}")
        ]
    )

    print("프롬프트가 초기화되었습니다. 예시:")
    print(prompt.format(context="컨텍스트 예시", chat_history=["대화 기록 예시1", "대화 기록 예시2"], question="질문 예시"))

    return prompt


def format_docs(docs: list[Document]) -> str:
    return "\n\n".join(doc.page_content for doc in docs)


def initialize_chain() -> RunnableWithMessageHistory:
    retriever = initialize_vectorstore().as_retriever(search_kwargs={"k": 3})
    prompt = initialize_prompt()

    model = ChatOpenAI(model="gpt-4o-mini", temperature=0)
    base_chain = (
            RunnablePassthrough.assign(
                context=lambda x: format_docs(retriever.invoke(x['question']))
            )
            | prompt
            | model
            | StrOutputParser()
    )
    chat_history = ChatMessageHistory()
    chain_with_memory = RunnableWithMessageHistory(
        base_chain,
        lambda session_id: chat_history,  # 세션 ID별 대화 기록 생성
        input_messages_key="question",
        history_messages_key="chat_history",
    )
    return chain_with_memory

def main():
    st.set_page_config(page_title="KB 부동산 보고서 챗봇", page_icon="🏠")
    st.title("🏠 KB 부동산 보고서 AI 어드바이저")
    st.caption("2024 KB 부동산 보고서 기반 질의응답 시스템")

    if "messages" not in st.session_state:
        st.session_state.messages = []

    for message in st.session_state.messages:
        with st.chat_message(message["role"]):
            st.markdown(message["content"])

    if prompt := st.chat_input("부동산 관련 질문을 입력하세요"):
        # 사용자 메시지 표시
        with st.chat_message("user"):
            st.markdown(prompt)
        st.session_state.messages.append({"role": "user", "content": prompt})

        # 체인 초기화
        chain = initialize_chain()

        # AI 응답 생성
        with st.chat_message("assistant"):
            with st.spinner("답변 생성 중..."):
                response = chain.invoke(
                    {"question": prompt},
                    {"configurable": {"session_id": "streamlit_session"}}
                )
                st.markdown(response)

        st.session_state.messages.append({"role": "assistant", "content": response})


if __name__ == "__main__":
    main()
