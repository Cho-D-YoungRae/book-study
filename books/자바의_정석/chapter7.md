# Chapter 7. 객체지향 프로그래밍 2

## 생성자와 초기화 블럭은 상속되지 않는다. 멤버만 상속된다.

- 부모 클래스의 생성자는 `super()` 를 통해 호출 가능하다.

## abstract class without abstract method

```java
public abstract class WindowAdapter 
        implements WindowListener, WindowStateListener, WindowFocusListener {

    public void windowOpened(WindowEvent e) {}
    public void windowClosing(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    ...
}
```

클래스 자체로는 쓸모가 없지만, 다른 클래스가 이 클래스를 상속받아서 일부의 원하는 메서드만 오버라이딩 해도 된다.

만일 이 클래스가 없다면 아무런 내용도 없는 메서드를 잔뜩 오버라이딩 해야한다.

## 참조변수의 형변환

- 자손타입 -> 조상타입 (Up-casting) : 형변환 생략 가능
- 자손타입 <- 조상타입 (Down-casting) : 형변환 생략 불가

## 익명 클래스 (anonymous class)

```java
new 조상클래스이름()) {
    // 멤버 선언
}

new 구현인터페이스이름() {
    // 멤버 선언
}
```