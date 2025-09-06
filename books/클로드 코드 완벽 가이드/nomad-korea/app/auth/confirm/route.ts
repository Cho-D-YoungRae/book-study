import { createClient } from '@/utils/supabase/server'
import { NextResponse } from 'next/server'

export async function GET(request: Request) {
  const { searchParams } = new URL(request.url)
  const token_hash = searchParams.get('token_hash')
  const type = searchParams.get('type') as 'email' | 'recovery' | 'email_change'
  const next = searchParams.get('next') ?? '/'

  if (!token_hash || !type) {
    return NextResponse.redirect(new URL('/error', request.url))
  }

  const supabase = await createClient()
  
  const { error } = await supabase.auth.verifyOtp({
    token_hash,
    type,
  })

  if (error) {
    return NextResponse.redirect(new URL('/error', request.url))
  }

  return NextResponse.redirect(new URL(next, request.url))
}