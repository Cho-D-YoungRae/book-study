import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Book, Briefcase, Home, Users, ArrowRight } from 'lucide-react'

const guides = [
  {
    icon: Briefcase,
    title: '원격근무 시작하기',
    description: '노마드 라이프를 위한 첫 걸음, 원격근무 준비 가이드',
    topics: ['필수 장비', '업무 도구', '시간 관리'],
    color: 'text-blue-600'
  },
  {
    icon: Home,
    title: '숙소 찾기',
    description: '한달살기부터 장기거주까지, 완벽한 숙소 찾기 팁',
    topics: ['숙소 유형', '계약 주의사항', '체크리스트'],
    color: 'text-green-600'
  },
  {
    icon: Users,
    title: '커뮤니티 참여',
    description: '현지 노마드들과 네트워킹하는 방법',
    topics: ['모임 찾기', '코워킹 스페이스', '이벤트'],
    color: 'text-purple-600'
  },
  {
    icon: Book,
    title: '도시별 생활 가이드',
    description: '각 도시의 특색과 노마드 라이프 팁',
    topics: ['교통', '맛집', '문화생활'],
    color: 'text-orange-600'
  }
]

export default function GuideSection() {
  return (
    <section className="py-16 lg:py-24 bg-gray-50">
      <div className="container mx-auto px-4">
        {/* Section Header */}
        <div className="text-center mb-10">
          <h2 className="text-3xl font-bold mb-3">노마드 가이드</h2>
          <p className="text-muted-foreground">성공적인 노마드 라이프를 위한 필수 정보</p>
        </div>

        {/* Guides Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          {guides.map((guide) => {
            const Icon = guide.icon
            return (
              <Card key={guide.title} className="group hover:shadow-lg transition-all duration-300 cursor-pointer">
                <CardHeader>
                  <div className={`${guide.color} mb-4`}>
                    <Icon className="h-10 w-10" />
                  </div>
                  <CardTitle className="text-lg">{guide.title}</CardTitle>
                </CardHeader>
                <CardContent>
                  <p className="text-sm text-muted-foreground mb-4">
                    {guide.description}
                  </p>
                  <div className="space-y-1 mb-4">
                    {guide.topics.map((topic) => (
                      <div key={topic} className="text-sm flex items-center gap-2">
                        <div className="w-1 h-1 bg-gray-400 rounded-full" />
                        <span>{topic}</span>
                      </div>
                    ))}
                  </div>
                  <div className="flex items-center gap-2 text-primary font-medium group-hover:gap-3 transition-all">
                    <span className="text-sm">자세히 보기</span>
                    <ArrowRight className="h-4 w-4" />
                  </div>
                </CardContent>
              </Card>
            )
          })}
        </div>

        {/* CTA Section */}
        <div className="mt-12 bg-gradient-to-r from-primary/10 to-primary/5 rounded-2xl p-8 text-center">
          <h3 className="text-2xl font-bold mb-3">노마드 입문자이신가요?</h3>
          <p className="text-muted-foreground mb-6">
            초보자를 위한 단계별 가이드로 노마드 라이프를 시작해보세요
          </p>
          <a 
            href="/guides/beginner" 
            className="inline-flex items-center gap-2 bg-primary text-primary-foreground px-6 py-3 rounded-lg font-medium hover:bg-primary/90 transition-colors"
          >
            입문 가이드 보기
            <ArrowRight className="h-4 w-4" />
          </a>
        </div>
      </div>
    </section>
  )
}