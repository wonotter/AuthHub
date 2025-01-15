# **Spring Security 기반 권한 인증 및 접근 제어 라이브러리 구현 가이드**

## **개요**

Spring Security는 Java 애플리케이션의 보안을 담당하는 강력한 프레임워크로, 인증, 권한 부여, 일반적인 공격에 대한 방어 기능을 제공합니다. Spring 기반 애플리케이션을 보호하는 데 널리 사용되는 Spring Security는 명령형 애플리케이션뿐만 아니라 Spring Security 6.0부터는 리액티브 애플리케이션까지 완벽하게 지원합니다1. 이 가이드에서는 Spring Security의 주요 기능과 활용 방법을 소개하여, Spring Security를 기반으로 권한 인증 및 접근 제어 라이브러리를 구현하는 데 필요한 정보를 제공합니다.

## **Spring Security 주요 기능**

Spring Security는 애플리케이션 보안을 위한 다양한 기능을 제공합니다. 핵심 기능은 다음과 같습니다3.

* **인증(Authentication)**: 특정 리소스에 접근하려는 주체(Principal)의 신원을 확인하는 과정입니다4. 사용자는 일반적으로 아이디와 비밀번호를 통해 인증됩니다. Spring Security는 다양한 인증 방식을 지원하며, 폼 로그인, OAuth 2.0, JWT 등을 사용할 수 있습니다1.  
* **권한 부여(Authorization)**: 인증된 사용자에게 허용된 리소스와 작업을 결정하는 과정입니다. 사용자의 역할과 권한에 따라 시스템의 특정 영역에 대한 접근을 허용하거나 제한합니다5.  
  * **Access Decision Manager**: 사용자의 요청과 사용자의 권한 정보를 비교하여, 해당 사용자가 요청한 리소스에 접근할 수 있는지 여부를 결정합니다5.  
  * **Filter Security Interceptor**: 스프링 시큐리티 필터 체인의 일부로, 사용자의 요청을 가로채 권한을 검사합니다. 사용자에게 요청된 리소스에 대한 접근 권한이 있는지 확인하고, 권한이 없을 경우 접근을 거부합니다5.  
* **공격 방어(Protection against common attacks)**: CSRF(Cross-Site Request Forgery), XSS(Cross-Site Scripting) 등과 같은 일반적인 웹 공격으로부터 애플리케이션을 보호합니다1.  
  * **CSRF 방어**: Spring Security는 CSRF 공격으로부터 웹 애플리케이션을 보호하기 위한 기능을 내장하고 있습니다. CSRF 공격은 웹 사이트에 대한 사용자의 인증 정보를 악용하여 사용자가 의도하지 않은 작업을 수행하도록 하는 공격입니다. Spring Security는 CSRF 토큰을 사용하여 이러한 공격을 방지합니다.  
  * **HTTP 보안 헤더**: Spring Security는 HTTP 응답 헤더를 통해 보안을 강화할 수 있는 기능을 제공합니다. 예를 들어, X-Frame-Options 헤더를 사용하여 클릭재킹 공격을 방지하고, X-XSS-Protection 헤더를 사용하여 XSS 공격을 방지할 수 있습니다.  
  * **암호 저장**: Spring Security는 안전한 암호 저장을 위한 기능을 제공합니다. 암호를 평문으로 저장하는 대신, 해싱 알고리즘을 사용하여 암호를 암호화하여 저장합니다. Spring Security는 bcrypt, PBKDF2, scrypt 등 다양한 해싱 알고리즘을 지원합니다.  
* **AuthenticationTrustResolver**: AuthenticationTrustResolver는 Authentication 객체가 신뢰할 수 있는 인증인지 여부를 판별하는 데 사용됩니다. 예를 들어, 익명 인증이나 리멤버 미 인증은 신뢰할 수 없는 인증으로 간주될 수 있습니다6.  
* **SecurityContext**: SecurityContext는 현재 인증된 사용자의 Authentication 객체를 저장하는 데 사용됩니다. SecurityContextHolder를 통해 SecurityContext에 접근하고 현재 사용자의 인증 정보를 가져올 수 있습니다4.

