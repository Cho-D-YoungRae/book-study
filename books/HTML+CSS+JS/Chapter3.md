# Chapter 3. 실무에서 자주 사용하는 HTML 필수

## Note

### `<hn>`

- h: heading, n: 중요도
- 검색 엔진에서 키워드로 인식

### `<P>`

- 문단을 작성할 때 사용

### `<br>`

- 문단에서 줄 바꿈할 때 사용

### `<blockquote>`

- 출처로부터 인용한 문단 단위의 텍스트를 작성할 때 사용
- 출처가 확실한 인용문은 cite 속성으로 출처 경로를 명시
- 반드시 한 개 이상의 `<p>` 를 포함해야 한다.
- `<p>` 내용에 포함될 수 없다

### `<q>`

- 택스트 단위의 짧은 인용문

### `<ins>` `<del>`

- `<ins><`ins>추가 텍스트</>`</ins>`
- `<del><`del>삭제 텍스트</>`</del>`

### `<sub>` `<sup>`

- \<sub>아래 첨자\</sub>
- \<sup>위 첨주\</sup>

### `<div>`

- 블랙 요소와 인라인 요소를 그룹으로 묶을 때 사용

### `<span>`

- 인라인 요소를 그룹으로 묶을 때 사용

### `<ul>` `<ol>`

- `<ul>:` unordered list
- `<ol>:` ordered list
- `<li>` 로 구성

### `<dl>`

- `<dl>:` definition list
- `<dt>:` definition term, \<dd>: definition description 으로 구성

### `<a>`

- HTML에서 내부나 외부 링크를 생성합니다.
- \<a href="대상 경로" target="링크 연결 방식" title="링크 설명"></a>
- `href` 대상 경로의 URL이거나 내부 문서의 id 속성값. 개발 초기 단계에서는 연결해야하는 대상의 주소가 불분명한 경우 "#"를 넣으면 된다.
- `target`: 링크를 생성할 때 대상이 연결되는 방식을 지정. 생략가능
- `title`: 링크를 설명할 수 있는 텍스트. 생략가능

### `<img>`

- HTML에서 그림과 같은 이미지 객체를 삽입하고 싶을 때
- \<img src="이미지 경로" alt="이미지 설명">

### `<strong>` `<em>`

- 텍스트의 의미를 강조하고 싶을 때
- \<strong><strong>강조하고 싶은 텍스트</strong>\</strong>
- \<em><em>강조하고 싶은 텍스트</em>\</em>

### `<form>`

- 폼의 시작을 의미하는 태그
- 폼을 구성하는 태그는 모두 이 안에 작성
- \<form action="서버 url" method="get or post">\</form>

### `<input>`

- 입력받는 요소를 생성할 때
- \<input type="종류" name="이름" value="초깃값">
- `type` 필수. `name`, `value` 선택

### `<label>`

- `<form>` 태그 안에서 사용하는 상호작용 요소에 이름을 붙일 때 사용
- `for` 속성을 사용한다면 상호작용 요소의 `id` 속성을 같은 값으로 설정

### `<fieldset>` `<legend>`

- `<fieldset>`: `<form>` 안에 사용된 다양한 상호작용 요소를 그룹 짓는다.
- `<legend>`: 그룹 지은 요소들을 이름 붙일 수 있다.

### `<textarea>`

- 여러 줄의 입력 요소를 생성할 떄는 `<input>` 가 아닌 `<textarea>` 사용합니다.
- `<textarea>`초깃값`<textarea>`

### `<select>` `<option>` `<optgroup>`

- `<select>`: 콤보박스 생성
- `<option>`: 콤보박스에 항목 추가
- `<optgroup>`: 항목들을 그룹으로 묶음

### `<button>`

- `<button type="종류">`버튼 내용`</button>`

## 좋은 점

자주 사용되는 태그들을 자세하게 정리해줘서 나중에도 참조하기 좋을 것 같다.

## 오탈자

- "이때 출처가 확실한 인용문은..." -> "이때 출처가 확실한 인용문은..." (44pg 맨 윗 줄.)