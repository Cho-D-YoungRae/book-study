import Link from 'next/link'
import { Button } from '@/components/ui/button'
import { Search, Menu, User } from 'lucide-react'

export default function Header() {
  return (
    <header className="sticky top-0 z-50 w-full border-b bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60">
      <div className="container mx-auto px-4">
        <div className="flex h-16 items-center justify-between">
          {/* Logo */}
          <Link href="/" className="flex items-center space-x-2">
            <span className="text-2xl">🏙</span>
            <span className="font-bold text-xl">NOMAD KOREA</span>
          </Link>

          {/* Desktop Navigation - Simplified for Phase 1 */}
          <nav className="hidden lg:flex items-center space-x-8">
            {/* Navigation items removed - keeping only authentication related features */}
          </nav>

          {/* Right Section */}
          <div className="flex items-center space-x-4">
            <Button variant="ghost" size="icon" className="hidden sm:flex">
              <Search className="h-5 w-5" />
              <span className="sr-only">검색</span>
            </Button>
            
            <Button variant="ghost" className="hidden sm:flex" size="sm">
              <User className="h-4 w-4 mr-2" />
              로그인
            </Button>

            {/* Mobile Menu Button */}
            <Button variant="ghost" size="icon" className="lg:hidden">
              <Menu className="h-5 w-5" />
              <span className="sr-only">메뉴</span>
            </Button>
          </div>
        </div>
      </div>
    </header>
  )
}