## **Integrations with Other Security Mechanisms**

Spring Security는 LDAP(Lightweight Directory Access Protocol)와 같은 다른 보안 메커니즘과 통합될 수 있습니다. LDAP는 사용자 정보와 권한을 중앙 집중식으로 관리하는 데 사용되는 디렉토리 서비스입니다. Spring Security는 LDAP 서버를 인증 및 권한 부여에 사용할 수 있도록 지원합니다1.

## **Authorization Changes in Spring Security 6**

Spring Security 6에서는 권한 부여 모델에 몇 가지 중요한 변경 사항이 도입되었습니다. 이러한 변경 사항은 권한 부여 규칙을 보다 세밀하게 제어하고, 보안 취약성을 줄이는 데 도움이 됩니다1. 자세한 내용은 Spring Security 공식 문서를 참조하십시오.

## **Spring Security 권한 인증 방식**

Spring Security는 다양한 인증 방식을 지원합니다. 몇 가지 주요 인증 방식과 장단점은 다음과 같습니다3.

| 인증 방식 | 설명 | 장점 | 단점 |
| :---- | :---- | :---- | :---- |
| 폼 로그인 | 사용자가 웹 페이지에서 아이디와 비밀번호를 입력하여 인증하는 방식 | 구현이 간단 | 보안에 취약할 수 있음 |
| OAuth 2.0 | 제3자 서비스(예: Google, Facebook)를 통해 사용자를 인증하는 방식 | 단일 로그인(SSO) 지원, 사용자 편의성 증대 | 구현이 복잡할 수 있음 |
| JWT (JSON Web Token) | 토큰 기반 인증 방식으로, 서버에서 발급한 토큰을 통해 사용자를 인증 | 상태 비저장, 확장성 용이 | 토큰 만료 및 무효화 관리 필요 |

**인증 방식 선택 가이드:**

* **폼 로그인**: 애플리케이션에서 자체적으로 사용자 정보를 관리하고, 간단한 인증 방식을 구현하고자 할 때 적합합니다.  
* **OAuth 2.0**: 제3자 서비스를 이용하여 사용자 인증을 위임하고, 단일 로그인(SSO)을 지원하고자 할 때 적합합니다.  
* **JWT**: 마이크로서비스 아키텍처와 같이 상태 비저장 방식으로 인증을 구현하고자 할 때 적합합니다.

## **Spring Security 접근 제어 방법**

Spring Security는 다양한 접근 제어 방법을 제공합니다. 주요 방법과 장단점은 다음과 같습니다3.

| 접근 제어 방법 | 설명 | 장점 | 단점 |
| :---- | :---- | :---- | :---- |
| URL 기반 접근 제어 | URL 패턴에 따라 접근 권한을 제어하는 방식 | 간단하고 직관적 | URL 구조 변경 시 유지보수 어려움 |
| 메서드 기반 접근 제어 | 특정 메서드에 대한 접근 권한을 제어하는 방식 | 세분화된 접근 제어 가능 | 구현이 복잡할 수 있음 |
| 표현식 기반 접근 제어 | Spring Expression Language(SpEL)을 사용하여 복잡한 접근 제어 로직을 구현하는 방식 | 유연하고 강력 | 설정이 복잡할 수 있음 |

**접근 제어 방법 비교:**

* **URL 기반 접근 제어**: 간단한 권한 부여 규칙을 구현할 때 적합합니다. 예를 들어, 특정 URL 경로에 대한 접근을 특정 역할을 가진 사용자에게만 허용하는 경우에 유용합니다.  
* **메서드 기반 접근 제어**: 보다 세분화된 권한 부여 규칙을 구현할 때 적합합니다. 특정 메서드에 대한 접근을 제어해야 하는 경우에 유용합니다.  
* **표현식 기반 접근 제어**: 복잡한 권한 부여 규칙을 구현할 때 적합합니다. 예를 들어, 사용자의 속성, 요청 매개변수, 시스템 시간 등 다양한 조건을 기반으로 접근을 제어해야 하는 경우에 유용합니다.

## **Spring Security 예제 코드**

