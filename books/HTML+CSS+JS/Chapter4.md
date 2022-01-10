# Chapter 4. 웹 스타일링을 위한 CSS 기초 배우기

## Note

### CSS 문법

- 선택자 { 속성: 값; }

### CSS 적용 3가지 방법

```html
<style>
    /* css code */
</style>
```

- 내부 스타일 시트(internal style sheet) 는 HTML 파일 내부에 CSS 작성하는 방법.
- 정해진 기준은 없지만 보통 `<head>` 안에 작성.
- HTML 문서를 해석할 때 첫 번쨰 줄부터 순차적으로 해석하기 때문에 `<body>`의 내용이 노출되기 전에 CSS를 불러와 적용될 수 있는 장점
- HTML 문설르 해석할 때마다 CSS 코드를 매번 다시 읽기 때문에 성능상으로 좋지 않다는 단점

```html
<link rel="stylesheet", href="css 파일 경로">
```

- **외부 스타일 시트(external style sheet)**
- 가장 많이 사용됨

```html
<태그 style="css 코드"></태그>
```
