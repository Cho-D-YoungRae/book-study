import Link from 'next/link'

export default function ErrorPage() {
  return (
    <div className="flex min-h-screen items-center justify-center">
      <div className="text-center">
        <h1 className="text-4xl font-bold text-red-600">오류가 발생했습니다</h1>
        <p className="mt-4 text-gray-600">
          요청을 처리하는 중 문제가 발생했습니다.
        </p>
        <Link
          href="/login"
          className="mt-6 inline-block rounded-md bg-indigo-600 px-4 py-2 text-white hover:bg-indigo-700"
        >
          로그인 페이지로 돌아가기
        </Link>
      </div>
    </div>
  )
}