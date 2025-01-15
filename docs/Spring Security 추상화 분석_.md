# **Spring Security 아키텍처 분석: 추상화를 통한 유연하고 확장 가능한 보안 시스템 구축**

## **개요**

Spring Security는 Java 애플리케이션의 보안을 담당하는 강력한 프레임워크입니다. Spring Security는 인증, 인가, 공격 방어 등 다양한 보안 기능을 제공하며, 개발자는 이를 통해 복잡한 보안 로직을 간편하게 구현할 수 있습니다. Spring Security의 핵심은 추상화를 통한 유연성과 확장성 확보에 있습니다. Spring Security는 PSA(Portable Service Abstraction)을 통해 트랜잭션 관리, 메시징, 이메일 등의 서비스를 추상화하여 개발자가 특정 구현 기술에 종속되지 않고 일관된 방식으로 서비스를 사용할 수 있도록 지원합니다. 또한, 추상화를 통해 개발자는 보안 로직에 대한 깊은 이해 없이도 Spring Security를 사용할 수 있고, 보안 설정을 유연하게 변경할 수 있으며, 다양한 보안 요구 사항에 쉽게 대응할 수 있습니다. 이 글에서는 Spring Security의 주요 구성 요소와 아키텍처를 분석하고, 추상화를 통해 높은 수준의 보안 시스템을 구축하는 방법을 자세히 알아보겠습니다.

## **Spring Security 핵심 구성 요소**

Spring Security는 여러 핵심 구성 요소들의 상호 작용을 통해 작동합니다. 각 구성 요소는 특정 역할을 담당하며, 이들의 조합을 통해 다양한 보안 요구 사항을 충족할 수 있습니다. 주요 구성 요소는 다음과 같습니다.

* **AuthenticationManager:** 인증을 담당하는 핵심 인터페이스입니다. authenticate() 메서드를 통해 인증 요청을 처리하고, 인증 성공 시 Authentication 객체를 반환합니다1.  
* **AccessDecisionManager:** 인가를 담당하는 핵심 인터페이스입니다. decide() 메서드를 통해 인증된 사용자가 특정 자원에 접근할 수 있는지 판단합니다2.  
* **SecurityContextHolder:** 인증된 사용자의 정보를 저장하는 저장소입니다. SecurityContext 객체를 통해 현재 인증된 사용자의 정보에 접근할 수 있습니다3.  
* **UserDetailsService:** 사용자 정보를 로드하는 인터페이스입니다. loadUserByUsername() 메서드를 통해 사용자 이름을 기반으로 사용자 정보를 조회합니다4.  
* **AuthenticationProvider:** AuthenticationManager의 구현체로, 실제 인증 로직을 담당합니다5.  
* **AccessDecisionVoter:** AccessDecisionManager에서 사용하는 투표자로, 인가 결정에 참여합니다6.  
* **FilterChainProxy:** Spring Security 필터 체인의 프록시 역할을 수행합니다7.  
* **SecurityInterceptor:** 웹 요청을 가로채어 보안 기능을 적용하는 인터셉터입니다8.

## **Spring Security 인증 과정 추상화**

Spring Security는 인증 과정을 추상화하여 개발자가 보안 로직을 직접 구현하는 부담을 줄여줍니다. 다음은 Spring Security의 일반적인 인증 과정입니다.

1. 사용자가 로그인 정보를 입력하면 AuthenticationFilter (예: UsernamePasswordAuthenticationFilter) 가 요청을 가로채 Authentication 객체를 생성합니다9.  
2. AuthenticationManager는 AuthenticationProvider에게 인증을 위임합니다5.  
3. AuthenticationProvider는 UserDetailsService를 사용하여 사용자 정보를 로드하고, 입력된 로그인 정보와 비교하여 인증을 수행합니다9.  
4. 인증 성공 시 Authentication 객체가 SecurityContext에 저장됩니다9.

이러한 추상화된 인증 과정을 통해 개발자는 다양한 인증 방식을 쉽게 구현하고 통합할 수 있습니다.

## **Spring Security 인가 과정 추상화**

Spring Security는 인가 과정 또한 추상화하여 개발자가 세밀한 권한 설정을 간편하게 처리할 수 있도록 지원합니다.

1. 인증된 사용자가 자원에 접근하면 SecurityInterceptor가 요청을 가로챕니다8.  
2. AccessDecisionManager는 AccessDecisionVoter들의 투표 결과를 종합하여 접근 허용 여부를 결정합니다2.  
3. 접근이 허용되면 요청이 처리되고, 허용되지 않으면 예외가 발생합니다8.

