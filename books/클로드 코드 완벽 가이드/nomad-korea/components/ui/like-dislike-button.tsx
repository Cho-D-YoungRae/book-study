'use client'

import { ThumbsUp, ThumbsDown } from 'lucide-react'
import { cn } from '@/lib/utils'

interface LikeDislikeButtonProps {
  likes: number
  dislikes: number
  userVote?: 'like' | 'dislike' | null
  onLike: () => void
  onDislike: () => void
}

export default function LikeDislikeButton({
  likes,
  dislikes,
  userVote,
  onLike,
  onDislike
}: LikeDislikeButtonProps) {
  return (
    <div className="flex items-center gap-4">
      <button
        onClick={onLike}
        className={cn(
          "flex items-center gap-1 px-2 py-1 rounded-md transition-colors hover:bg-blue-50",
          userVote === 'like' ? "text-blue-500" : "text-muted-foreground"
        )}
      >
        <ThumbsUp 
          className={cn(
            "h-4 w-4",
            userVote === 'like' && "fill-current"
          )} 
        />
        <span className="text-sm font-medium">{likes}</span>
      </button>
      
      <button
        onClick={onDislike}
        className={cn(
          "flex items-center gap-1 px-2 py-1 rounded-md transition-colors hover:bg-red-50",
          userVote === 'dislike' ? "text-red-500" : "text-muted-foreground"
        )}
      >
        <ThumbsDown 
          className={cn(
            "h-4 w-4",
            userVote === 'dislike' && "fill-current"
          )} 
        />
        <span className="text-sm font-medium">{dislikes}</span>
      </button>
    </div>
  )
}