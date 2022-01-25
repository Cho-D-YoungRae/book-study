# Chapter 6. 객체지향 프로그래밍 1

## 가변인자를 사용한 메서드는 오버로딩 하지 말자

구별되지 못 하는 경우가 발생하기 쉽다.

```java
class VarArgsEx {
	public static void main(String[] args) {
		String[] strArr = { "100", "200", "300" };
		
		System.out.println(concatenate("", "100", "200", "300"));
		System.out.println(concatenate("-", strArr));
		System.out.println(concatenate(",", new String[]{"1", "2", "3"}));
		System.out.println("["+concatenate(",", new String[0])+"]");
		System.out.println("["+concatenate(",")+"]");
	}

	static String concatenate(String delim, String... args) {
		String result = "";

		for(String str : args) {
			result += str + delim;
		}
		
		return result;
	}

/*  주석을 해제하면 오류가 발생한다.
	static String concatenate(String... args) {
		return concatenate("",args);
	}
*/
}
```

위 주석처리된 메서드를 주석 해제하면 `System.out.println(concatenate("", "100", "200", "300"))` 에서 `concatenate(String, String...)`, `concatenate(String...)` 중 구별하지 못해서 오류가 발생한다.

## 지역변수는 사용하기 전에 반드시 초기화해야 한다.

멤버변수는 초기화를 하지 않아도 자동적으로 변수의 자료형에 맞는 기본값(=0)으로 초기화가 이루어지므로 초기화 하지 않고 사용 가능

## 초기화 블럭(initialization block)

```java
class InitBlock {

	static {
		// 클래스 초기화 블럭
	}

	{
		// 인스턴스 초기화 블럭
	}

	public InitBlock() {     
		// 생성자
	}
}
```

## 멤버변수의 초기화 시기와 순서

- `클래스변수의 초기화 시점`: 클래스가 처음 로딩될 때 단 한번 초기화 된다.
- `인스턴스변수의 초기화 시점`: 인스턴스가 생성될 때마다 각 인스턴스별로 초기화가 이루어진다.
- `클래스변수의 초기화 순서`: 기본값 -> 명시적 초기화 -> 클래스 초기화 블럭
- `인스턴스변수의 초기화 순서`: 기본값 -> 명시적 초기화 -> 인스턴스 초기화 블럭 -> 생성자

> 명시적 초기회: 인스턴스 변수에 직접 초기화