Spring Security를 사용하여 권한 인증 및 접근 제어를 구현하는 예제 코드는 다음과 같습니다5.

**1\. 폼 로그인 기반 인증 및 URL 기반 접근 제어:**

Java

`@Configuration`  
`@EnableWebSecurity`  
`public class WebSecurityConfig extends WebSecurityConfigurerAdapter {`

    `@Override`  
    `protected void configure(HttpSecurity http) throws Exception {`  
        `http`  
            `.authorizeRequests()`  
                `.antMatchers("/", "/home").permitAll()`  
                `.antMatchers("/admin/**").hasRole("ADMIN")`  
                `.anyRequest().authenticated()`  
            `.and()`  
            `.formLogin()`  
                `.loginPage("/login")`  
                `.permitAll()`  
            `.and()`  
            `.logout()`  
                `.permitAll();`  
    `}`  
`}`

이 예제 코드는 폼 로그인을 사용하며, / 및 /home URL은 모든 사용자가 접근할 수 있도록 허용하고, /admin/\*\* URL은 ADMIN 역할을 가진 사용자만 접근할 수 있도록 설정합니다. 나머지 URL은 인증된 사용자만 접근할 수 있습니다.

**2\. 메서드 기반 접근 제어:**

Java

`@Service`  
`public class MyService {`

    `@PreAuthorize("hasRole('ADMIN')")`  
    `public void adminOnlyMethod() {`  
        `// 관리자 권한이 필요한 로직 수행`  
    `}`  
`}`

이 예제 코드는 MyService 클래스의 adminOnlyMethod() 메서드에 @PreAuthorize 어노테이션을 사용하여 ADMIN 역할을 가진 사용자만 해당 메서드를 호출할 수 있도록 설정합니다.

**3\. 표현식 기반 접근 제어:**

Java

`@Service`  
`public class MyService {`

    `@PreAuthorize("#userId == authentication.principal.id")`  
    `public void accessOnlyOwnData(String userId) {`  
        `// 현재 인증된 사용자의 ID와 요청된 userId가 일치하는 경우에만 접근 허용`  
    `}`  
`}`

이 예제 코드는 accessOnlyOwnData() 메서드에 @PreAuthorize 어노테이션과 SpEL 표현식을 사용하여 현재 인증된 사용자의 ID와 요청된 userId가 일치하는 경우에만 메서드 호출을 허용합니다.

## **Spring Security 권한 계층 구조**

Spring Security는 권한 계층 구조를 직접적으로 지원하지는 않지만, 애플리케이션 레벨에서 권한 계층을 관리하고 Spring Security의 권한 부여 메커니즘과 연동하여 사용할 수 있습니다5. 예를 들어, ROLE\_ADMIN 권한은 ROLE\_USER 권한을 포함하도록 계층 구조를 설계하고, 이를 Spring Security 설정에 반영하여 ROLE\_ADMIN 사용자는 ROLE\_USER 권한이 필요한 리소스에도 접근할 수 있도록 구현할 수 있습니다.

**권한 계층 구조 구현 예시:**

* 사용자 정의 UserDetailsService를 구현하여 사용자의 역할 정보를 가져올 때, 상위 역할에 대한 권한도 함께 포함하도록 로직을 추가할 수 있습니다.  
* Spring Security의 RoleHierarchy 인터페이스를 구현하여 역할 계층 구조를 정의하고, SecurityContextHolder를 통해 현재 사용자의 권한을 확인할 때 RoleHierarchy를 사용하여 계층 구조를 고려한 권한 확인을 수행할 수 있습니다.

## **Spring Security 사용자 정의 권한**

Spring Security는 사용자 정의 권한을 생성하고 적용할 수 있는 유연성을 제공합니다1. @EnableWebSecurity 어노테이션을 사용하여 Spring Security를 활성화하고, HttpSecurity 객체를 구성하여 권한을 정의할 수 있습니다. 예를 들어, 특정 URL에 대한 접근 권한을 부여하려면 authorizeRequests() 메서드를 사용하여 URL 패턴과 권한을 매핑합니다.

**사용자 정의 권한 생성 및 적용:**

