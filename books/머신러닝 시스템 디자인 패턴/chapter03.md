# Chapter 03. 모델 릴리스하기

## 3.1 학습환경과 추론환경

- 학습환경에서는 풍부한 컴퓨팅 자원과 학습용 라이브러리를 함께 활용해 모델을 개발
- 추론환경에서는 한정된 자원으로 추론용 라이브러리를 통해 추론을 수행

### 3.1.2 학습환경과 추론환경

- 학습 페이즈
  - 일시적으로 초기 비용이 컴퓨팅 자원에 투자되는 시점이기도 함
  - 실험에 목적을 둔 페이즈이기도 하기 때문에 코드 리뷰나 단위 테스트, 리팩터링은 사실상 거의 이루어지지 않음
  - 코드를 라인 단위로 실행하고, 실험 결과를 검증하면서 개발해 나가는 방식
- 추론 페이즈
  - 모델과 추론 코드가 실제 시스템에 포함되어 다른 시스템과 연계 가동되고 운용
  - 프로덕트에 따라서는 사용자가 직접 사용해서 사용자 경험이나 비즈니스를 좌우하는 기능이 머신러닝 모델을 포함하기도 함
  - 머신러닝 모델 또는 추론기가 어떤 원인에 의해 장애가 발생해 정지하거나 잘못된 추론을 하는 경우, 비즈니스에 손실이 생길 우려가 있으므로 장애나 문제가 발생하면 이를 감지하고 트러블슈팅을 통해 즉시 복구해야 함
  - 비즈니스가 지속되는 한 끊김 없이 가동되어야 함
    - 부하에 따라 오토 스케일하는 설계가 필요
  - 추론 모델이 시스템에 포함되어 상시 가동 중일 것이므로 테스트가 끝난 코드를 런타임으로 실행하게 됨
- 학습환경과 추론환경에서 사용하는 라이브러리나 툴 역시 다를 수 있음
  - 학습환경에서는 파이썬, 주피터 노트북을 주로 사용함
  - 추론환경에서는 주피터 노트북을 사용하는 경우는 드물고 추론 전용 라이브러리도 사용할 수 있음
- 학습환경과 출노환경에서 공통으로 사용하는 컴포넌트도 존재
- 학습과 추론에서 입출력 데이터의 타입이나 형태가 바뀌는 일은 없음

## 3.2 안티 패턴 (버전 불일치 패턴)

> 머신러닝 모델을 실제 시스템에 릴리스할 떄 빈번하게 발생하는 문제는 학습환경과 추론 환경의 OS 나 라이브러리 또는 프로그램의 불일치  

### 3.2.1 상황

- 학습환경과 추론환경에서 같은 라이브러리를 사용하고 있으나, 라이브러리의 버전이 일치하지 않는 경우
- 추론기로 모델을 불러올 수 없는 경우
- 추론기의 추론 결과가 학습환경에서 예상했던 추론 결과와 일치하지 않는 경우

### 3.2.2 구체적인 문제

- 버전에 따른 호환성 문제를 회피하기 위해 ONNX 를 사용하는 방법이 있음
- 학습환경과 추론환경에서 공통으로 사용되는 라이브러리는 그 버전까지 포함해서 공유하는 구조를 만들어 두는 것이 좋음

### 3.2.3 이점

- 라이브러리의 버전에 따른 호환성을 검증할 수 있음

### 3.2.4 과제

- 모델을 불러올 수 없음
- 모델을 불러올 수 있지만, 추론 결과가 학습할 떄와 다름

### 3.2.5 해결방법

- 학습환경에서 사용한 라이브러리와 버전을 출력해서 추론기의 개발에 공유하는 구조나 워크플로를 구성함

## 3.4 모델-인-이미지 패턴

> 모델-인-이미지(model-in-image)패턴은 추론기

### 3.4.1 유스케이스

- 서버 이미지와 추론 모델의 버전을 일치시키고 싶은 경우
- 추론 모델에 개별 서버 이미지를 준비하는 경우

### 3.4.2 해결하려는 과제

- 릴리스 이전에 추론기를 가동시켜 테스트를 실시하면 추론기의 정상 가동 여부를 확인할 수 있음
- 추론기 서버와 모델은 대부분 서로 다른 페이즈에서 개발하므로, 서버나 모델의 수가 늘어남에 따라 테스트해야 하는 가짓수가 많아지기 때문에 서버와 모델의 모든 조합을 검증하는 것은 불가능
- 모델-인-이미지 패턴에서는 모델을 포함한 서버를 빌드함으로써 오직 빌트-인 모델만들 가동시키는 서버를 구축
- 서버와 모델의 버전을 일치시킬 수 있기 때문에 정상적인 가동이 가능한 서버를 모델과 일대일로 정리할 수 있는 장점

### 3.4.3 아키텍처

- 추론 서버의 이미지에 학습이 끝난 모델을 포함시키기 때문에 학습과 서버 이미지의 구축을 일련의 워크플로로 만들 수 있음
  - 서버 이미지와 모델 파일의 버전을 동일하게 관리할 수 있기 때문에 추론기에 설치된 라이브러리의 버전에 따라 가동 가능한 모델을 선정할 필요가 없어짐
