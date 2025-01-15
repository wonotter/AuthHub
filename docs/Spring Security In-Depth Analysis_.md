# **Spring Security 심층 분석**

## **개요**

Spring Security는 Spring 기반 애플리케이션의 보안을 담당하는 강력하고 유연한 프레임워크입니다. 인증, 권한 부여, 일반적인 공격 방어 등 포괄적인 보안 솔루션을 제공하며, Spring Boot, Spring MVC와 같은 Spring 생태계의 다른 모듈과 완벽하게 통합됩니다. 이 글에서는 Spring Security의 핵심 기능과 아키텍처, 인증 및 권한 부여 메커니즘, 그리고 실제 애플리케이션에서의 활용 방법 등을 심층 분석합니다.

## **핵심 기능 및 아키텍처**

Spring Security는 다음과 같은 핵심 기능을 제공합니다1.

* **인증:** 사용자의 신원을 확인하는 프로세스입니다. Spring Security는 폼 로그인, OAuth 2.0, JWT 등 다양한 인증 방식을 지원합니다.  
* **권한 부여:** 인증된 사용자에게 특정 리소스 또는 기능에 대한 접근 권한을 부여하는 프로세스입니다. URL 기반, 메서드 기반, SpEL (Spring Expression Language) 등 다양한 권한 부여 방식을 지원합니다.  
* **일반적인 공격 방어:** CSRF (Cross-Site Request Forgery), XSS (Cross-Site Scripting), 세션 고정, 클릭재킹 등 일반적인 웹 공격으로부터 애플리케이션을 보호합니다3.  
* **Spring MVC Validation:** Spring MVC에서 제공하는 유효성 검사 기능과 통합하여 사용자 입력 데이터의 유효성을 검증할 수 있습니다4.  
* **LDAP (Lightweight Directory Access Protocol):** LDAP 서버를 사용하여 사용자 인증 및 권한 부여를 수행할 수 있습니다4.  
* **JAAS (Java Authentication and Authorization Service) LoginModule:** JAAS LoginModule을 사용하여 Spring Security 인증을 확장할 수 있습니다4.

Spring Security는 Spring MVC의 DispatcherServlet을 통해 모든 HTTP 요청을 처리하며, 서블릿 필터는 Spring Security의 필터 체인을 구성하는 요소 중 하나입니다5. 들어오는 모든 요청은 여러 개의 필터로 구성된 필터 체인을 통과하며, 각 필터는 인증, 권한 부여, 세션 관리 등 특정 보안 작업을 수행합니다6. 필터 체인의 순서는 요청 처리 및 사용자 접근 권한 결정에 중요한 역할을 합니다7.

## **권한 인증 및 접근 제어를 위한 인터페이스 및 구현체**

Spring Security는 권한 인증 및 접근 제어를 위해 다음과 같은 주요 인터페이스와 구현체를 제공합니다.

### **AuthenticationManager**

인증 과정의 핵심 인터페이스인 AuthenticationManager는 사용자 인증 요청을 처리하고, 인증 규칙을 적용하여 사용자의 자격 증명을 검증합니다8. AuthenticationManager는 인증 요청을 여러 개의 AuthenticationProvider에게 위임하고, 각 Provider는 자신의 기능에 따라 요청을 인증합니다9. 최종적으로 AuthenticationManager는 각 인증 시도에 대한 결과를 결정합니다9. Spring Security는 ProviderManager라는 기본 AuthenticationManager 구현체를 제공합니다10.

### **UserDetailsService**

UserDetailsService는 사용자 정보를 로드하는 데 사용되는 핵심 인터페이스입니다11. 데이터베이스, LDAP 또는 외부 서비스와 같은 백엔드 데이터 소스에서 사용자 정보를 검색하여 UserDetails 객체를 반환합니다11. UserDetails 객체는 Spring Security 프레임워크에서 인증된 사용자를 나타내며, 사용자 이름, 비밀번호, 권한 (역할) 및 추가 속성과 같은 정보를 포함합니다11. UserDetailsService는 DaoAuthenticationProvider에서 사용자 이름, 비밀번호 및 기타 속성을 검색하여 사용자를 인증하는 데 사용됩니다12. Spring Security는 UserDetailsService의 메모리 내, JDBC 및 캐싱 구현체를 제공하며, 사용자 정의 UserDetailsService를 빈으로 노출하여 사용자 정의 인증을 정의할 수 있습니다12.

