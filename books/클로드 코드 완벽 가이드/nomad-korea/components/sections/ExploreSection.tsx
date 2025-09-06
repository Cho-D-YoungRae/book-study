import { Card, CardContent } from '@/components/ui/card'
import { MapPin } from 'lucide-react'

const regions = [
  {
    name: '수도권',
    cities: ['서울', '경기', '인천'],
    count: 45,
    color: 'bg-blue-100 hover:bg-blue-200 border-blue-300'
  },
  {
    name: '강원도',
    cities: ['강릉', '속초', '춘천'],
    count: 23,
    color: 'bg-green-100 hover:bg-green-200 border-green-300'
  },
  {
    name: '충청도',
    cities: ['대전', '천안', '청주'],
    count: 18,
    color: 'bg-yellow-100 hover:bg-yellow-200 border-yellow-300'
  },
  {
    name: '경상도',
    cities: ['부산', '대구', '경주'],
    count: 38,
    color: 'bg-red-100 hover:bg-red-200 border-red-300'
  },
  {
    name: '전라도',
    cities: ['광주', '전주', '여수'],
    count: 27,
    color: 'bg-purple-100 hover:bg-purple-200 border-purple-300'
  },
  {
    name: '제주도',
    cities: ['제주시', '서귀포', '애월'],
    count: 15,
    color: 'bg-orange-100 hover:bg-orange-200 border-orange-300'
  }
]

export default function ExploreSection() {
  return (
    <section className="py-16 lg:py-24">
      <div className="container mx-auto px-4">
        {/* Section Header */}
        <div className="text-center mb-10">
          <h2 className="text-3xl font-bold mb-3">지역별 탐색</h2>
          <p className="text-muted-foreground">원하는 지역의 노마드 도시를 탐색해보세요</p>
        </div>

        {/* Regions Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {regions.map((region) => (
            <Card 
              key={region.name}
              className={`cursor-pointer transition-all duration-200 border-2 ${region.color}`}
            >
              <CardContent className="p-6">
                <div className="flex items-start justify-between mb-4">
                  <div>
                    <h3 className="text-xl font-bold mb-2">{region.name}</h3>
                    <p className="text-sm text-muted-foreground">
                      {region.count}개 도시
                    </p>
                  </div>
                  <MapPin className="h-6 w-6 text-muted-foreground" />
                </div>
                
                <div className="space-y-2">
                  <p className="text-sm font-medium">인기 도시</p>
                  <div className="flex flex-wrap gap-2">
                    {region.cities.map((city) => (
                      <span 
                        key={city}
                        className="px-2 py-1 text-xs bg-background rounded-md border"
                      >
                        {city}
                      </span>
                    ))}
                  </div>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>

        {/* Map Placeholder */}
        <div className="mt-12 rounded-lg overflow-hidden border-2 border-dashed border-gray-300">
          <div className="h-64 lg:h-96 bg-gray-100 flex items-center justify-center">
            <div className="text-center">
              <MapPin className="h-12 w-12 text-gray-400 mx-auto mb-4" />
              <p className="text-gray-500">인터랙티브 지도가 여기에 표시됩니다</p>
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}