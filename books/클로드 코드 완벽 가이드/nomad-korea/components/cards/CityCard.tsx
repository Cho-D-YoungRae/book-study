'use client'

import { Card, CardContent } from '@/components/ui/card'
import { Badge } from '@/components/ui/badge'
import { Progress } from '@/components/ui/progress'
import LikeDislikeButton from '@/components/ui/like-dislike-button'
import { Wifi, Coffee, TrendingUp, MapPin } from 'lucide-react'
import { useState } from 'react'

interface CityCardProps {
  city: {
    name: string
    district: string
    image: string
    likes: number
    dislikes: number
    monthlyBudget: string
    cafeDensity: number
    internetQuality: number
    trending?: boolean
    tags?: string[]
  }
}

export default function CityCard({ city }: CityCardProps) {
  const [userVote, setUserVote] = useState<'like' | 'dislike' | null>(null)
  const [currentLikes, setCurrentLikes] = useState(city.likes)
  const [currentDislikes, setCurrentDislikes] = useState(city.dislikes)

  const handleLike = () => {
    if (userVote === 'like') {
      setUserVote(null)
      setCurrentLikes(prev => prev - 1)
    } else {
      if (userVote === 'dislike') {
        setCurrentDislikes(prev => prev - 1)
      }
      setUserVote('like')
      setCurrentLikes(prev => prev + 1)
    }
  }

  const handleDislike = () => {
    if (userVote === 'dislike') {
      setUserVote(null)
      setCurrentDislikes(prev => prev - 1)
    } else {
      if (userVote === 'like') {
        setCurrentLikes(prev => prev - 1)
      }
      setUserVote('dislike')
      setCurrentDislikes(prev => prev + 1)
    }
  }

  return (
    <Card className="group overflow-hidden hover:shadow-lg transition-all duration-300 cursor-pointer">
      {/* Image Section */}
      <div className="relative h-48 overflow-hidden">
        <img 
          src={city.image} 
          alt={`${city.name} ${city.district}`}
          className="w-full h-full object-cover group-hover:scale-110 transition-transform duration-300"
        />
        <div className="absolute top-3 left-3">
          <div className="flex items-center gap-1 bg-white/90 backdrop-blur-sm px-2 py-1 rounded-md">
            <MapPin className="h-4 w-4" />
            <span className="text-sm font-medium">{city.name}-{city.district}</span>
          </div>
        </div>
        {city.trending && (
          <div className="absolute top-3 right-3">
            <Badge variant="destructive" className="gap-1">
              <TrendingUp className="h-3 w-3" />
              인기 급상승
            </Badge>
          </div>
        )}
      </div>

      {/* Content Section */}
      <CardContent className="p-4">
        {/* Like/Dislike Buttons */}
        <div className="mb-3">
          <LikeDislikeButton
            likes={currentLikes}
            dislikes={currentDislikes}
            userVote={userVote}
            onLike={handleLike}
            onDislike={handleDislike}
          />
        </div>

        {/* Monthly Budget */}
        <div className="mb-4">
          <p className="text-sm text-muted-foreground mb-1">월 평균 생활비</p>
          <p className="font-semibold">{city.monthlyBudget}만원</p>
        </div>

        {/* Metrics */}
        <div className="space-y-3">
          {/* Cafe Density */}
          <div>
            <div className="flex items-center justify-between mb-1">
              <div className="flex items-center gap-1">
                <Coffee className="h-4 w-4 text-muted-foreground" />
                <span className="text-sm text-muted-foreground">카페 밀집도</span>
              </div>
              <span className="text-sm font-medium">{city.cafeDensity}%</span>
            </div>
            <Progress value={city.cafeDensity} className="h-2" />
          </div>

          {/* Internet Quality */}
          <div>
            <div className="flex items-center justify-between mb-1">
              <div className="flex items-center gap-1">
                <Wifi className="h-4 w-4 text-muted-foreground" />
                <span className="text-sm text-muted-foreground">인터넷 품질</span>
              </div>
              <span className="text-sm font-medium">{city.internetQuality}%</span>
            </div>
            <Progress value={city.internetQuality} className="h-2" />
          </div>
        </div>

        {/* Tags */}
        {city.tags && city.tags.length > 0 && (
          <div className="flex gap-2 mt-4">
            {city.tags.map((tag) => (
              <Badge key={tag} variant="secondary" className="text-xs">
                {tag}
              </Badge>
            ))}
          </div>
        )}
      </CardContent>
    </Card>
  )
}