UserDetailsService는 사용자 데이터에 대한 DAO 역할만 수행하며, 프레임워크 내의 다른 구성 요소에 해당 데이터를 제공하는 것 외에 다른 기능은 수행하지 않습니다. 특히, 사용자 인증은 AuthenticationManager에서 수행됩니다13. UserDetailsService는 사용자 정보를 제공하고, AuthenticationProvider는 실제 인증을 수행하는 역할을 담당합니다13.

### **AccessDecisionVoter**

AccessDecisionVoter는 권한 부여 결정에 대한 투표를 담당하는 인터페이스입니다14. AccessDecisionManager는 AccessDecisionVoter들의 투표를 수집하고, 응답을 집계하여 최종 권한 부여 결정을 내립니다15. AccessDecisionVoter는 권한 부여 결정에 찬성 (ACCESS\_GRANTED), 반대 (ACCESS\_DENIED) 또는 기권 (ACCESS\_ABSTAIN) 투표를 할 수 있습니다14. AccessDecisionVoter는 호출된 메서드 또는 구성 속성 매개변수로 인해 접근 제어 결정에 투표하도록 특별히 의도된 경우가 아니면 ACCESS\_ABSTAIN을 반환해야 합니다14.

AccessDecisionVoter의 동작 방식을 설명하기 위해 USER와 ADMIN 두 가지 사용자 유형이 있는 시나리오를 가정해 보겠습니다. USER는 짝수 분에만 시스템에 접근할 수 있고, ADMIN은 항상 접근 권한이 부여됩니다. 이 경우, AccessDecisionVoter는 현재 시간과 사용자 유형을 기반으로 접근 허용 여부를 결정합니다16.

최종 권한 부여 결정은 AccessDecisionManager에서 처리합니다. AbstractAccessDecisionManager는 서로 독립적으로 투표를 담당하는 AccessDecisionVoter 목록을 포함합니다. 가장 일반적인 사용 사례를 다루기 위해 투표를 처리하는 세 가지 구현체가 있습니다16.

* AffirmativeBased: AccessDecisionVoter 중 하나라도 찬성하면 접근 권한을 부여합니다.  
* ConsensusBased: 기권하는 Voter를 무시하고 찬성 투표가 반대 투표보다 많으면 접근 권한을 부여합니다.  
* UnanimousBased: 모든 Voter가 찬성해야 접근 권한을 부여합니다.

Spring Security는 RoleVoter, AuthenticatedVoter, WebExpressionVoter 등 다양한 AccessDecisionVoter 구현체를 제공합니다14.

### **주요 구현체**

| 인터페이스 | 구현체 | 설명 |
| :---- | :---- | :---- |
| AuthenticationManager | ProviderManager | 여러 개의 AuthenticationProvider를 사용하여 인증을 처리하는 기본 구현체 |
| UserDetailsService | InMemoryUserDetailsManager | 메모리에 사용자 정보를 저장하는 구현체 |
| UserDetailsService | JdbcUserDetailsManager | JDBC를 통해 데이터베이스에서 사용자 정보를 로드하는 구현체 |
| AccessDecisionVoter | AffirmativeBased | 하나 이상의 AccessDecisionVoter가 찬성하면 접근을 허용하는 구현체 |
| AccessDecisionVoter | RoleVoter | 구성 속성을 단순 역할 이름으로 처리하고, 사용자에게 해당 역할이 할당된 경우 접근을 허용하는 구현체 |

## **Spring Security 설정 방법**

Spring Security는 다양한 설정 방법을 제공하며, 각 방식은 특징과 장단점을 가지고 있습니다. 아래 표는 Spring Security의 주요 설정 방법을 요약한 것입니다.

| 설정 방식 | 설명 | 주요 특징 | 사용 예시 |
| :---- | :---- | :---- | :---- |
| XML 설정 | Spring Security 2.x에서 주로 사용되던 방식 | XML 파일을 사용하여 보안 설정을 정의 | \<http\> 태그를 사용하여 URL 패턴에 대한 보안 규칙을 정의 |
| Java Configuration | Spring Security 3.x부터 도입된 방식 | Java 클래스를 사용하여 보안 설정을 정의 | @EnableWebSecurity 어노테이션과 WebSecurityConfigurerAdapter 클래스를 사용 |
| SecurityFilterChain | Spring Security 5.7부터 도입된 방식 | 람다 표현식을 사용하여 보안 설정을 정의 | HttpSecurity 객체의 메서드 체이닝을 사용 |