1. @EnableWebSecurity 어노테이션을 사용하여 Spring Security를 활성화합니다.  
2. WebSecurityConfigurerAdapter를 상속받는 설정 클래스를 생성하고, configure(HttpSecurity http) 메서드를 오버라이드합니다.  
3. authorizeRequests() 메서드를 사용하여 URL 패턴과 권한을 매핑합니다.  
4. 사용자 정보를 저장하는 데이터베이스 또는 인증 시스템에서 사용자에게 권한을 할당합니다.

**예시:**

Java

`@EnableWebSecurity`  
`public class SecurityConfig extends WebSecurityConfigurerAdapter {`

    `@Override`  
    `protected void configure(HttpSecurity http) throws Exception {`  
        `http`  
            `.authorizeRequests()`  
                `.antMatchers("/admin/**").hasRole("ADMIN")`  
                `.antMatchers("/user/**").hasAnyRole("USER", "ADMIN")`  
                `.anyRequest().authenticated()`  
            `.and()`  
            `// ... other configurations`  
    `}`  
`}`

이 예제에서 /admin/\*\* URL은 ADMIN 권한을 가진 사용자만 접근할 수 있고, /user/\*\* URL은 USER 또는 ADMIN 권한을 가진 사용자만 접근할 수 있습니다.

## **Spring Security 이벤트 처리**

Spring Security는 인증 및 권한 부여와 관련된 다양한 이벤트를 제공합니다1. 이러한 이벤트를 활용하여 애플리케이션의 보안 로직을 확장할 수 있습니다.

**이벤트 처리 예시:**

* **인증 성공/실패 이벤트**: AuthenticationSuccessEvent 및 AuthenticationFailureEvent를 사용하여 로그인 성공/실패 시 로그를 기록하거나, 추가적인 작업을 수행할 수 있습니다.  
* **세션 생성/소멸 이벤트**: SessionCreationEvent 및 SessionDestroyedEvent를 사용하여 사용자 세션 생성/소멸 시 필요한 작업을 수행할 수 있습니다.  
* **권한 부여 실패 이벤트**: AccessDeniedEvent를 사용하여 권한 부여 실패 시 사용자에게 오류 메시지를 표시하거나, 접근 로그를 기록할 수 있습니다.

**이벤트 리스너 등록:**

Spring Security 이벤트 리스너를 등록하려면 ApplicationListener 인터페이스를 구현하는 클래스를 생성하고, onApplicationEvent() 메서드에서 이벤트 처리 로직을 구현합니다.

**예시:**

Java

`@Component`  
`public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {`

    `@Override`  
    `public void onApplicationEvent(AuthenticationSuccessEvent event) {`  
        `// 로그인 성공 시 수행할 작업`  
        `Authentication authentication = event.getAuthentication();`  
        `// ...`  
    `}`  
`}`

## **Accessing Security Context**

Spring Security는 인증된 사용자의 정보(SecurityContext)를 SecurityContextHolder에 저장합니다. 개발자는 필요시 SecurityContextHolder를 참조하여 보안 컨텍스트(SecurityContext)에 접근할 수 있으며 현재 사용자의 권한 확인이나 사용자 정보를 가져올 수 있습니다7.

**예시:**

Java

`Authentication authentication = SecurityContextHolder.getContext().getAuthentication();`  
`String currentUsername = authentication.getName();`

## **Implementing an Authorization Library**

Spring Security를 사용하여 사용자 정의 권한 인증 및 접근 제어 라이브러리를 구현하는 방법은 다음과 같습니다.

1. 사용자 정의 권한을 정의합니다. 예를 들어, READ, WRITE, DELETE와 같은 권한을 정의할 수 있습니다.  
2. 사용자 저장소를 생성하고, 각 사용자에게 권한을 할당합니다.  
3. AccessDecisionVoter 인터페이스를 구현하여 사용자 정의 권한 확인 로직을 구현합니다.  
4. AccessDecisionManager를 구성하여 사용자 정의 AccessDecisionVoter를 사용하도록 설정합니다.

**예시:**

Java

