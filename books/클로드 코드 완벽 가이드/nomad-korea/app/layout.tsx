import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import Header from "@/components/layout/Header";
import Footer from "@/components/layout/Footer";

const inter = Inter({
  subsets: ["latin"],
  display: "swap",
});

export const metadata: Metadata = {
  title: "NOMAD KOREA - 한국에서 노마드하기 좋은 도시 찾기",
  description: "한국 도시들의 노마드 친화도를 한눈에 비교하고, 실제 경험 기반 정보를 통해 최적의 도시를 찾을 수 있는 플랫폼",
  keywords: ["노마드", "디지털노마드", "원격근무", "한달살기", "워케이션"],
  openGraph: {
    title: "NOMAD KOREA",
    description: "한국에서 노마드하기 좋은 도시를 찾아보세요",
    type: "website",
    locale: "ko_KR",
  },
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="ko">
      <body className={`${inter.className} antialiased`}>
        <div className="min-h-screen flex flex-col">
          <Header />
          <main className="flex-1">{children}</main>
          <Footer />
        </div>
      </body>
    </html>
  );
}
