# Chapter 5. CSS 선택자 다루기

## Note

### 전체 선택자

``` css
* {
    /* CSS code */
}
```

### 태그 선택자

```css
태그명 {
    /* CSS code */
}
```

### 아이디 선택자

```css
#id속성값 {
    /* CSS code */
}
```

### 클래스 선택자

```css
.class속성값 {
    /* CSS code */
}
```

### 기본 속성 선택자

```css
[속성] {
    /* CSS code */
}

[속성=값] {
    /* CSS code */
}
```

### 그룹 선택자

``` css
선택자1, 선택자2, ... {
    /* CSS code */
}
```

- 여러 선택자를 하나로 그룹 지을 때 사용

### 자식 선택자

``` css
부모 선택자 > 자식 선택자 {
    /* CSS code */
}
```

- 부모 요소의 하위에 있는 자식 요소에 스타일을 적용할 때 사용

### 하위 선택자

``` css
선택자1 선택자2 ... {
    /* CSS code */
}
```

- 선택자의 범위를 특정 부모 요소의 자손 요소로 한정

### 인접 형제 선택자

``` css
이전 선택자 + 대상 선택자 {
    /* CSS code */
}
```

- 앞에서 지정한 선택자 요소 바로 다음에 있는 형제 관계 요소를 선택자로 지정

### 일반 형제 선택자

``` css
이전 선택자 ~ 대상 선택자 {
    /* CSS code */
}
```

- 이전 선택자 뒤에 오는 형제 관계 요소를 모두 선택자로 지정

### 가상 요소 선택자

``` css
기준 선택자::가상 요소 선택자 {
    /* CSS code */
}
```

- HTML 문서에 명시적으로 작성된 구성 요소는 아니지만, 마치 존재하는 것처럼 취급해 선택하는 선택자 지정 방법

|종료|설명|
|---|---|
|`::before`|콘텐츠 앞의 공간을 선택|
|`::after`|콘텐츠 뒤의 공간을 선택|

> [전체 가상 요소 선택자](https://developer.mozilla.org/ko/docs/Web/CSS/Pseudo-elements)

### 가상 클래스 선택자

``` css
기준 요소:가상 클래스 선택자 {
    /* CSS code */
}
```

- 요소의 상태를 이용해 선택자를 지정하는 방법

#### 링크 가상 클래스 선택자

|종료|설명|
|---|---|
|`:link`|한 번도 방문하지 않은 링크일 때 선택합니다.|
|`:visited`|한 번이라도 방문한 적이 있는 링크일 때 선택합니다.|

#### 동적 가상 클래스 선택자

|종료|설명|
|---|---|
|`:hover`|요소에 마우스를 올리면 해당 태그가 선택자로 지정됩니다.|
|`:active`|요소에 마우스를 클릭하면 해당 태그가 선택자로 지정됩니다.|

#### 입력 요소 가상 클래스 선택자

|종료|설명|
|---|---|
|`:focus`|입력 요소의 커서가 활성화되면|
|`:checked`|체크박스가 표시되어 있으면|
|`:disabled`|상호 작용 요소가 disabled 되면|
|`:enabled`|상호 작용 요소가 enabled(disabled 사용 X) 되면|

#### 구조적 가상 클래스

|종료|설명|
|---|---|
|`E:first-child`|E 요소의 첫 번째 자식 요소|
|`E:last-child`|E 요소의 마지막 자식 요소|
|`E:nth-child(n)`|E 요소가 부모 요소의 자식 요소 중 n번째 순서가 맞으면|
|`E:nth-last-child(n)`|E 요소가 부모 요소의 자식 요소 중 마지막 에서 n번째 순서가 맞으면|
|`E:nth-of-type(n)`|부모 요소의 자식 요소 중 n번째로 등장하는 E 요소|
|`E:nth-last-of-type(n)`|부모 요소의 자식 요소 중 마지막에서 n번째로 등장하는 E 요소|
|`E:fist-of-type`|부모 요소의 자식 요소 중 첫 번째로 등장하는 E|
|`E:last-of-type`|부모 요소의 자식 요소 중 마지막으로 등장하는 E|

## 오탈자

- 151pg attribute_selector_2.html 의 가장 아래 부분에 "새창으로 열리는 a 태그" -> "새 창으로 열리는 a 태그"
  - 조금 헷갈리는데 혹시나 해서 말씀드려봅니다!
