import { createClient } from '@/utils/supabase/server'
import { redirect } from 'next/navigation'
import HeroSection from '@/components/sections/HeroSection'
import HotCitiesSection from '@/components/sections/HotCitiesSection'
import TrendSection from '@/components/sections/TrendSection'
import ExploreSection from '@/components/sections/ExploreSection'
import GuideSection from '@/components/sections/GuideSection'
import ReviewSection from '@/components/sections/ReviewSection'
import RecommendSection from '@/components/sections/RecommendSection'

export default async function HomePage() {
  const supabase = await createClient()

  const {
    data: { user },
  } = await supabase.auth.getUser()

  if (!user) {
    redirect('/login')
  }

  return (
    <>
      <HeroSection />
      <HotCitiesSection />
      <TrendSection />
      <ExploreSection />
      <GuideSection />
      <ReviewSection />
      <RecommendSection />
    </>
  )
}