## **실제 애플리케이션에서 Spring Security 활용 방법**

Spring Security는 실제 애플리케이션에서 다양한 방식으로 활용될 수 있습니다.

* **다양한 인증 방식 구현:** 폼 로그인, OAuth 2.0, JWT 등 다양한 인증 방식을 구현하여 사용자 인증을 처리할 수 있습니다17.  
* **권한 부여 및 접근 제어 설정:** URL 기반, 메서드 기반, SpEL 등 다양한 권한 부여 방식을 사용하여 리소스 및 기능에 대한 접근 제어를 설정할 수 있습니다18.  
* **Spring Security 확장 및 커스터마이징:** Spring Security는 확장 및 커스터마이징이 용이하도록 설계되었습니다19. 필요에 따라 사용자 정의 필터, 인증 제공자, 접근 결정 투표자 등을 구현하여 Spring Security의 기능을 확장할 수 있습니다20.  
* **SPA 및 마이크로서비스 아키텍처:** 최근에는 SPA(Single Page Application)와 같은 REST 기반 마이크로서비스 아키텍처에서 Spring Security가 JWT 토큰 기반 인증과 API Gateway를 통한 권한 관리 등에 활용됩니다21.

## **데모 애플리케이션 구현 및 테스트**

Spring Security를 활용한 간단한 데모 애플리케이션을 구현하고 테스트하는 과정에서 InMemoryUserDetailsManager, UserDetailsService, UserDetails 등을 활용하여 사용자 정보를 저장하고, 인증 및 권한 부여 기능을 구현했습니다22. 이를 통해 Spring Security의 기본적인 동작 방식과 설정 방법을 익힐 수 있었습니다.

## **Spring Security 장단점 및 주의 사항**

### **장점**

* **포괄적인 보안 기능:** Spring Security는 인증, 권한 부여, 일반적인 공격 방어 등 웹 애플리케이션 보안에 필요한 모든 기능을 제공합니다.  
* **Spring Framework과의 통합:** Spring Security는 Spring Framework과 완벽하게 통합되어 Spring 기반 애플리케이션에서 쉽게 사용할 수 있습니다.  
* **유연성 및 확장성:** Spring Security는 매우 유연하고 확장 가능한 프레임워크로, 필요에 따라 사용자 정의 보안 설정을 구성할 수 있습니다19.  
* **활성화된 커뮤니티:** Spring Security는 활성화된 커뮤니티를 가지고 있어 문제 해결 및 정보 공유가 용이합니다. Stack Overflow와 같은 온라인 커뮤니티에서 Spring Security 관련 질문에 대한 답변을 쉽게 찾아볼 수 있으며, 개발자들 간의 활발한 정보 교류가 이루어지고 있습니다24.

### **단점**

* **복잡성:** Spring Security는 다양한 기능을 제공하기 때문에 설정 및 사용이 복잡할 수 있습니다25. Spring Security 문서나 튜토리얼은 초보 개발자에게는 어렵게 느껴질 수 있으며, 다양한 기능으로 인해 설정이 복잡해지고 오류가 발생하기 쉽습니다.  
* **학습 곡선:** Spring Security를 효과적으로 사용하려면 어느 정도의 학습 곡선이 필요합니다25.

### **주의 사항**

* **보안 설정 오류:** Spring Security 설정 오류는 애플리케이션 보안에 취약점을 야기할 수 있습니다26. 예를 들어, 필터 체인을 잘못 설정하면 리다이렉션 루프 또는 잘못된 인증 문제가 발생할 수 있으며, springSecurityFilterChain 빈을 정의하지 않으면 Spring Security가 정상적으로 동작하지 않을 수 있습니다26.  
* **최신 버전 유지:** Spring Security의 최신 버전을 유지하여 알려진 보안 취약점을 방지해야 합니다28.

## **Spring Security 관련 라이브러리**

* **Spring Boot Security:** Spring Boot 애플리케이션에서 Spring Security를 쉽게 통합할 수 있도록 자동 구성 및 편의 기능을 제공합니다29. Spring Boot Security는 security starter dependency를 추가하면 SecurityAutoConfiguration 클래스를 통해 기본적인 보안 설정을 자동으로 구성합니다30. 웹 애플리케이션은 기본적으로 보안이 활성화되며, 컨텐츠 협상 전략을 사용하여 httpBasic 또는 formLogin을 사용할지 결정합니다29. 또한, 사용자 정의 설정을 통해 actuator endpoints 및 static resources에 대한 접근 규칙을 재정의할 수 있습니다29.

