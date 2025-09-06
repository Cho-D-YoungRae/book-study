import { createClient } from '@/utils/supabase/server'
import { redirect } from 'next/navigation'

export default async function DashboardPage() {
  const supabase = await createClient()

  const {
    data: { user },
  } = await supabase.auth.getUser()

  if (!user) {
    redirect('/login')
  }

  return (
    <div className="min-h-screen p-8">
      <div className="mx-auto max-w-4xl">
        <h1 className="text-3xl font-bold">대시보드</h1>
        <p className="mt-4 text-gray-600">환영합니다, {user.email}님!</p>
        
        <div className="mt-8 rounded-lg bg-white p-6 shadow">
          <h2 className="text-xl font-semibold">사용자 정보</h2>
          <dl className="mt-4 space-y-2">
            <div>
              <dt className="inline font-medium">이메일:</dt>
              <dd className="inline ml-2">{user.email}</dd>
            </div>
            <div>
              <dt className="inline font-medium">가입일:</dt>
              <dd className="inline ml-2">
                {new Date(user.created_at).toLocaleDateString('ko-KR')}
              </dd>
            </div>
          </dl>
        </div>

        <form action={async () => {
          'use server'
          const supabase = await createClient()
          await supabase.auth.signOut()
          redirect('/login')
        }} className="mt-6">
          <button
            type="submit"
            className="rounded-md bg-red-600 px-4 py-2 text-white hover:bg-red-700"
          >
            로그아웃
          </button>
        </form>
      </div>
    </div>
  )
}