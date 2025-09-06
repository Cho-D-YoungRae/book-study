import CityCard from '@/components/cards/CityCard'
import { Flame } from 'lucide-react'

const hotCities = [
  {
    name: '서울',
    district: '성수동',
    image: '/images/seoul-seongsu.jpg',
    rating: 4.8,
    reviewCount: 234,
    monthlyBudget: '150-200',
    cafeDensity: 95,
    internetQuality: 98,
    trending: true,
    tags: ['카페천국', '스타트업']
  },
  {
    name: '부산',
    district: '해운대',
    image: '/images/busan-haeundae.jpg',
    rating: 4.7,
    reviewCount: 189,
    monthlyBudget: '120-160',
    cafeDensity: 88,
    internetQuality: 95,
    trending: false,
    tags: ['바다뷰', '서핑']
  },
  {
    name: '제주',
    district: '애월',
    image: '/images/jeju-aewol.jpg',
    rating: 4.9,
    reviewCount: 312,
    monthlyBudget: '140-180',
    cafeDensity: 92,
    internetQuality: 93,
    trending: true,
    tags: ['자연', '힐링']
  },
  {
    name: '서울',
    district: '강남',
    image: '/images/seoul-gangnam.jpg',
    rating: 4.6,
    reviewCount: 456,
    monthlyBudget: '180-250',
    cafeDensity: 98,
    internetQuality: 99,
    trending: false,
    tags: ['편의시설', '교통']
  }
]

export default function HotCitiesSection() {
  return (
    <section className="py-16 lg:py-24">
      <div className="container mx-auto px-4">
        {/* Section Header */}
        <div className="flex items-center justify-between mb-10">
          <div className="flex items-center gap-3">
            <Flame className="h-8 w-8 text-red-500" />
            <h2 className="text-3xl font-bold">HOT 도시</h2>
          </div>
          <a href="/cities" className="text-sm font-medium text-primary hover:underline">
            전체보기 →
          </a>
        </div>

        {/* City Cards Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          {hotCities.map((city, index) => (
            <CityCard key={`${city.name}-${city.district}-${index}`} city={city} />
          ))}
        </div>
      </div>
    </section>
  )
}