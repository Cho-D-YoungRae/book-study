import CityCard from '@/components/cards/CityCard'
import { Button } from '@/components/ui/button'
import { Sparkles, RefreshCw } from 'lucide-react'

const recommendedCities = [
  {
    name: '속초',
    district: '대포항',
    image: '/images/sokcho-daepo.jpg',
    rating: 4.7,
    reviewCount: 87,
    monthlyBudget: '100-140',
    cafeDensity: 78,
    internetQuality: 90,
    trending: false,
    tags: ['조용한', '바다근처'],
    matchReason: '바다 근처 + 저렴한 생활비'
  },
  {
    name: '전주',
    district: '한옥마을',
    image: '/images/jeonju-hanok.jpg',
    rating: 4.6,
    reviewCount: 156,
    monthlyBudget: '90-130',
    cafeDensity: 82,
    internetQuality: 92,
    trending: false,
    tags: ['문화', '맛집'],
    matchReason: '풍부한 문화 + 저렴한 물가'
  },
  {
    name: '춘천',
    district: '명동',
    image: '/images/chuncheon-myeongdong.jpg',
    rating: 4.5,
    reviewCount: 102,
    monthlyBudget: '100-150',
    cafeDensity: 85,
    internetQuality: 94,
    trending: true,
    tags: ['자연', '카페'],
    matchReason: '자연 환경 + 카페 밀집'
  }
]

export default function RecommendSection() {
  return (
    <section className="py-16 lg:py-24 bg-gradient-to-b from-purple-50 to-white">
      <div className="container mx-auto px-4">
        {/* Section Header */}
        <div className="text-center mb-10">
          <div className="flex items-center justify-center gap-2 mb-3">
            <Sparkles className="h-8 w-8 text-purple-600" />
            <h2 className="text-3xl font-bold">맞춤 추천</h2>
          </div>
          <p className="text-muted-foreground">당신의 선호도에 맞는 도시를 추천해드려요</p>
        </div>

        {/* Preference Pills */}
        <div className="flex flex-wrap justify-center gap-3 mb-10">
          <div className="px-4 py-2 bg-purple-100 text-purple-700 rounded-full text-sm font-medium">
            ✓ 바다 근처
          </div>
          <div className="px-4 py-2 bg-purple-100 text-purple-700 rounded-full text-sm font-medium">
            ✓ 저렴한 물가
          </div>
          <div className="px-4 py-2 bg-purple-100 text-purple-700 rounded-full text-sm font-medium">
            ✓ 카페 많은 곳
          </div>
          <Button variant="ghost" size="sm" className="gap-2">
            <RefreshCw className="h-4 w-4" />
            선호도 변경
          </Button>
        </div>

        {/* Recommended Cities */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {recommendedCities.map((city, index) => (
            <div key={`${city.name}-${city.district}`} className="relative">
              {/* Match Reason Badge */}
              <div className="absolute -top-3 left-4 right-4 z-10">
                <div className="bg-purple-600 text-white text-xs px-3 py-1 rounded-full text-center">
                  {city.matchReason}
                </div>
              </div>
              <div className="pt-3">
                <CityCard city={city} />
              </div>
            </div>
          ))}
        </div>

        {/* CTA */}
        <div className="text-center mt-10">
          <p className="text-muted-foreground mb-4">더 정확한 추천을 원하시나요?</p>
          <Button size="lg" className="gap-2">
            <Sparkles className="h-4 w-4" />
            상세 선호도 설정하기
          </Button>
        </div>
      </div>
    </section>
  )
}