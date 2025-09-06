import { Card, CardContent, CardHeader } from '@/components/ui/card'
import { Badge } from '@/components/ui/badge'
import { Star, MessageSquare, ThumbsUp, Calendar } from 'lucide-react'

const reviews = [
  {
    id: 1,
    author: {
      name: '김개발',
      avatar: '/avatars/user1.jpg',
      job: '프론트엔드 개발자'
    },
    city: '제주-애월',
    rating: 5,
    date: '2024년 9월 15일',
    content: '카페가 정말 많고 뷰가 좋아서 일하기 최고예요. 특히 해안도로 근처 카페들은 와이파이도 빵빵하고 좌석도 편해요.',
    helpful: 42,
    tags: ['카페추천', '오션뷰']
  },
  {
    id: 2,
    author: {
      name: '박디자인',
      avatar: '/avatars/user2.jpg',
      job: 'UX 디자이너'
    },
    city: '서울-성수동',
    rating: 4,
    date: '2024년 9월 12일',
    content: '스타트업이 많아서 네트워킹하기 좋아요. 코워킹 스페이스도 많고 카페도 늦게까지 열어서 작업하기 편합니다.',
    helpful: 38,
    tags: ['네트워킹', '코워킹']
  },
  {
    id: 3,
    author: {
      name: '이마케터',
      avatar: '/avatars/user3.jpg',
      job: '디지털 마케터'
    },
    city: '부산-해운대',
    rating: 5,
    date: '2024년 9월 10일',
    content: '바다 보면서 일할 수 있는 카페가 많아요. 서울보다 생활비도 저렴하고, 교통도 편리해서 장기 거주하기 좋습니다.',
    helpful: 56,
    tags: ['바다뷰', '가성비']
  },
  {
    id: 4,
    author: {
      name: '최콘텐츠',
      avatar: '/avatars/user4.jpg',
      job: '콘텐츠 크리에이터'
    },
    city: '강릉-경포',
    rating: 4,
    date: '2024년 9월 8일',
    content: '조용하고 집중하기 좋은 환경이에요. 다만 카페 선택지가 서울만큼 다양하지는 않아요. 그래도 바다가 가까워서 힐링됩니다.',
    helpful: 29,
    tags: ['조용한', '힐링']
  }
]

export default function ReviewSection() {
  return (
    <section className="py-16 lg:py-24">
      <div className="container mx-auto px-4">
        {/* Section Header */}
        <div className="flex items-center justify-between mb-10">
          <div>
            <h2 className="text-3xl font-bold mb-2">최신 리뷰</h2>
            <p className="text-muted-foreground">실제 노마드들의 생생한 경험담</p>
          </div>
          <a href="/reviews" className="text-sm font-medium text-primary hover:underline">
            전체 리뷰 보기 →
          </a>
        </div>

        {/* Reviews Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {reviews.map((review) => (
            <Card key={review.id} className="hover:shadow-lg transition-shadow duration-300">
              <CardHeader className="pb-4">
                <div className="flex items-start justify-between">
                  <div className="flex items-center gap-3">
                    <div className="w-12 h-12 rounded-full bg-gray-200 flex items-center justify-center">
                      <span className="text-lg font-semibold">{review.author.name[0]}</span>
                    </div>
                    <div>
                      <h3 className="font-semibold">{review.author.name}</h3>
                      <p className="text-sm text-muted-foreground">{review.author.job}</p>
                    </div>
                  </div>
                  <Badge variant="outline">{review.city}</Badge>
                </div>
              </CardHeader>
              <CardContent>
                {/* Rating and Date */}
                <div className="flex items-center justify-between mb-3">
                  <div className="flex items-center gap-1">
                    {[...Array(5)].map((_, i) => (
                      <Star
                        key={i}
                        className={`h-4 w-4 ${
                          i < review.rating
                            ? 'fill-yellow-400 text-yellow-400'
                            : 'text-gray-300'
                        }`}
                      />
                    ))}
                  </div>
                  <div className="flex items-center gap-1 text-sm text-muted-foreground">
                    <Calendar className="h-3 w-3" />
                    {review.date}
                  </div>
                </div>

                {/* Review Content */}
                <p className="text-sm mb-4">{review.content}</p>

                {/* Tags */}
                <div className="flex flex-wrap gap-2 mb-4">
                  {review.tags.map((tag) => (
                    <Badge key={tag} variant="secondary" className="text-xs">
                      #{tag}
                    </Badge>
                  ))}
                </div>

                {/* Interaction */}
                <div className="flex items-center justify-between pt-3 border-t">
                  <button className="flex items-center gap-2 text-sm text-muted-foreground hover:text-primary transition-colors">
                    <ThumbsUp className="h-4 w-4" />
                    도움됐어요 ({review.helpful})
                  </button>
                  <button className="flex items-center gap-2 text-sm text-muted-foreground hover:text-primary transition-colors">
                    <MessageSquare className="h-4 w-4" />
                    댓글
                  </button>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </section>
  )
}