- 학습을 통해 모델을 생성한 후 모델을 포함한 추론용 서버 이미지를 빌드하고, 추론기를 준비할 때는 서버 이미지를 풀(Pull)한 뒤 가동시킴
- 추론용 서버 이미지를 빌드하는 데 소요되는 시간이 길고, 용량이 증가한다는 **단점**
- 서버 이미지의 구축은 모델의 학습이 완료된 이후 이뤄지기 떄문에 전 과정을 아울러 구축을 완료하는 파이프라인이 필요
- 서버 이미지의 용량이 증가함에 따라 이미지를 풀(Pull)하고 시스템이 가동될 때까지 소요 시간이 길어질 수 있음
- 모델-인-이미지 패턴으로 모델 파일을 포함해서 빌드하더라도 원래 모델 파일을 서버 이미지와는 별도로 저장해 두는 것을 권장
  - 서버의 빌드에 실패했을 때 모델 파일이 저장되어 있지 않으면 적지 않은 과정을 처음부터 다시 시작해야 하는 사태가 발생

### 3.4.5 이점

- 가동 확인이 끝난 서버와 모델의 편성을 하나의 추론용 서버 이미지로 관리가 가능함
- 서버와 모델을 일대일 대응으로 관리할 수 있어 웅용상 간편함

### 3.4.6 검토사항

- 학습한 모델의 수만큼 서버 이미지의 수도 늘어나는 구조
  - 스토리지 용량도 점차 증가
  - 불필요한 이미지를 삭제하는 것이 좋음
- 서버 이미지 자체도 모델을 포함하고 있는 만큼 그 사이즈는 커지게 되고, 추론기를 가동하기 위해 서버 이미지를 다운로드 받는 소요 시간도 길어짐
  - 추론기의 가동과 스케일 아웃의 소요시간이 길어질 수 있음

## 3.5 모델 로드 패턴

### 3.5.1 유스케이스

- 서버 이미지 버전보다 추론 모델의 버전을 더 빈번하게 갱신하는 경우
- 동일한 서버 이미지로 여러 종류의 추론 모델 가동이 가능한 경우

### 3.5.2 해결하려는 과제

- 모델-인-이미지 패턴
  - 서버 이미지와 모델의 버전을 일치시킬 수 있다는 장점
  - 서버 이미지의 빌드가 빈번하게 발생하고 서버 이미지의 사이즈가 증가한다는 단점
  - 모델마다 하나의 서버 이미지를 준비해야 하는 모델에 특화된 추론기를 만드는 용도라면 모델-인-이미지 패턴은 합리적인 선택일 수 있음
- 동일한 베이스 이미지로 모델의 버전을 자주 변경하게 되면 모델-인-이미지 패턴은 번거로운 운용을 맞이
  - 동일한 전처리 기법과 동일한 의사결정나무 모델을 다른 데이터셋으로 여러 번 학습하는 경우, 학습할 때마다 서버 이미지를 빌드하는 것은 운용면에서 합리적이라고 볼 수 없음
  - 이미 범용적인 파라미터가 선정되어 있을 때는 데이터셋만 지속적으로 바꾸어 가며 새로운 모델을 생성하는 것이 바람직함

### 3.5.3 아키텍처

- 서버 이미지의 구축(및 라이브러리의 버저닝)과 모델의 학습이 자연스럽게 분리
- 추론 서버의 이미지 구축고 모델의 저장이 따로 이뤄지므로 서버 이미지를 경량화할 수 있음
- 서버 이미지의 범용성을 높여 동일한 이미지를 여러 개의 추론 모델에 응용할 수 있음
- 추론기를 배치할 때 서버 이미지를 풀(Pull)하고 난 뒤 추론기를 기동하고, 이후에 모델 파일을 취득해서 추론기를 본격적으로 가동하므로 환경변수 등으로 추론 서버에서 가동하는 모델을 유연하게 변경할 수 있음
- 모델이 라이브러리의 버전에 의존적일 경우 서버 이미지의 버전 관리와 모델 파일의 버전 관리(지원하는 라이브러리의 버전 관리)를 별도로 수행해야 한다는 **단점**

### 3.5.4 구현

- 모델 파일을 도커 이미지에 포함시키지 않고, 도커 컨테이너를 기동할 떄 모델을 다운로드하는 구성
- 모델 파일은 AWS S3, GCP Storage 와 같ㅇ느 스토리지에 저장해두고, 필요할 때 다운로드할 수 있게 해두는 것이 좋음
- 모델 파일의 다운로드는 쿠버네티의 `initContainer` 라는 기능을 사용
  - 컨테이너를 기동하기 전에 필요한 초기화를 실행할 수 있음

### 3.5.5 이점

- 서버 이미지의 버전과 모델 파일의 버전이 분리 가능함
- 서버 이미지의 응용성이 향상됨
- 서버 이미지가 가벼워짐

### 3.5.6 검토사항

- 서버 이미지와 모델의 버전 불일치를 해결하는 구조가 필요하다는 점
