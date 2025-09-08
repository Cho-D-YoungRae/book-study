import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Badge } from '@/components/ui/badge'
import { TrendingUp, Users, Activity, ChevronUp } from 'lucide-react'

const trendingCities = [
  { name: '서울-성수동', change: 32, direction: 'up' },
  { name: '제주-애월', change: 28, direction: 'up' },
  { name: '부산-광안리', change: 24, direction: 'up' },
  { name: '강릉-경포', change: 18, direction: 'up' },
  { name: '서울-을지로', change: 15, direction: 'up' }
]

const communityStats = [
  { city: '서울', activeUsers: 1234, weeklyPosts: 89 },
  { city: '제주', activeUsers: 876, weeklyPosts: 72 },
  { city: '부산', activeUsers: 654, weeklyPosts: 45 },
  { city: '강릉', activeUsers: 432, weeklyPosts: 38 }
]

export default function TrendSection() {
  return (
    <section className="py-16 lg:py-24 bg-gray-50">
      <div className="container mx-auto px-4">
        {/* Section Header */}
        <div className="text-center mb-10">
          <h2 className="text-3xl font-bold mb-3">실시간 트렌드</h2>
          <p className="text-muted-foreground">노마드들이 주목하는 도시들을 확인해보세요</p>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* 상승 도시 */}
          <Card>
            <CardHeader className="flex flex-row items-center justify-between pb-3">
              <CardTitle className="text-lg flex items-center gap-2">
                <TrendingUp className="h-5 w-5 text-green-600" />
                인기 상승 도시
              </CardTitle>
              <Badge variant="secondary" className="text-xs">실시간</Badge>
            </CardHeader>
            <CardContent>
              <div className="space-y-3">
                {trendingCities.map((city, index) => (
                  <div key={city.name} className="flex items-center justify-between">
                    <div className="flex items-center gap-3">
                      <span className="text-2xl font-bold text-muted-foreground w-8">
                        {index + 1}
                      </span>
                      <span className="font-medium">{city.name}</span>
                    </div>
                    <div className="flex items-center gap-1 text-green-600">
                      <ChevronUp className="h-4 w-4" />
                      <span className="text-sm font-semibold">{city.change}%</span>
                    </div>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>

          {/* 커뮤니티 현황 */}
          <Card>
            <CardHeader className="flex flex-row items-center justify-between pb-3">
              <CardTitle className="text-lg flex items-center gap-2">
                <Users className="h-5 w-5 text-blue-600" />
                커뮤니티 현황
              </CardTitle>
              <Badge variant="secondary" className="text-xs">이번 주</Badge>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                {communityStats.map((stat) => (
                  <div key={stat.city} className="space-y-2">
                    <div className="flex items-center justify-between">
                      <span className="font-medium">{stat.city}</span>
                      <span className="text-sm text-muted-foreground">
                        {stat.activeUsers.toLocaleString()}명 활동
                      </span>
                    </div>
                    <div className="flex items-center gap-2">
                      <div className="flex-1 bg-gray-200 rounded-full h-2">
                        <div 
                          className="bg-blue-600 h-2 rounded-full"
                          style={{ width: `${(stat.activeUsers / 1234) * 100}%` }}
                        />
                      </div>
                      <span className="text-xs text-muted-foreground">
                        {stat.weeklyPosts}개 게시물
                      </span>
                    </div>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>

          {/* 노마드 인구 차트 */}
          <Card>
            <CardHeader className="flex flex-row items-center justify-between pb-3">
              <CardTitle className="text-lg flex items-center gap-2">
                <Activity className="h-5 w-5 text-purple-600" />
                월별 노마드 인구
              </CardTitle>
              <Badge variant="secondary" className="text-xs">2024</Badge>
            </CardHeader>
            <CardContent>
              <div className="space-y-2">
                {/* 간단한 차트 표현 */}
                <div className="h-32 flex items-end gap-2">
                  {[40, 45, 55, 65, 70, 85, 90, 95, 100].map((height, index) => (
                    <div 
                      key={index}
                      className="flex-1 bg-purple-200 rounded-t"
                      style={{ height: `${height}%` }}
                    >
                      <div 
                        className="bg-purple-600 rounded-t w-full"
                        style={{ height: '100%' }}
                      />
                    </div>
                  ))}
                </div>
                <div className="flex justify-between text-xs text-muted-foreground pt-2">
                  <span>1월</span>
                  <span>6월</span>
                  <span>9월</span>
                </div>
                <div className="text-center pt-2">
                  <p className="text-sm text-muted-foreground">전월 대비</p>
                  <p className="text-2xl font-bold text-purple-600">+12%</p>
                </div>
              </div>
            </CardContent>
          </Card>
        </div>
      </div>
    </section>
  )
}