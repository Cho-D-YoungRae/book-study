// Link import removed as it's no longer used in Phase 1
import { Facebook, Twitter, Instagram, Youtube, Mail, Phone } from 'lucide-react'

export default function Footer() {
  return (
    <footer className="bg-gray-900 text-gray-300">
      <div className="container mx-auto px-4 py-12">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          {/* Company Info */}
          <div>
            <div className="flex items-center space-x-2 mb-4">
              <span className="text-2xl">🏙</span>
              <span className="font-bold text-xl text-white">NOMAD KOREA</span>
            </div>
            <p className="text-sm mb-4">
              한국에서 노마드하기 좋은 도시를 찾고,
              <br />
              같은 라이프스타일을 가진 사람들과
              <br />
              연결해드립니다.
            </p>
            <div className="flex space-x-4">
              <a href="#" className="hover:text-white transition-colors">
                <Facebook className="h-5 w-5" />
              </a>
              <a href="#" className="hover:text-white transition-colors">
                <Twitter className="h-5 w-5" />
              </a>
              <a href="#" className="hover:text-white transition-colors">
                <Instagram className="h-5 w-5" />
              </a>
              <a href="#" className="hover:text-white transition-colors">
                <Youtube className="h-5 w-5" />
              </a>
            </div>
          </div>

          {/* Simplified for Phase 1 - Removed unnecessary navigation links */}

          {/* Contact */}
          <div>
            <h3 className="text-white font-semibold mb-4">연락처</h3>
            <div className="space-y-3">
              <div className="flex items-center gap-3">
                <Mail className="h-4 w-4" />
                <a href="mailto:contact@nomadkorea.com" className="text-sm hover:text-white transition-colors">
                  contact@nomadkorea.com
                </a>
              </div>
              <div className="flex items-center gap-3">
                <Phone className="h-4 w-4" />
                <span className="text-sm">02-1234-5678</span>
              </div>
              <div className="text-sm mt-4">
                <p>월-금: 10:00 - 18:00</p>
                <p className="text-xs text-gray-500">주말 및 공휴일 휴무</p>
              </div>
            </div>
          </div>
        </div>

        {/* Bottom Bar */}
        <div className="mt-12 pt-8 border-t border-gray-800">
          <div className="flex flex-col md:flex-row justify-between items-center gap-4">
            <p className="text-sm text-gray-500">
              © 2024 NOMAD KOREA. All rights reserved.
            </p>
            <div className="flex items-center gap-6">
              {/* Bottom links removed for Phase 1 simplification */}
            </div>
          </div>
        </div>
      </div>
    </footer>
  )
}