`public class CustomPermissionEvaluator implements PermissionEvaluator {`

    `@Override`  
    `public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {`  
        `// 사용자 정의 권한 확인 로직 구현`  
        `// ...`  
    `}`

    `// ...`  
`}`

## **Spring Security 추가 정보**

Spring Security에 대한 추가 정보는 다음 자료들을 참고할 수 있습니다1.

* Spring Security 공식 문서: [https://docs.spring.io/spring-security/reference/index.html](https://docs.spring.io/spring-security/reference/index.html)  
* Spring Security 관련 블로그 게시물: [https://blog.naver.com/naverfinancial/223546684214?fromRss=true\&trackingCode=rss](https://blog.naver.com/naverfinancial/223546684214?fromRss=true&trackingCode=rss)

## **결론**

Spring Security는 Java 애플리케이션의 보안을 위한 강력하고 유연한 프레임워크입니다. 다양한 인증 방식, 접근 제어 방법, 이벤트 처리 기능, 그리고 다른 시스템과의 통합 기능을 제공하여 애플리케이션의 보안 요구사항을 충족할 수 있도록 지원합니다. 애플리케이션의 특성에 맞는 적절한 인증 및 권한 부여 메커니즘을 선택하고, Spring Security가 제공하는 다양한 기능을 활용하여 안전하고 효율적인 애플리케이션을 구축할 수 있습니다. Spring Security는 지속적으로 발전하고 있으며, 최신 버전에서는 리액티브 애플리케이션 지원, 권한 부여 모델 개선 등 새로운 기능들이 추가되었습니다. Spring Security 공식 문서와 관련 자료들을 참고하여 최신 정보를 습득하고, 애플리케이션 보안에 활용하시기 바랍니다.

#### **참고 자료**

1\. Spring Security :: Spring Security, 1월 5, 2025에 액세스, [https://docs.spring.io/spring-security/reference/index.html](https://docs.spring.io/spring-security/reference/index.html)  
2\. Introduction to Spring Security and its Features \- GeeksforGeeks, 1월 5, 2025에 액세스, [https://www.geeksforgeeks.org/introduction-to-spring-security-and-its-features/](https://www.geeksforgeeks.org/introduction-to-spring-security-and-its-features/)  
3\. Spring Security Tutorial \- GeeksforGeeks, 1월 5, 2025에 액세스, [https://www.geeksforgeeks.org/spring-security-tutorial/](https://www.geeksforgeeks.org/spring-security-tutorial/)  
4\. Spring Security의 인증 알아보기 | 네이버페이 기술블로그, 1월 5, 2025에 액세스, [https://blog.naver.com/naverfinancial/223546684214?fromRss=true\&trackingCode=rss](https://blog.naver.com/naverfinancial/223546684214?fromRss=true&trackingCode=rss)  
5\. Spring Security: Authentication and Authorization In-Depth, 1월 5, 2025에 액세스, [https://www.marcobehler.com/guides/spring-security](https://www.marcobehler.com/guides/spring-security)  
6\. Spring Security \- 토리맘의 한글라이즈 프로젝트, 1월 5, 2025에 액세스, [https://godekdls.github.io/Spring%20Security/contents/](https://godekdls.github.io/Spring%20Security/contents/)  
7\. Spring Security: Part 1\. 인증과 인가 아키텍처의 이해와 실전 활용 \- 이글루코퍼레이션, 1월 5, 2025에 액세스, [https://www.igloo.co.kr/security-information/spring-security-part1-%EC%9D%B8%EC%A6%9D%EA%B3%BC-%EC%9D%B8%EA%B0%80-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98%EC%9D%98-%EC%9D%B4%ED%95%B4%EC%99%80-%EC%8B%A4%EC%A0%84-%ED%99%9C%EC%9A%A9/](https://www.igloo.co.kr/security-information/spring-security-part1-%EC%9D%B8%EC%A6%9D%EA%B3%BC-%EC%9D%B8%EA%B0%80-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98%EC%9D%98-%EC%9D%B4%ED%95%B4%EC%99%80-%EC%8B%A4%EC%A0%84-%ED%99%9C%EC%9A%A9/)