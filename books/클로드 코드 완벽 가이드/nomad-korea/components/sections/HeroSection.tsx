import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Search, Waves, Mountain, Coffee, DollarSign } from 'lucide-react'

export default function HeroSection() {
  return (
    <section className="relative overflow-hidden bg-gradient-to-b from-gray-50 to-white py-20 lg:py-32">
      <div className="container mx-auto px-4">
        <div className="max-w-3xl mx-auto text-center">
          {/* Main Title */}
          <h1 className="text-4xl md:text-5xl lg:text-6xl font-bold tracking-tight">
            <span className="inline-block mb-2">🇰🇷</span>
            <br />
            한국에서 노마드하기 좋은 도시를 찾아보세요
          </h1>
          
          {/* Subtitle */}
          <p className="mt-6 text-xl text-muted-foreground">
            일하고, 살고, 즐기기 좋은 도시
          </p>

          {/* Search Bar */}
          <div className="mt-10 relative max-w-xl mx-auto">
            <div className="relative">
              <Search className="absolute left-4 top-1/2 -translate-y-1/2 h-5 w-5 text-muted-foreground" />
              <Input
                type="text"
                placeholder="어디서 노마드 라이프를 시작하시겠어요?"
                className="h-14 pl-12 pr-4 text-base"
              />
            </div>
          </div>

          {/* Quick Filters */}
          <div className="mt-8 flex flex-wrap justify-center gap-3">
            <Button variant="outline" size="lg" className="gap-2">
              <Waves className="h-4 w-4" />
              바다 근처
            </Button>
            <Button variant="outline" size="lg" className="gap-2">
              <Mountain className="h-4 w-4" />
              산 근처
            </Button>
            <Button variant="outline" size="lg" className="gap-2">
              <Coffee className="h-4 w-4" />
              카페 많은 곳
            </Button>
            <Button variant="outline" size="lg" className="gap-2">
              <DollarSign className="h-4 w-4" />
              저렴한 곳
            </Button>
          </div>
        </div>
      </div>

      {/* Background Decoration */}
      <div className="absolute inset-0 -z-10 overflow-hidden">
        <div className="absolute left-[max(-7rem,calc(50%-52rem))] top-1/2 -z-10 -translate-y-1/2 transform-gpu blur-2xl" aria-hidden="true">
          <div
            className="aspect-[577/310] w-[36.0625rem] bg-gradient-to-r from-[#ff80b5] to-[#9089fc] opacity-30"
            style={{
              clipPath:
                'polygon(74.8% 41.9%, 97.2% 73.2%, 100% 34.9%, 92.5% 0.4%, 87.5% 0%, 75% 28.6%, 58.5% 54.6%, 50.1% 56.8%, 46.9% 44%, 48.3% 17.4%, 24.7% 53.9%, 0% 27.9%, 11.9% 74.2%, 24.9% 54.1%, 68.6% 100%, 74.8% 41.9%)',
            }}
          />
        </div>
        <div className="absolute left-[max(45rem,calc(50%+8rem))] top-1/2 -z-10 -translate-y-1/2 transform-gpu blur-2xl" aria-hidden="true">
          <div
            className="aspect-[577/310] w-[36.0625rem] bg-gradient-to-r from-[#ff80b5] to-[#9089fc] opacity-30"
            style={{
              clipPath:
                'polygon(74.8% 41.9%, 97.2% 73.2%, 100% 34.9%, 92.5% 0.4%, 87.5% 0%, 75% 28.6%, 58.5% 54.6%, 50.1% 56.8%, 46.9% 44%, 48.3% 17.4%, 24.7% 53.9%, 0% 27.9%, 11.9% 74.2%, 24.9% 54.1%, 68.6% 100%, 74.8% 41.9%)',
            }}
          />
        </div>
      </div>
    </section>
  )
}