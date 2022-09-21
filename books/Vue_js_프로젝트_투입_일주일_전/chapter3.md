# Vue CLI로 Vue 프로젝트 생성하기

## 3.1 Vue VLI 설치

- npm install
  - `-g` 옵션: 패키지가 현재 디렉토리뿐만 아니라 모든 프로젝트에서 사용할 수 있는 global 패키지로 등록
  - `--save` 옵션: 현재 작업 중인 디렉토리 내에 있는 ./node_modules에 패키지 설치, package.json 파일에 있는 dependencies 객체에 지금 설치한 패키지 정보를 추가
    - 팀원들과 공동 작업을 하고 있다면 매번 패키지 파일 전체를 공유하는 일이 자원 낭비가 될 수 있으므로 패키지를 설치할 때 해당 옵션 사용
  - npm install 을 입력하여 현재 내 프로젝트 디렉토리에 없는 패키지 전체를 한번에 설치할 수 있음. 이 방식을 이용하면 깃허브 같은 코드 레파지토리에 패키지 파일을 업로드 할 필요 없음

## 3.2 Default 옵션으로 프로젝트 설치하기

```bash
vue create {프로젝트명}
```

### 3.2.3 Vue 프로젝트 파일 구조

- `node_moduls`: npm으로 설치된 패키지 파일들이 모여있는 디렉토리
- `public`: : 웹팩(webpack)을 통해 관리되지 않는 정적 리소스가 모여 있는 디렉토리
- `src/assets`: 이미지, css, 폰트 등을 관리하는 디렉토리
- `src/components`: Vue 컴포넌트 파일이 모여있는 디렉토리
- `App.vue`: 취상위(Root) 컴포넌트
- `main.js`: 가장 먼저 실행되는 자바스크립트 파일로써, Vue 인스턴스를 생성하는 역할
- `package-lock.json`: 설치된 package의 dependency 정보를 관리하는 파일
- `package.json`: 프로젝트에 필요한 package를 정의하고 관리하는 파일

## 3.4 Vue 프로젝트 매니저로 프로젝트 설치

### 3.4.1 Vue 프로젝트 매니저 실행

```bash
vue ui
```