Spring Security는 다양한 AccessDecisionVoter를 제공하며, 개발자는 필요에 따라 사용자 정의 AccessDecisionVoter를 구현하여 인가 로직을 확장할 수 있습니다.

## **다양한 인증 방식 지원**

Spring Security는 폼 로그인, OAuth 2.0, JWT 등 다양한 인증 방식을 지원합니다. 각 방식은 추상화된 인터페이스를 통해 구현되어, 개발자는 필요에 따라 적절한 방식을 선택하고 설정할 수 있습니다.

* **폼 로그인:** 전통적인 웹 애플리케이션에서 사용하는 로그인 방식입니다. Spring Security는 폼 로그인을 위한 기본적인 기능을 제공하지만, 개발자가 직접 로그인 페이지, 성공/실패 처리 URL 등을 설정해야 합니다10.  
* **OAuth 2.0:** 써드파티 서비스 (예: 구글, 페이스북) 를 이용한 로그인 방식입니다. Spring Security는 OAuth 2.0을 위한 클라이언트 및 서버 설정을 지원합니다. OAuth 2.0은 사용자의 계정 정보를 직접 관리하지 않고 써드파티 서비스에 위임하여 인증을 처리하므로, 애플리케이션의 보안 관리 부담을 줄일 수 있습니다11.  
* **JWT:** 토큰 기반 인증 방식입니다. Spring Security는 JWT 토큰 생성, 검증, 필터 등을 제공합니다. JWT는 stateless한 인증 방식으로, 토큰 자체에 사용자 정보와 권한 정보가 포함되어 있어 서버에 세션 정보를 저장할 필요가 없습니다. 따라서 확장성이 뛰어나지만, 토큰 관리에 주의해야 합니다12.

## **다양한 인가 방식 지원**

Spring Security는 URL 기반, 역할 기반, 표현식 기반 등 다양한 인가 방식을 지원합니다.

* **URL 기반 인가:** URL 패턴에 따라 접근 권한을 설정하는 방식입니다. 예를 들어, /admin/\*\* 패턴의 URL은 관리자 권한을 가진 사용자만 접근할 수 있도록 설정할 수 있습니다. URL 기반 인가는 간단한 권한 설정에 적합합니다13.  
* **역할 기반 인가:** 사용자의 역할에 따라 접근 권한을 설정하는 방식입니다. 예를 들어, ROLE\_ADMIN 역할을 가진 사용자는 관리자 페이지에 접근할 수 있도록 설정할 수 있습니다. Spring Security는 Role Hierarchy를 사용하여 권한 계층 구조를 정의할 수 있습니다. Role Hierarchy를 사용하면 상위 역할이 하위 역할의 모든 권한을 포함하도록 설정하여 권한 관리를 단순화할 수 있습니다14.  
* **표현식 기반 인가:** SpEL (Spring Expression Language)을 사용하여 접근 권한을 설정하는 방식입니다. SpEL을 사용하면 복잡한 조건을 사용하여 권한을 설정할 수 있습니다. 예를 들어, hasRole('ADMIN') and isAuthenticated()와 같은 표현식을 사용하여 관리자 권한을 가진 인증된 사용자만 접근할 수 있도록 설정할 수 있습니다. 표현식 기반 인가는 복잡한 권한 설정에 유용합니다16.

## **다양한 설정 방식 지원**

Spring Security는 XML, Java Configuration, Security DSL 등 다양한 설정 방식을 지원합니다.

* **XML 설정:** Spring Security 초기 버전에서 사용되던 방식입니다. XML 파일을 사용하여 보안 설정을 정의합니다. XML 설정은 유연성이 떨어지고 설정 파일이 복잡해질 수 있다는 단점이 있습니다17.  
* **Java Configuration:** Java 코드를 사용하여 Spring Security를 설정하는 방식입니다. @Configuration 어노테이션을 사용하여 설정 클래스를 정의하고, @EnableWebSecurity 어노테이션을 사용하여 Spring Security를 활성화합니다. Java Configuration은 XML 설정보다 유연하고 코드를 통해 설정을 관리할 수 있다는 장점이 있습니다18.  
* **Security DSL:** 람다 표현식을 사용하여 Spring Security를 설정하는 방식입니다. Security DSL은 Java Configuration을 기반으로 하며, 람다 표현식을 사용하여 보다 간결하고 직관적인 설정이 가능합니다19.

## **웹 요청 처리 과정**

