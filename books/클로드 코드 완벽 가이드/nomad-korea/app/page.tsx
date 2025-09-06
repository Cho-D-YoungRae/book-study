import HeroSection from '@/components/sections/HeroSection'
import HotCitiesSection from '@/components/sections/HotCitiesSection'
import TrendSection from '@/components/sections/TrendSection'
import ExploreSection from '@/components/sections/ExploreSection'
import GuideSection from '@/components/sections/GuideSection'
import ReviewSection from '@/components/sections/ReviewSection'
import RecommendSection from '@/components/sections/RecommendSection'

export default function HomePage() {
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