## **Spring Security 학습 자료 및 참고 서적**

* **Spring Security 공식 문서:** Spring Security에 대한 자세한 정보는 공식 문서를 참조하십시오31.  
* **Spring Security 관련 블로그 포스트 및 튜토리얼:** 다양한 블로그 포스트 및 튜토리얼을 통해 Spring Security 활용 방법을 학습할 수 있습니다1.  
* **Spring Security 소스 코드:** Spring Security 소스 코드를 분석하여 프레임워크의 동작 방식을 심층적으로 이해할 수 있습니다32.  
* **Spring Security 관련 질의응답 사이트:** Stack Overflow와 같은 질의응답 사이트에서 Spring Security 관련 문제 및 해결 방안을 찾아볼 수 있습니다33.  
* **Spring Security 관련 학습 자료:** Udemy, edX, Pluralsight 등 온라인 학습 플랫폼에서 Spring Security 강의를 수강할 수 있습니다34.  
* **Spring Security 관련 참고 서적:** "Spring Security in Action", "Spring Security 3rd Edition" 등 Spring Security 관련 서적을 참고할 수 있습니다37.

## **결론**

Spring Security는 Java 애플리케이션, 특히 Spring 기반 애플리케이션의 보안을 위한 강력하고 유연한 프레임워크입니다. 다양한 인증 및 권한 부여 메커니즘, 일반적인 공격 방어 기능, Spring 생태계와의 완벽한 통합을 통해 안전하고 신뢰할 수 있는 애플리케이션을 구축할 수 있도록 지원합니다. 하지만 복잡성과 학습 곡선을 고려하여 신중하게 설정하고 사용해야 합니다. Spring Security 공식 문서, 블로그 포스트, 튜토리얼, 소스 코드 분석, 질의응답 사이트, 온라인 강의, 참고 서적 등 다양한 학습 자료를 활용하여 Spring Security에 대한 이해도를 높이고, 실제 애플리케이션에 효과적으로 적용할 수 있습니다.

Spring Security는 지속적으로 발전하고 있으며, 최신 버전에서는 OAuth 2.0, OpenID Connect, JWT와 같은 최신 보안 표준을 지원하고 있습니다. 또한, 클라우드 환경, 마이크로서비스 아키텍처, 모바일 애플리케이션 등 다양한 환경에서 Spring Security를 활용하는 사례가 늘어나고 있습니다. Spring Security를 사용할 때는 최신 보안 트렌드를 따라잡고, 애플리케이션의 특성에 맞는 적절한 설정을 적용하는 것이 중요합니다. 또한, 보안 설정 오류를 방지하기 위해 철저한 테스트를 수행하고, 정기적인 보안 점검을 통해 애플리케이션의 안전성을 확보해야 합니다.

#### **참고 자료**