Spring Security는 필터 체인을 사용하여 웹 요청을 처리합니다. 각 필터는 특정 보안 기능을 담당하며, 필터 체인을 통해 순차적으로 실행됩니다. 웹 요청 처리 과정은 다음과 같습니다.

1. 클라이언트에서 웹 요청이 전송됩니다.  
2. DispatcherServlet이 요청을 수신하고 Spring Security 필터 체인으로 전달합니다20.  
3. FilterChainProxy는 SecurityFilterChain에 등록된 필터들을 순서대로 실행합니다7.  
4. 각 필터는 인증, 인가, CSRF 방지 등의 보안 기능을 수행합니다21.  
5. 모든 필터를 통과한 요청은 DispatcherServlet으로 다시 전달되어 컨트롤러로 라우팅됩니다20.  
6. 컨트롤러는 비즈니스 로직을 처리하고 응답을 생성합니다.  
7. 응답은 Spring Security 필터 체인을 거쳐 클라이언트로 전송됩니다.

## **다양한 보안 기능 제공**

Spring Security는 CSRF 방지, 세션 관리, CORS 등 다양한 보안 기능을 제공합니다.

* **CSRF 방지:** Cross-Site Request Forgery 공격을 방지하는 기능입니다. Spring Security는 CSRF 토큰을 사용하여 CSRF 공격을 방지합니다. CSRF 토큰은 서버에서 생성되어 클라이언트에 전달되며, 클라이언트는 이후 요청에 CSRF 토큰을 포함하여 전송해야 합니다. 서버는 요청에 포함된 CSRF 토큰이 유효한지 검증하여 CSRF 공격을 차단합니다22.  
* **세션 관리:** 사용자 세션을 관리하는 기능입니다. Spring Security는 세션 생성, 만료, 동시 세션 제어 등을 지원합니다. Remember Me 기능을 통해 사용자가 로그인 상태를 유지할 수 있도록 지원하고, 이를 통해 사용자 편의성을 높일 수 있습니다. 또한, 동시 세션 제어를 통해 동일한 계정으로 여러 곳에서 로그인하는 것을 제한하거나, 이전 세션을 만료시키는 등의 기능을 제공합니다23.  
* **CORS:** Cross-Origin Resource Sharing을 지원하는 기능입니다. Spring Security는 CORS 설정을 통해 다른 도메인의 자원 접근을 제어할 수 있습니다. 예를 들어, 특정 도메인에서 오는 요청만 허용하거나, 특정 HTTP 메서드만 허용하도록 설정할 수 있습니다24.  
* **익명 사용자 처리:** Spring Security는 익명 사용자에게도 AnonymousAuthenticationToken을 부여하여 인증된 사용자와 동일한 방식으로 처리합니다. 이를 통해 일관된 보안 관리가 가능합니다23.

## **확장성**

Spring Security는 확장 가능하도록 설계되었습니다. 개발자는 AuthenticationProvider, AccessDecisionVoter 외에도 UserDetailsService, SecurityFilterChain 등을 확장하여 사용자 정의 보안 기능을 추가할 수 있습니다25.

* **AuthenticationProvider 확장:** 사용자 정의 인증 로직을 구현할 수 있습니다. 예를 들어, 데이터베이스가 아닌 LDAP 서버에서 사용자 정보를 가져와 인증을 수행하도록 AuthenticationProvider를 구현할 수 있습니다26.  
* **AccessDecisionVoter 확장:** 사용자 정의 인가 로직을 구현할 수 있습니다. 예를 들어, IP 주소를 기반으로 접근을 제어하는 AccessDecisionVoter를 구현할 수 있습니다15.  
* **UserDetailsService 확장:** 사용자 정보를 로드하는 방식을 변경할 수 있습니다. 예를 들어, 데이터베이스가 아닌 외부 API에서 사용자 정보를 가져오도록 UserDetailsService를 구현할 수 있습니다4.  
* **SecurityFilterChain 확장:** 필터 체인에 사용자 정의 필터를 추가하여 보안 기능을 확장할 수 있습니다. 예를 들어, 모든 요청에 대한 로그를 기록하는 필터를 추가할 수 있습니다17.

## **결론**

Spring Security는 추상화를 통해 유연하고 확장 가능한 보안 시스템을 구축할 수 있도록 지원합니다. 다양한 인증 및 인가 방식, 설정 방식, 보안 기능을 제공하며, 개발자는 필요에 따라 이를 조합하여 애플리케이션의 보안 요구 사항을 충족할 수 있습니다. Spring Security의 추상화된 아키텍처는 개발 생산성 향상, 보안 시스템 유연성 확보, 다양한 보안 요구 사항에 대한 효율적인 대응 등의 이점을 제공합니다.

