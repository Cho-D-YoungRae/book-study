# Chapter 32. I/O 스트림

## 32-1 I/O 스트림에 대한 이해

가장 기본적인 데이터의 입출력 단위는 바이트

```java
public int read() throws IOException;
```

- `InputStream` 의 메서드
- 1바이트의 유효한 데이터에 3바이트의 0을 채워서 4바이트의 int 형 데이터로 반환
- 스트림의 끝에 도달해서 더 이상 읽어들일 데이터가 없는 경우 -1 반환

```java
public void write(int b) throws IOException;
```

- `OutputStream` 의 메서드
- int형 데이터의 첫 번째 바이트만을 파일에 저장

> 1바이트씩 읽고 쓰면 크기가 클 경우 속도가 느림

바이트 스트림이라 하여 1바이트씩만 읽고 써야하는 것은 아니고, byte 배열을 생성해서 이를 기반으로 많은 양의 데이터를 한 번에 읽고 쓰는 것도 가능

```java
public int read(byte[] b) throws IOException;
```

```java
public void write(byte[] b, int off, int len) throws IOException;
```

## 32-2 필터 스트림의 이해와 활용

> 필터 스트림: 입력 또는 출력 스트림에 덧붙여서 데이터를 조합, 가공 및 분리하는 역할을 한다.

자료형을 결정해서 데이터를 입력 및 출력하는 경우에는 그 순서를 지키는 것이 중요

필터 스트림 중에서 상대적으로 사용 빈도수가 높은 두 필터 스트림: `BufferedInputStream`, `BufferedOutputStream`

- `BufferedInputStream`: 입력 스트림에 버퍼를 달아주는 역할
  - 내부에 버퍼를 갖는다
  - 입력 스트림으로부터 많은 양의 데이터를 가져다 해당 버퍼를 채운다
  - 때문에 프로그래머가 read 메서드를 호출할 때 파일에 저장된 데이터를 반환하는 것이 아니라, 버퍼 스트림의 버퍼에 저장된 데이터를 반환
  - 메서드 호출의 빈도수보다 더 문제되는 것은 파일에 빈번히 접근하는 행위임
- `BufferedOutputStream`: 출력 스트림에 버퍼를 달아주는 역할
  - 입력 스트림과 마찬가지

> 버퍼링은 성능 향상에 도움을 주지만 버퍼 출력 스트림에서 다음과 같은 상황이 발생할 수 있음  
> "버퍼 스트림에 저장된 데이터가 파일에 저장되지 않은 상태에서 컴퓨터 다운"
> 
> 이때 프로그램상에서는 write 메서드 호출을 통해 데이터를 저장했음에도 불구하고 실제 파일에는 데이터가 저장되지 않는 일이 발생할 수 있음.  
> 떄문에 버퍼가 차지 않아도 파일에 저장해야 할 중요한 데이터가 있다면, 다음 메서드 호출을 통해서 명시적으로 버퍼를 비우도록 할 수 있음(flush 메서드)
> 
> 그러나 이 메서드를 빈번히 호출하는 것은 버퍼링을 통한 성능 향상에 방해가 되는 일이기 때문에 제한적으로 호출하는 것이 좋음
> 
> 스트림이 종료되면 버퍼는 자동으로 비워짐(스트림을 종료하기 직전에 굳이 이 메서드를 호출할 필요는 없음)

## 32-3 문자 스트림의 이해와 활용

> 문자 스트림: 문자가 갖는 특성을 고려하여 입출력이 진행되는 것

- 자바는 모든 문자를 유니코드를 기준으로 표현
- 운영체제의 문자 표현방식(인코딩방식)이 자바와 다름
- 문자 스트림을 사용하여 운영체제의 문자 표현방식에 맞춰서 유니코드로 표현된 문자를 알아서 변환해서 저장

### FileReader & FileWriter

- 바이트 스트림의 생성과 관련된 클래스가 상속하는 클래스: `InputStream`, `OutputStream`
- 이에 대응하여 문자 스트림의 생성과 관련된 클래스가 삭송하는 클래스: `Reader`, `Writer`
- 파일을 대상으로하는 바이트 입출력 스트림을 생성하는 클래스: `FileInputStream`, `FileOutputStream`
- 이에 대응하여 파일을 대상으로 하는 문자 입출력 스트림을 생성하는 클래스: `FileReader`, `FileWriter`

```java
public int read() throws IOException;
```

이 메서드의 반환형이 char 가 아닌 int 인 이유는 반환할 문자가 없을 때 -1 을 반환하기 위함

### BufferedReader & BufferedWriter

- 바이트 스트림에는 필터 스트림을 연결할 수 있었다. 그리고 앞서 소개한 대표적인 필터 스트림 둘은: `BufferedInputStream`, `BufferedOutputStream`
- 문자 스트림에도 필터 스트림을 연결할 수 있다: `BufferedReader`, `BufferedWriter`

```java
public String readLine() throws IOException;    // BufferedReader
```

- 문자열 반환, 반환할 문자열 없으면 null 반환

```java
public void write(String s, int off, int len) throws IOException;    // BufferedWriter
```

- 문자열 s 를 인덱스 off 에서부터 len 개의 문자까지 저장

운영체제별로 줄 바꿈을 표시하는 방법(문자)에는 차이가 있다. 그런데 아래 메서드를 호출하면 해당 운영체제에서 정의하고 있는 줄 바꿈 표시 문자를 삽입해준다.

```java
public void newLine() throws IOException;    // BufferedWriter
```

## 34-4 IO 스트림 기반의 인스턴스 저장

> 바이트 스트림을 통해서 인스턴스를 통째로 저장하고 꺼내는 것도 가능하다
> 
> 객체 직렬화(Object Serialization): 인스턴스를 통째로 저장하는 것
> 
> 객체 역직렬화(Object Deserialization): 저장된 인스턴스를 다시 꺼내는 것

인스턴스의 저장은 당연히 바이트 스트림을 통해서 이뤄진다. 바이트 기반의 기본 입출력 스트림에 다음 스트림을 연결하면 인스턴스를 통째로 입력하고 출력할 수 있다.

- `ObjectInputStream`
- `ObjectOutputStream`

위의 두 스트림은 사용방법이 필터 스트림과 유사하다. 그러나 이들은 필터 스트림으로 구분하지 않는다. 이유는 필터 스트림이 상속하는 다음 두 클래스를 상속하지 않기 때문이다.

- `FilterInputStream`
- `FilterOutputStream`

입쳘력의 대상이 되는 인스턴스의 클래스는 `java.io.Serializable` 을 구현해야 한다.

`Serializable` 은 마커 인터페이스로 직렬화 가능함을 표시하는 인터페이스이다. 따라서 구현해야할 메서드는 없다.

인스턴스를 저장하면 인스턴스 변수가 참조하는 인스턴스까지 함께 저장이 된다.

- 단, 함께 저장되려면 해당 인스턴스의 클래스도 `Serializable` 을 구현하고 있어야 한다.
- 그런데 만약에 참조변수가 참조하는 인스턴스의 저장을 원치 않는다면 `transient` 선언을 추가