1\. Spring Security Tutorial \- GeeksforGeeks, 1월 5, 2025에 액세스, [https://www.geeksforgeeks.org/spring-security-tutorial/](https://www.geeksforgeeks.org/spring-security-tutorial/)  
2\. Spring Security Tutorial \- Javatpoint, 1월 5, 2025에 액세스, [https://www.javatpoint.com/spring-security-tutorial](https://www.javatpoint.com/spring-security-tutorial)  
3\. Spring Security: Authentication and Authorization In-Depth \- Marco Behler, 1월 5, 2025에 액세스, [https://www.marcobehler.com/guides/spring-security](https://www.marcobehler.com/guides/spring-security)  
4\. Spring Security Features \- Javatpoint, 1월 5, 2025에 액세스, [https://www.javatpoint.com/spring-security-features](https://www.javatpoint.com/spring-security-features)  
5\. Introduction to Spring Security Architecture | by Rasheed Shaik \- Medium, 1월 5, 2025에 액세스, [https://medium.com/@rasheed99/introduction-on-spring-security-architecture-eb5d7de75a4f](https://medium.com/@rasheed99/introduction-on-spring-security-architecture-eb5d7de75a4f)  
6\. Spring Security Architecture Explained with JWT Authentication Example(Spring Boot) | by Dilanka Wijesekara | Medium, 1월 5, 2025에 액세스, [https://medium.com/@dilankacm/spring-security-architecture-explained-with-jwt-authentication-example-spring-boot-5cc583a9aeac](https://medium.com/@dilankacm/spring-security-architecture-explained-with-jwt-authentication-example-spring-boot-5cc583a9aeac)  
7\. Spring Security architecture \- Hyperskill, 1월 5, 2025에 액세스, [https://hyperskill.org/learn/step/27770](https://hyperskill.org/learn/step/27770)  
8\. hyperskill.org, 1월 5, 2025에 액세스, [https://hyperskill.org/learn/step/40291\#:\~:text=The%20AuthenticationManager%20is%20a%20primary,rules%20within%20the%20Spring%20application.](https://hyperskill.org/learn/step/40291#:~:text=The%20AuthenticationManager%20is%20a%20primary,rules%20within%20the%20Spring%20application.)  
9\. AuthenticationManager \- Hyperskill, 1월 5, 2025에 액세스, [https://hyperskill.org/learn/step/40291](https://hyperskill.org/learn/step/40291)  
10\. Spring Security Authentication Process Explained In Detailed | by Abhijeet Chopra \- Medium, 1월 5, 2025에 액세스, [https://medium.com/spring-framework/spring-security-authentication-process-explained-in-detailed-5bc0a424a746](https://medium.com/spring-framework/spring-security-authentication-process-explained-in-detailed-5bc0a424a746)  
11\. Spring Security \- UserDetailsService and UserDetails with Example \- GeeksforGeeks, 1월 5, 2025에 액세스, [https://www.geeksforgeeks.org/spring-security-userdetailsservice-and-userdetails-with-example/](https://www.geeksforgeeks.org/spring-security-userdetailsservice-and-userdetails-with-example/)  
12\. UserDetailsService :: Spring Security, 1월 5, 2025에 액세스, [https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/user-details-service.html](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/user-details-service.html)  
13\. Spring Security Custom Authentication \- AuthenticationProvider vs UserDetailsService, 1월 5, 2025에 액세스, [https://stackoverflow.com/questions/31630818/spring-security-custom-authentication-authenticationprovider-vs-userdetailsser](https://stackoverflow.com/questions/31630818/spring-security-custom-authentication-authenticationprovider-vs-userdetailsser)  
14\. AccessDecisionVoter (Spring Security 4.2.20.RELEASE API), 1월 5, 2025에 액세스, [https://docs.spring.io/spring-security/site/docs/4.2.x/apidocs/org/springframework/security/access/AccessDecisionVoter.html](https://docs.spring.io/spring-security/site/docs/4.2.x/apidocs/org/springframework/security/access/AccessDecisionVoter.html)  
15\. 21.2.1 The AccessDecisionManager \- Spring, 1월 5, 2025에 액세스, [https://docs.spring.io/spring-security/site/docs/4.0.x/reference/html/authz-arch.html](https://docs.spring.io/spring-security/site/docs/4.0.x/reference/html/authz-arch.html)  
16\. Custom Spring Security with AccessDecisionVoters | Baeldung, 1월 5, 2025에 액세스, [https://www.baeldung.com/spring-security-custom-voter](https://www.baeldung.com/spring-security-custom-voter)  
17\. Security with Spring Series | Baeldung, 1월 5, 2025에 액세스, [https://www.baeldung.com/security-spring](https://www.baeldung.com/security-spring)  
18\. Authorization :: Spring Security, 1월 5, 2025에 액세스, [https://docs.spring.io/spring-security/reference/servlet/authorization/index.html](https://docs.spring.io/spring-security/reference/servlet/authorization/index.html)  
19\. Spring Security – Customizing Authentication and Authorization \- GeeksforGeeks, 1월 5, 2025에 액세스, [https://www.geeksforgeeks.org/spring-security-customizing-authentication-and-authorization/](https://www.geeksforgeeks.org/spring-security-customizing-authentication-and-authorization/)  
20\. Customizing Authentication in Spring Boot | by Nagarjun (Arjun) Nagesh \- Medium, 1월 5, 2025에 액세스, [https://medium.com/@nagarjun\_nagesh/customizing-authentication-in-spring-boot-aa60bd13aebe](https://medium.com/@nagarjun_nagesh/customizing-authentication-in-spring-boot-aa60bd13aebe)  
21\. Why bother with Spring Security when you can simply generate a token in the backend, pass it to the client, then validate it in each request/endpoint? : r/learnjava \- Reddit, 1월 5, 2025에 액세스, [https://www.reddit.com/r/learnjava/comments/16cwowr/why\_bother\_with\_spring\_security\_when\_you\_can/](https://www.reddit.com/r/learnjava/comments/16cwowr/why_bother_with_spring_security_when_you_can/)  
22\. Security demo with JWT authentication and Spring Boot \- GitHub, 1월 5, 2025에 액세스, [https://github.com/ramit21/Spring-Security-Demo](https://github.com/ramit21/Spring-Security-Demo)  
23\. A Custom Spring SecurityConfigurer | Baeldung, 1월 5, 2025에 액세스, [https://www.baeldung.com/spring-security-custom-configurer](https://www.baeldung.com/spring-security-custom-configurer)  
24\. Spring Security Community, 1월 5, 2025에 액세스, [https://docs.spring.io/spring-security/reference/community.html](https://docs.spring.io/spring-security/reference/community.html)  
25\. An Overview of the Spring Security Java Framework \- Auth0, 1월 5, 2025에 액세스, [https://auth0.com/blog/spring-security-overview/](https://auth0.com/blog/spring-security-overview/)  
26\. Spring Security: Solving Common Login Issues \- Java Tech Blog, 1월 5, 2025에 액세스, [https://javanexus.com/blog/spring-security-login-troubleshooting](https://javanexus.com/blog/spring-security-login-troubleshooting)  
27\. Why is Spring Security not working? \- Stack Overflow, 1월 5, 2025에 액세스, [https://stackoverflow.com/questions/39588934/why-is-spring-security-not-working](https://stackoverflow.com/questions/39588934/why-is-spring-security-not-working)  
28\. Top 10 Spring Security Best Practices for Java Developers | Black Duck Blog, 1월 5, 2025에 액세스, [https://www.blackduck.com/blog/spring-security-best-practices.html](https://www.blackduck.com/blog/spring-security-best-practices.html)  
29\. Spring Security :: Spring Boot, 1월 5, 2025에 액세스, [https://docs.spring.io/spring-boot/reference/web/spring-security.html](https://docs.spring.io/spring-boot/reference/web/spring-security.html)  
30\. Spring Boot Security Auto-Configuration | Baeldung, 1월 5, 2025에 액세스, [https://www.baeldung.com/spring-boot-security-autoconfiguration](https://www.baeldung.com/spring-boot-security-autoconfiguration)  
31\. Spring Security, 1월 5, 2025에 액세스, [https://docs.spring.io/spring-security/reference/index.html](https://docs.spring.io/spring-security/reference/index.html)  
32\. spring-projects/spring-security \- GitHub, 1월 5, 2025에 액세스, [https://github.com/spring-projects/spring-security](https://github.com/spring-projects/spring-security)  
33\. Newest 'spring-security' Questions \- Stack Overflow, 1월 5, 2025에 액세스, [https://stackoverflow.com/questions/tagged/spring-security](https://stackoverflow.com/questions/tagged/spring-security)  
34\. Top Spring Security Courses Online \- Updated \[January 2025\] \- Udemy, 1월 5, 2025에 액세스, [https://www.udemy.com/topic/spring-security/](https://www.udemy.com/topic/spring-security/)  
35\. Learn Spring Security with Online Courses and Programs | edX, 1월 5, 2025에 액세스, [https://www.edx.org/learn/spring-security](https://www.edx.org/learn/spring-security)  
36\. Spring Course: Spring Security Fundamentals \- Pluralsight, 1월 5, 2025에 액세스, [https://www.pluralsight.com/courses/spring-security-fundamentals](https://www.pluralsight.com/courses/spring-security-fundamentals)  
37\. 3 Books and Courses to Learn Spring Security in 2024 | by javinpaul \- Medium, 1월 5, 2025에 액세스, [https://medium.com/javarevisited/3-best-spring-security-books-and-resources-for-java-programmers-653d05c8afd4](https://medium.com/javarevisited/3-best-spring-security-books-and-resources-for-java-programmers-653d05c8afd4)  
38\. Spring Security \- Third Edition: Secure your web applications, RESTful services, and microservice architectures \- Amazon.com, 1월 5, 2025에 액세스, [https://www.amazon.com/Spring-Security-applications-microservice-architectures/dp/1787129519](https://www.amazon.com/Spring-Security-applications-microservice-architectures/dp/1787129519)