#### **참고 자료**

1\. Spring Security 파헤치기 \- velog, 12월 29, 2024에 액세스, [https://velog.io/@mw310/Spring-Security-%ED%8C%8C%ED%97%A4%EC%B9%98%EA%B8%B0](https://velog.io/@mw310/Spring-Security-%ED%8C%8C%ED%97%A4%EC%B9%98%EA%B8%B0)  
2\. 스프링 시큐리티 주요 아키텍처 이해 \- Catsbi's DLog, 12월 29, 2024에 액세스, [https://catsbi.oopy.io/f9b0d83c-4775-47da-9c81-2261851fe0d0](https://catsbi.oopy.io/f9b0d83c-4775-47da-9c81-2261851fe0d0)  
3\. \[Spring\]SecurityContextHolder란? \- 블로그 \- 티스토리, 12월 29, 2024에 액세스, [https://dev-swlee.tistory.com/31](https://dev-swlee.tistory.com/31)  
4\. Spring Security \- 사용자 관리, 12월 29, 2024에 액세스, [https://assu10.github.io/dev/2023/11/18/springsecurity-user-management/](https://assu10.github.io/dev/2023/11/18/springsecurity-user-management/)  
5\. \[Spring Security\] 스프링 시큐리티의 동작 구조 \- 개발 메모용 블로그, 12월 29, 2024에 액세스, [https://memodayoungee.tistory.com/135](https://memodayoungee.tistory.com/135)  
6\. \[ 정수원 스프링 시큐리티 \#2 \] 스프링 시큐리티 주요 아키텍처 이해 (4) \- velog, 12월 29, 2024에 액세스, [https://velog.io/@tngh4037/%EC%A0%95%EC%88%98%EC%9B%90-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0-2-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0-%EC%A3%BC%EC%9A%94-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98-%EC%9D%B4%ED%95%B4-4](https://velog.io/@tngh4037/%EC%A0%95%EC%88%98%EC%9B%90-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0-2-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0-%EC%A3%BC%EC%9A%94-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98-%EC%9D%B4%ED%95%B4-4)  
7\. FilterChainProxy \- Lifealong \- 티스토리, 12월 29, 2024에 액세스, [https://0soo.tistory.com/m/136](https://0soo.tistory.com/m/136)  
8\. \[Spring Security\] 권한 체크와 오류 처리(SecurityIntercepter, ExceptionTranslationFilter), 12월 29, 2024에 액세스, [https://velog.io/@dnrwhddk1/Spring-Security-%EA%B6%8C%ED%95%9C-%EC%B2%B4%ED%81%AC%EC%99%80-%EC%98%A4%EB%A5%98-%EC%B2%98%EB%A6%AC](https://velog.io/@dnrwhddk1/Spring-Security-%EA%B6%8C%ED%95%9C-%EC%B2%B4%ED%81%AC%EC%99%80-%EC%98%A4%EB%A5%98-%EC%B2%98%EB%A6%AC)  
9\. 스프링 시큐리티 (Spring Security) \- 인증, 인가 절차 \- velog, 12월 29, 2024에 액세스, [https://velog.io/@kwj1830/codestates29](https://velog.io/@kwj1830/codestates29)  
10\. \[security\] Form Login 구현 \- velog, 12월 29, 2024에 액세스, [https://velog.io/@evan523/security-FormLogin-%EA%B5%AC%ED%98%84](https://velog.io/@evan523/security-FormLogin-%EA%B5%AC%ED%98%84)  
11\. \[NHN FORWARD 2021\] Spring Security 5 OAuth 총정리: 클라부터 서버까지 \- YouTube, 12월 29, 2024에 액세스, [https://www.youtube.com/watch?v=-YbqW-pqt3w](https://www.youtube.com/watch?v=-YbqW-pqt3w)  
12\. \[Spring Security\] Spring Security와 JWT 적용 과정 \- prao \- 티스토리, 12월 29, 2024에 액세스, [https://prao.tistory.com/entry/Spring-Security-Spring-Security%EC%99%80-JWT-%EC%A0%81%EC%9A%A9-%EA%B3%BC%EC%A0%95](https://prao.tistory.com/entry/Spring-Security-Spring-Security%EC%99%80-JWT-%EC%A0%81%EC%9A%A9-%EA%B3%BC%EC%A0%95)  
13\. \[Spring Security\] 예제 프로젝트5. 리소스별 인가 및 동적 권한 설정 \- 괴발자의 공간, 12월 29, 2024에 액세스, [https://cares-log.tistory.com/27](https://cares-log.tistory.com/27)  
14\. Spring Security 구조, 흐름 그리고 역할 알아보기 \- velog, 12월 29, 2024에 액세스, [https://velog.io/@hope0206/Spring-Security-%EA%B5%AC%EC%A1%B0-%ED%9D%90%EB%A6%84-%EA%B7%B8%EB%A6%AC%EA%B3%A0-%EC%97%AD%ED%95%A0-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0](https://velog.io/@hope0206/Spring-Security-%EA%B5%AC%EC%A1%B0-%ED%9D%90%EB%A6%84-%EA%B7%B8%EB%A6%AC%EA%B3%A0-%EC%97%AD%ED%95%A0-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0)  
15\. AccessDecisionVoter (spring-security-docs 6.4.1 API), 12월 29, 2024에 액세스, [https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/AccessDecisionVoter.html](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/AccessDecisionVoter.html)  
16\. 시큐리티(세션) \- 캐모마일 개발자 커뮤니티, 12월 29, 2024에 액세스, [https://chamomile.lotteinnovate.com/guides/session/](https://chamomile.lotteinnovate.com/guides/session/)  
17\. Spring Security Custom 사용법 (Code-Based) \- 네이버 블로그, 12월 29, 2024에 액세스, [https://m.blog.naver.com/lhm0812/221057255249](https://m.blog.naver.com/lhm0812/221057255249)  
18\. Java Configuration :: Spring Security, 12월 29, 2024에 액세스, [https://docs.spring.io/spring-security/reference/servlet/configuration/java.html](https://docs.spring.io/spring-security/reference/servlet/configuration/java.html)  
19\. Custom DSLs \- HttpSecurity.with(AbstractHttpConfigurer) \- bestdevelop-lab 님의 블로그, 12월 29, 2024에 액세스, [https://bestdevelop-lab.tistory.com/141](https://bestdevelop-lab.tistory.com/141)  
20\. Spring Security: Part 1\. 인증과 인가 아키텍처의 이해와 실전 활용 \- 이글루코퍼레이션, 12월 29, 2024에 액세스, [https://www.igloo.co.kr/security-information/spring-security-part1-%EC%9D%B8%EC%A6%9D%EA%B3%BC-%EC%9D%B8%EA%B0%80-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98%EC%9D%98-%EC%9D%B4%ED%95%B4%EC%99%80-%EC%8B%A4%EC%A0%84-%ED%99%9C%EC%9A%A9/](https://www.igloo.co.kr/security-information/spring-security-part1-%EC%9D%B8%EC%A6%9D%EA%B3%BC-%EC%9D%B8%EA%B0%80-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98%EC%9D%98-%EC%9D%B4%ED%95%B4%EC%99%80-%EC%8B%A4%EC%A0%84-%ED%99%9C%EC%9A%A9/)  
21\. \[스프링 시큐리티 무작정 따라하기\] 5\. 필터(Filter) 이해하기, 12월 29, 2024에 액세스, [https://gaebalsogi.tistory.com/83](https://gaebalsogi.tistory.com/83)  
22\. \[Spring\]Spring security \- CSRF란?, http.csrf().disable() ? \- velog, 12월 29, 2024에 액세스, [https://velog.io/@wonizizi99/SpringSpring-security-CSRF%EB%9E%80-disable](https://velog.io/@wonizizi99/SpringSpring-security-CSRF%EB%9E%80-disable)  
23\. Spring Security Session, 12월 29, 2024에 액세스, [https://hohshho.github.io/SpringSecurity1/](https://hohshho.github.io/SpringSecurity1/)  
24\. Spring Security \- CORS (Cross-Site Resource Sharing, 교차 출처 리소스 공유), 12월 29, 2024에 액세스, [https://assu10.github.io/dev/2024/01/09/springsecurity-cors/](https://assu10.github.io/dev/2024/01/09/springsecurity-cors/)  
25\. Spring Security \- 3\. 인증 절차를 정의하는 AuthenticationProvider, 12월 29, 2024에 액세스, [https://gregor77.github.io/2021/05/18/spring-security-03/](https://gregor77.github.io/2021/05/18/spring-security-03/)  
26\. Spring Security \- 인증 구현(1): AuthenticationProvider · ASSU BLOG., 12월 29, 2024에 액세스, [https://assu10.github.io/dev/2023/11/25/springsecurity-authrorization-1/](https://assu10.github.io/dev/2023/11/25/springsecurity-authrorization-1/)