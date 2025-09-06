# Supabase 이메일 템플릿 설정 가이드

Supabase 대시보드에서 다음 이메일 템플릿을 설정해야 합니다:

## 1. 회원가입 확인 이메일 템플릿

Supabase 대시보드 > Authentication > Email Templates > Confirm signup에서 설정:

```html
<h2>회원가입을 확인하세요</h2>

<p>아래 링크를 클릭하여 이메일을 확인해주세요:</p>
<p>
  <a href="{{ .SiteURL }}/auth/confirm?token_hash={{ .TokenHash }}&type=email&next={{ .RedirectTo }}">
    이메일 확인하기
  </a>
</p>
```

## 2. 비밀번호 재설정 이메일 템플릿

Supabase 대시보드 > Authentication > Email Templates > Reset Password에서 설정:

```html
<h2>비밀번호 재설정</h2>

<p>아래 링크를 클릭하여 비밀번호를 재설정하세요:</p>
<p>
  <a href="{{ .SiteURL }}/auth/confirm?token_hash={{ .TokenHash }}&type=recovery&next={{ .RedirectTo }}">
    비밀번호 재설정하기
  </a>
</p>
```

## 3. 이메일 변경 확인 템플릿

Supabase 대시보드 > Authentication > Email Templates > Change Email Address에서 설정:

```html
<h2>이메일 변경 확인</h2>

<p>아래 링크를 클릭하여 새로운 이메일 주소를 확인해주세요:</p>
<p>
  <a href="{{ .SiteURL }}/auth/confirm?token_hash={{ .TokenHash }}&type=email_change&next={{ .RedirectTo }}">
    이메일 변경 확인하기
  </a>
</p>
```

## 설정 시 주의사항

1. Site URL 설정: Supabase 대시보드 > Settings > Authentication에서 Site URL이 올바르게 설정되어 있는지 확인하세요.
2. Redirect URLs: 허용된 리다이렉트 URL 목록에 애플리케이션 URL이 포함되어 있는지 확인하세요.
3. SMTP 설정: 프로덕션 환경에서는 자체 SMTP 서버를 설정하는 것을 권장합니다.