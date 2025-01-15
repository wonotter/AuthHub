# **Spring Security 내부 동작 원리 분석**

## **Spring Security란?**

Spring Security는 Spring 기반 애플리케이션의 보안을 담당하는 강력하고 유연한 프레임워크입니다. 인증, 인가, 공격 방어 등 웹 애플리케이션 보안에 필요한 다양한 기능을 제공하며, Spring 프레임워크에 최적화되어 있습니다1. 개발자는 Spring Security를 통해 복잡한 보안 로직을 직접 구현하지 않고도 손쉽게 애플리케이션을 보호할 수 있으며, IoC/DI 패턴과 같은 Spring의 주요 기능들을 활용하여 보안 기능을 효율적으로 구현할 수 있습니다1. 하지만, 현대의 복잡한 IT 서비스는 HTTP Basic 인증과 같은 단순한 인증 방식보다 더 정교한 인증 메커니즘을 요구합니다2.

## **Spring Security 자동 설정 초기화 과정**

Spring Boot 환경에서 spring-boot-starter-security 의존성을 추가하면 Spring Security의 자동 설정 기능이 활성화됩니다3. 자동 설정을 통해 기본적인 보안 설정이 적용되며, 초기화 과정은 다음과 같습니다.

1. 애플리케이션 구동 시 Spring Security 자동 설정이 시작됩니다5.  
2. SpringWebMvcImportSelector 인터페이스를 통해 특정 조건에 따라 필요한 설정 클래스를 로드합니다5.  
3. WebMvcSecurityConfiguration 클래스가 로드되고, SecurityFilterAutoConfiguration을 통해 DelegatingFilterProxyRegistartionBean이 로드됩니다5. DelegatingFilterProxyRegistartionBean은 클라이언트 요청을 FilterChainProxy라는 필터 체인에 위임하는 역할을 합니다5.  
4. WebMvcSecurityConfiguration은 3개의 타입 리졸버를 생성합니다. 그 중 AuthenticationPrincipalArgumentResolver는 인증된 사용자 정보를 컨트롤러 메서드의 파라미터에 자동으로 바인딩하는 역할을 합니다5.  
5. SpringBootWebSecurityConfiguration은 SecurityFilterChain 타입의 빈을 생성합니다5.  
6. WebSecurityConfiguration은 WebSecurity 객체를 생성합니다. WebSecurity는 SecurityFilterChainBuilder를 통해 SecurityFilterChain을 생성하고 관리합니다5.  
7. WebSecurity는 build() 메서드를 통해 SecurityFilterChain을 생성하고, 모든 요청에 대해 해당 필터 체인을 적용합니다5.

## **SecurityFilterChain**

SecurityFilterChain은 Spring Security에서 보안 필터의 체인을 정의하는 데 사용됩니다6. 요청이 애플리케이션의 Servlet에 도달하기 전에 다양한 보안 검사를 수행하는 필터들이 있으며, 이를 보안 필터라고 부릅니다. SecurityFilterChain은 각 보안 필터가 순차적으로 실행되도록 하여 애플리케이션의 보안 설정을 체계적으로 관리할 수 있게 합니다. Spring Security는 DelegatingFilterProxy라는 필터를 만들어 메인 필터 체인(서블릿 필터들 사이)에 추가하고, 그 아래에 다시 SecurityFilterChain을 그룹화합니다7. 이를 통해 URL에 따라 적용되는 필터 체인을 다르게 하는 방법을 사용하며, 경우에 따라 특정 필터를 무시하고 통과하게 할 수도 있습니다.

## **Spring Security 자동 설정 진행 과정**

Spring Security 자동 설정은 다음과 같은 순서로 진행됩니다.

1. @EnableWebSecurity 어노테이션을 통해 Spring Security 설정을 활성화합니다8.  
2. WebSecurityConfigurerAdapter를 상속받은 설정 클래스를 생성하여 보안 설정을 커스터마이징합니다9.  
3. HttpSecurity 객체를 사용하여 HTTP 요청에 대한 보안 설정을 구성합니다9.  
4. authorizeRequests() 메서드를 사용하여 URL 패턴별 접근 권한을 설정합니다9.  
5. antMatchers() 메서드를 사용하여 특정 URL 패턴에 대한 접근을 허용하거나 차단합니다9.  
6. formLogin() 또는 httpBasic() 메서드를 사용하여 인증 방식을 설정합니다9.  
7. logout() 메서드를 사용하여 로그아웃 설정을 구성합니다9.  
8. exceptionHandling() 메서드를 사용하여 예외 처리 설정을 구성합니다9.

## **Spring Security 커스텀 클래스 생성 과정**

Spring Security는 기본 설정 외에도 개발자가 필요에 따라 커스텀 클래스를 생성하여 보안 기능을 확장할 수 있도록 지원합니다. 커스텀 클래스 생성 과정은 다음과 같습니다.

1. Spring Security에서 제공하는 인터페이스 또는 클래스를 상속받아 커스텀 클래스를 생성합니다. 예를 들어, AuthenticationSuccessHandler를 상속받아 인증 성공 후 처리 로직을 커스터마이징할 수 있습니다10.  
2. 커스텀 클래스에 필요한 의존성을 주입하고, 비즈니스 로직을 구현합니다.  
3. Spring Security 설정 클래스에서 커스텀 클래스를 등록하고 사용합니다.

Spring Security는 메서드 레벨에서도 보안을 적용할 수 있도록 지원합니다1. @PreAuthorize와 같은 어노테이션을 사용하여 컨트롤러 내의 개별 메서드에 대한 접근 권한을 설정할 수 있습니다. 예를 들어, 특정 역할을 가진 사용자만 메서드에 접근할 수 있도록 제한할 수 있습니다.

## **AuthenticationEntryPoint 동작 과정**

AuthenticationEntryPoint는 인증되지 않은 사용자가 보호된 리소스에 접근하려고 할 때 호출되는 인터페이스입니다11. 인증이 필요한 리소스에 접근 시 인증되지 않은 사용자는 AuthenticationEntryPoint에 의해 정의된 동작을 수행하게 됩니다. 예를 들어, 로그인 페이지로 리다이렉션되거나 에러 메시지를 받을 수 있습니다12.

Spring Security는 기본적으로 LoginUrlAuthenticationEntryPoint와 BasicAuthenticationEntryPoint 두 가지 AuthenticationEntryPoint를 제공합니다13. LoginUrlAuthenticationEntryPoint는 폼 로그인 방식에서 인증 예외 발생 시 로그인 페이지로 리다이렉션하고, BasicAuthenticationEntryPoint는 HTTP Basic 인증 방식에서 인증 예외 발생 시 401 Unauthorized 응답을 반환합니다13.

ExceptionHandlingConfigurer는 AuthenticationEntryPoint 설정을 관리하며, defaultEntryPointMappings 맵에 여러 개의 AuthenticationEntryPoint를 저장합니다13. 인증 예외 발생 시 ExceptionTranslationFilter는 defaultEntryPointMappings에서 적절한 AuthenticationEntryPoint를 선택하여 실행합니다13.

## **커스텀 AuthenticationEntryPoint 생성 방법**

개발자는 필요에 따라 커스텀 AuthenticationEntryPoint를 생성하여 인증 예외 처리 로직을 변경할 수 있습니다. 커스텀 AuthenticationEntryPoint 생성 방법은 다음과 같습니다.

1. AuthenticationEntryPoint 인터페이스를 구현하는 클래스를 생성합니다.  
2. commence() 메서드를 오버라이딩하여 인증 예외 발생 시 수행할 동작을 구현합니다. 예를 들어, JSON 형식의 에러 메시지를 반환하거나 특정 페이지로 리다이렉션할 수 있습니다9. 아래는 JSON 형식의 응답을 반환하는 커스텀 AuthenticationEntryPoint 예시입니다.

Java

`@Component`  
`public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {`

    `@Override`  
    `public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {`  
        `response.setContentType("application/json;charset=UTF-8");`  
        `response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);`

        `Map<String, Object> responseMap = new HashMap<>();`  
        `responseMap.put("message", "인증이 필요합니다.");`  
        `responseMap.put("code", HttpServletResponse.SC_UNAUTHORIZED);`

        `ObjectMapper objectMapper = new ObjectMapper();`  
        `String json = objectMapper.writeValueAsString(responseMap);`

        `PrintWriter writer = response.getWriter();`  
        `writer.print(json);`  
    `}`  
`}`

3. Spring Security 설정 클래스에서 http.exceptionHandling().authenticationEntryPoint() 메서드를 사용하여 커스텀 AuthenticationEntryPoint를 등록합니다14.

## **시큐리티 인증 및 인가 흐름**

Spring Security는 Servlet Filter를 기반으로 동작하며, DispatcherServlet 이전에 실행됩니다7. 클라이언트의 모든 HTTP 요청은 DispatcherServlet이 가장 먼저 수신하고, Spring Security 필터 체인을 거쳐 처리됩니다16. Spring Security의 인증 및 인가 흐름은 다음과 같습니다.

1. 클라이언트가 보호된 리소스에 접근합니다.  
2. Spring Security 필터 체인이 요청을 가로챕니다.  
3. SecurityContextPersistenceFilter는 SecurityContextRepository를 통해 SecurityContext를 로드하거나 저장합니다7. SecurityContext는 현재 사용자의 인증 정보를 저장하는 객체입니다.  
4. WebAsyncManagerIntegrationFilter는 Spring MVC에서 Async 관련 기능을 사용할 때 SecurityContext를 서로 다른 Thread 간에 공유할 수 있도록 도와줍니다7.  
5. 인증 필터 (예: UsernamePasswordAuthenticationFilter, BasicAuthenticationFilter)가 인증을 시도합니다.  
6. 인증에 성공하면 Authentication 객체가 생성되고 SecurityContext에 저장됩니다.  
7. 인가 필터 (예: FilterSecurityInterceptor)가 AccessDecisionManager를 사용하여 접근 권한을 검사합니다.  
8. 접근 권한이 있으면 요청이 컨트롤러로 전달됩니다.  
9. 접근 권한이 없으면 AccessDeniedHandler가 예외를 처리합니다.

**CSRF Protection:**

Spring Security는 CsrfFilter를 통해 CSRF(Cross-Site Request Forgery) 공격을 방어합니다16. CsrfFilter는 CSRF 토큰을 생성하고 검증하여 악의적인 요청으로부터 애플리케이션을 보호합니다. 서버로 HTTP 요청이 수신되면 SecurityFilterChain이 동작하여 요청을 처리하고, CsrfFilter는 CSRF 토큰을 자동으로 생성하고 관리합니다.

## **HTTP Basic 인증**

HTTP Basic 인증은 사용자 이름과 비밀번호를 HTTP 헤더에 포함하여 인증하는 간단한 인증 방식입니다17. 사용자 이름과 비밀번호는 Base64로 인코딩되어 Authorization 헤더에 추가됩니다18. HTTP Basic 인증은 보안에 취약하므로 HTTPS와 함께 사용하는 것이 권장됩니다2.

**HTTP Basic 인증 과정:**

1. 클라이언트가 보호된 리소스에 접근합니다.  
2. 서버는 401 Unauthorized 응답과 함께 WWW-Authenticate 헤더를 반환합니다19. WWW-Authenticate 헤더는 클라이언트에게 Basic 인증을 요구하는 정보를 포함하며, realm이라는 속성을 사용하여 인증 영역을 지정합니다19. Realm은 서버에서 서로 다른 비밀번호를 가진 영역을 구분하기 위해 사용됩니다.  
3. 클라이언트는 사용자 이름과 비밀번호를 Base64로 인코딩하여 Authorization 헤더에 추가하여 다시 요청합니다18.  
4. 서버는 Authorization 헤더를 디코딩하여 사용자를 인증합니다.  
5. 인증에 성공하면 요청된 리소스를 반환합니다.

## **HttpBasicConfigurer**

HttpBasicConfigurer는 Spring Security에서 HTTP Basic 인증을 설정하는 데 사용되는 클래스입니다19. HttpBasicConfigurer를 사용하여 realm 이름을 변경하거나, 인증 성공/실패 핸들러를 지정할 수 있습니다19.

## **BasicAuthenticationFilter**

BasicAuthenticationFilter는 HTTP Basic 인증을 처리하는 필터입니다20. BasicAuthenticationFilter는 요청 헤더에서 Authorization 헤더를 추출하고, Base64로 인코딩된 사용자 이름과 비밀번호를 디코딩하여 인증을 시도합니다18. BasicAuthenticationFilter는 OncePerRequestFilter를 상속받아 요청당 한 번만 실행됩니다18. BasicAuthenticationFilter는 stateless 방식으로 동작하며, 매 요청마다 인증을 시도합니다18. 즉, 세션 정보를 사용하지 않고, 각 요청에 포함된 인증 정보를 기반으로 인증을 수행합니다.

**HttpBasicConfigurer와 BasicAuthenticationFilter 사용 시기:**

* 클라이언트와 서버가 분리된 환경 (예: SPA, 모바일 앱)에서 HTTP Basic 인증을 사용할 때 18  
* 서버 사이드 렌더링을 사용하지 않고, 프론트엔드에서 뷰를 담당하는 경우 18  
* 간단한 인증 방식이 필요한 경우 18

## **CORS**

CORS (Cross-Origin Resource Sharing)는 웹 브라우저가 다른 출처 (도메인, 프로토콜, 포트)의 리소스에 접근할 수 있도록 허용하는 메커니즘입니다21. 예를 들어, domainA.com에서 호스팅되는 웹 페이지가 domainB.com의 API를 호출하려는 경우 CORS 정책이 적용됩니다. CORS는 웹 페이지에서 AJAX 요청을 사용하여 다른 도메인의 API를 호출하는 경우와 같이, 웹 보안을 위해 필요한 메커니즘입니다23.

**CORS 요청 종류:**

CORS는 요청의 특징에 따라 두 가지 종류로 나뉩니다.

* **Simple Request:** GET, HEAD, POST 메서드를 사용하고, Content-Type 헤더가 application/x-www-form-urlencoded, multipart/form-data, text/plain 중 하나인 경우입니다24. Simple Request는 브라우저가 서버에 직접 요청을 보냅니다.  
* **Preflight Request:** Simple Request 조건을 만족하지 않는 경우, 브라우저는 실제 요청을 보내기 전에 OPTIONS 메서드를 사용하여 서버에 사전 요청 (Preflight Request)을 보냅니다24. Preflight Request는 서버가 실제 요청을 처리할 수 있는지 확인하기 위한 요청이며, 보안상의 이유로 필요합니다. 브라우저는 Preflight Request에 대한 응답을 통해 서버가 CORS를 지원하는지, 어떤 HTTP 메서드와 헤더를 허용하는지 확인합니다.

**CORS 요청 동작 방식:**

1. 브라우저는 교차 출처 요청을 보낼 때 Origin 헤더를 추가하여 요청 출처를 서버에 알립니다25.  
2. 서버는 응답 헤더에 Access-Control-Allow-Origin 헤더를 포함하여 허용되는 출처를 지정합니다25.  
3. 브라우저는 응답 헤더를 확인하여 CORS 정책을 준수하는지 확인합니다25. 만약 정책을 준수하지 않으면 브라우저는 에러를 발생시키고 요청을 차단합니다.  
4. Preflight Request의 경우, 브라우저는 Access-Control-Allow-Methods, Access-Control-Allow-Headers 등의 헤더를 확인하여 허용되는 메서드와 헤더를 확인합니다26.

## **서버에서 Access-Control-Allow 설정**

서버에서 CORS를 허용하려면 응답 헤더에 Access-Control-Allow-Origin 등의 CORS 관련 헤더를 추가해야 합니다27. 이러한 헤더들은 브라우저에게 어떤 출처, 메서드, 헤더가 허용되는지 알려주는 역할을 합니다.

| Header | Description |
| :---- | :---- |
| Access-Control-Allow-Origin | 허용되는 출처를 지정합니다28. |
| Access-Control-Allow-Methods | 허용되는 HTTP 메서드를 지정합니다26. |
| Access-Control-Allow-Headers | 허용되는 HTTP 헤더를 지정합니다26. |
| Access-Control-Allow-Credentials | 쿠키와 같은 자격 증명을 포함할지 여부를 지정합니다27. |
| Access-Control-Max-Age | Preflight Request 결과를 캐싱할 시간을 지정합니다27. |

예를 들어, Access-Control-Allow-Origin 헤더에 https://domainA.com을 지정하면 domainA.com에서 온 요청만 허용됩니다. Access-Control-Allow-Methods 헤더에 GET, POST를 지정하면 GET과 POST 메서드만 허용됩니다. 이러한 헤더들을 조합하여 서버는 CORS 정책을 세밀하게 제어할 수 있습니다27.

## **CorsConfigurer**

CorsConfigurer는 Spring Security에서 CORS 설정을 구성하는 데 사용되는 클래스입니다29. CorsConfigurer를 사용하여 CORS 필터를 추가하고, CorsConfigurationSource를 통해 CORS 설정을 제공할 수 있습니다29. CorsConfigurer는 Spring Security 필터 체인에 CorsFilter를 추가하는 역할을 합니다. 만약 corsFilter라는 이름의 Bean이 이미 존재하면 해당 CorsFilter를 사용하고, 그렇지 않으면 CorsConfigurationSource 빈에서 CORS 설정을 가져와 CorsFilter를 생성합니다.

## **CorsFilter**

CorsFilter는 CORS 정책을 적용하는 필터입니다30. CorsFilter는 요청 헤더를 검사하여 CORS 정책을 위반하는 요청을 차단합니다30. CorsFilter는 CorsConfigurationSource에서 제공하는 CORS 설정을 사용하여 요청을 처리합니다30. CorsFilter는 실제 요청을 처리하기 전에 CORS 정책을 검사하고, 정책을 위반하는 요청은 차단합니다. 정책을 준수하는 요청은 Access-Control-Allow-Origin 등의 CORS 헤더를 응답에 추가하여 브라우저에 전달합니다.

## **결론**

Spring Security는 Spring 기반 애플리케이션의 보안을 강화하기 위한 필수적인 프레임워크입니다. 자동 설정, 커스텀 클래스 생성, 다양한 인증 및 인가 기능, CORS 지원 등을 통해 개발자는 Spring Security를 사용하여 웹 애플리케이션을 효과적으로 보호할 수 있습니다. 이 보고서는 Spring Security의 핵심 동작 원리와 주요 기능에 대한 심층적인 분석을 제공하며, 개발자가 Spring Security를 이해하고 활용하는 데 도움이 될 것입니다. Spring Security의 내부 동작 원리를 이해하는 것은 안전한 애플리케이션을 구축하는 데 매우 중요합니다. 앞으로 OAuth 2.0 및 JWT와 같은 최신 인증 및 인가 기술의 활용이 증가할 것으로 예상되며, Spring Security는 이러한 기술들을 지원하기 위한 기능들을 지속적으로 발전시켜 나갈 것입니다.

#### **참고 자료**

1\. Spring Security RBAC 설정, @PreAuthorize 로 쌈@뽕하게 관리하기 \- dgjinsu \- 티스토리, 1월 15, 2025에 액세스, [https://dgjinsu.tistory.com/78](https://dgjinsu.tistory.com/78)  
2\. Basic 인증 \- 토스페이먼츠 개발자센터, 1월 15, 2025에 액세스, [https://docs.tosspayments.com/resources/glossary/basic-auth](https://docs.tosspayments.com/resources/glossary/basic-auth)  
3\. Spring Boot \- Spring Security 자동설정 \- wooody92's blog, 1월 15, 2025에 액세스, [https://wooody92.github.io/spring%20boot/Spring-Boot-Spring-Security-%EC%9E%90%EB%8F%99%EC%84%A4%EC%A0%95/](https://wooody92.github.io/spring%20boot/Spring-Boot-Spring-Security-%EC%9E%90%EB%8F%99%EC%84%A4%EC%A0%95/)  
4\. Spring Security란? 사용하는 이유부터 설정 방법까지 알려드립니다\! \- 이랜서, 1월 15, 2025에 액세스, [https://www.elancer.co.kr/blog/detail/235](https://www.elancer.co.kr/blog/detail/235)  
5\. Spring Security \- 자동설정에 의한 초기화 과정 이해(1) \- velog, 1월 15, 2025에 액세스, [https://velog.io/@superkkj/Spring-Security-%EC%9E%90%EB%8F%99%EC%84%A4%EC%A0%95%EC%97%90-%EC%9D%98%ED%95%9C-%EC%B4%88%EA%B8%B0%ED%99%94-%EA%B3%BC%EC%A0%95-%EC%9D%B4%ED%95%B41](https://velog.io/@superkkj/Spring-Security-%EC%9E%90%EB%8F%99%EC%84%A4%EC%A0%95%EC%97%90-%EC%9D%98%ED%95%9C-%EC%B4%88%EA%B8%B0%ED%99%94-%EA%B3%BC%EC%A0%95-%EC%9D%B4%ED%95%B41)  
6\. Spring Security의 인증 알아보기 | 네이버페이 기술블로그, 1월 15, 2025에 액세스, [https://blog.naver.com/naverfinancial/223546684214?fromRss=true\&trackingCode=rss](https://blog.naver.com/naverfinancial/223546684214?fromRss=true&trackingCode=rss)  
7\. cors와 spring security filter chain \- kiwi \- 티스토리, 1월 15, 2025에 액세스, [https://kiwideveloper.tistory.com/44](https://kiwideveloper.tistory.com/44)  
8\. \[구현\] Spring Security 6.1 이상 버전 적용, 설정하기 \- 티스토리, 1월 15, 2025에 액세스, [https://dowlsovo.tistory.com/147](https://dowlsovo.tistory.com/147)  
9\. Spring Security Custom EntryPoint \- Program Development \- 티스토리, 1월 15, 2025에 액세스, [https://jodu.tistory.com/15](https://jodu.tistory.com/15)  
10\. \[Spring Security\] 스프링시큐리티 커스텀 필터의 구현(3) \- kimchanjung.dev, 1월 15, 2025에 액세스, [https://kimchanjung.github.io/programming/2020/07/03/spring-security-03/](https://kimchanjung.github.io/programming/2020/07/03/spring-security-03/)  
11\. AuthenticationFailureHandler, AuthenticationEntryPoint 차이점 \- 어쩔 수 없이 시작하는 IT공부 블로그 \- 티스토리, 1월 15, 2025에 액세스, [https://sujinisacat.tistory.com/38](https://sujinisacat.tistory.com/38)  
12\. Spring Security 적용기 (5) Spring Security Authentication 동작원리 \- JiYoung Dev, 1월 15, 2025에 액세스, [https://danyoujeong.tistory.com/229](https://danyoujeong.tistory.com/229)  
13\. AuthenticationEntryPoint 이해 \- velog, 1월 15, 2025에 액세스, [https://velog.io/@superkkj/AuthenticationEntryPoint-%EC%9D%B4%ED%95%B4](https://velog.io/@superkkj/AuthenticationEntryPoint-%EC%9D%B4%ED%95%B4)  
14\. AuthenticationEntryPoint \- 공장콧차뇽 \- 티스토리, 1월 15, 2025에 액세스, [https://batory.tistory.com/454](https://batory.tistory.com/454)  
15\. How to define a custom AuthenticationEntryPoint without XML configuration \- Stack Overflow, 1월 15, 2025에 액세스, [https://stackoverflow.com/questions/24684806/how-to-define-a-custom-authenticationentrypoint-without-xml-configuration](https://stackoverflow.com/questions/24684806/how-to-define-a-custom-authenticationentrypoint-without-xml-configuration)  
16\. Spring Security: Part 1\. 인증과 인가 아키텍처의 이해와 실전 활용 \- 이글루코퍼레이션, 1월 15, 2025에 액세스, [https://www.igloo.co.kr/security-information/spring-security-part1-%EC%9D%B8%EC%A6%9D%EA%B3%BC-%EC%9D%B8%EA%B0%80-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98%EC%9D%98-%EC%9D%B4%ED%95%B4%EC%99%80-%EC%8B%A4%EC%A0%84-%ED%99%9C%EC%9A%A9/](https://www.igloo.co.kr/security-information/spring-security-part1-%EC%9D%B8%EC%A6%9D%EA%B3%BC-%EC%9D%B8%EA%B0%80-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98%EC%9D%98-%EC%9D%B4%ED%95%B4%EC%99%80-%EC%8B%A4%EC%A0%84-%ED%99%9C%EC%9A%A9/)  
17\. \[HTTP\] HTTP 기본 인증 (Basic Authentication): 개념과 사용 방법 \- 깃짱코딩 \- 티스토리, 1월 15, 2025에 액세스, [https://engineerinsight.tistory.com/69](https://engineerinsight.tistory.com/69)  
18\. BasicAuthenticationFilter \- Lifealong \- 티스토리, 1월 15, 2025에 액세스, [https://0soo.tistory.com/149](https://0soo.tistory.com/149)  
19\. 코드로 보는 스프링 시큐리티 basic authentication \- velog, 1월 15, 2025에 액세스, [https://velog.io/@cooper25\_dev/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%BD%94%EB%93%9C%EB%A1%9C-%EB%B3%B4%EB%8A%94-basic-authentication-CORS](https://velog.io/@cooper25_dev/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%BD%94%EB%93%9C%EB%A1%9C-%EB%B3%B4%EB%8A%94-basic-authentication-CORS)  
20\. \[Spring Security\] 14\. BasicAuthenticationFilter \- velog, 1월 15, 2025에 액세스, [https://velog.io/@zosungwoo/Spring-Security-14.-BasicAuthenticationFilter](https://velog.io/@zosungwoo/Spring-Security-14.-BasicAuthenticationFilter)  
21\. developer.mozilla.org, 1월 15, 2025에 액세스, [https://developer.mozilla.org/ko/docs/Web/HTTP/CORS\#:\~:text=%EA%B5%90%EC%B0%A8%20%EC%B6%9C%EC%B2%98%20%EB%A6%AC%EC%86%8C%EC%8A%A4%20%EA%B3%B5%EC%9C%A0(Cross,HTTP%20%ED%97%A4%EB%8D%94%20%EA%B8%B0%EB%B0%98%20%EB%A9%94%EC%BB%A4%EB%8B%88%EC%A6%98%EC%9E%85%EB%8B%88%EB%8B%A4.](https://developer.mozilla.org/ko/docs/Web/HTTP/CORS#:~:text=%EA%B5%90%EC%B0%A8%20%EC%B6%9C%EC%B2%98%20%EB%A6%AC%EC%86%8C%EC%8A%A4%20%EA%B3%B5%EC%9C%A0\(Cross,HTTP%20%ED%97%A4%EB%8D%94%20%EA%B8%B0%EB%B0%98%20%EB%A9%94%EC%BB%A4%EB%8B%88%EC%A6%98%EC%9E%85%EB%8B%88%EB%8B%A4.)  
22\. CORS(교차 출처 리소스 공유) \- 토스페이먼츠 개발자센터, 1월 15, 2025에 액세스, [https://docs.tosspayments.com/resources/glossary/cors](https://docs.tosspayments.com/resources/glossary/cors)  
23\. 악명 높은 CORS 개념 & 해결법 \- 정리 끝판왕, 1월 15, 2025에 액세스, [https://inpa.tistory.com/entry/WEB-%F0%9F%93%9A-CORS-%F0%9F%92%AF-%EC%A0%95%EB%A6%AC-%ED%95%B4%EA%B2%B0-%EB%B0%A9%EB%B2%95-%F0%9F%91%8F](https://inpa.tistory.com/entry/WEB-%F0%9F%93%9A-CORS-%F0%9F%92%AF-%EC%A0%95%EB%A6%AC-%ED%95%B4%EA%B2%B0-%EB%B0%A9%EB%B2%95-%F0%9F%91%8F)  
24\. CORS \- velog, 1월 15, 2025에 액세스, [https://velog.io/@shroad1802/CORS](https://velog.io/@shroad1802/CORS)  
25\. 교차 출처 리소스 공유(CORS) | Articles \- web.dev, 1월 15, 2025에 액세스, [https://web.dev/articles/cross-origin-resource-sharing?hl=ko](https://web.dev/articles/cross-origin-resource-sharing?hl=ko)  
26\. \[HTTP\] CORS(Cross Origin Resource Sharing)란? \- armadillo's blog, 1월 15, 2025에 액세스, [https://armadillo-dev.github.io/http/what-is-cors/](https://armadillo-dev.github.io/http/what-is-cors/)  
27\. 교차 출처 리소스 공유 (CORS) \- HTTP \- MDN Web Docs, 1월 15, 2025에 액세스, [https://developer.mozilla.org/ko/docs/Web/HTTP/CORS](https://developer.mozilla.org/ko/docs/Web/HTTP/CORS)  
28\. CORS의 이해와 올바른 구현을 위한 가이드 \- PallyCon, 1월 15, 2025에 액세스, [https://pallycon.com/ko/blog/cors%EC%9D%98-%EC%9D%B4%ED%95%B4%EC%99%80-%EC%98%AC%EB%B0%94%EB%A5%B8-%EA%B5%AC%ED%98%84%EC%9D%84-%EC%9C%84%ED%95%9C-%EA%B0%80%EC%9D%B4%EB%93%9C/](https://pallycon.com/ko/blog/cors%EC%9D%98-%EC%9D%B4%ED%95%B4%EC%99%80-%EC%98%AC%EB%B0%94%EB%A5%B8-%EA%B5%AC%ED%98%84%EC%9D%84-%EC%9C%84%ED%95%9C-%EA%B0%80%EC%9D%B4%EB%93%9C/)  
29\. Spring Security Fundamentals(3) \- velog, 1월 15, 2025에 액세스, [https://velog.io/@crow/Spring-Security-Fundamentals3](https://velog.io/@crow/Spring-Security-Fundamentals3)  
30\. Spring Security에서 CORS 해결 \- 사연이 있는 코드 \- 티스토리, 1월 15, 2025에 액세스, [https://yousrain.tistory.com/22](https://yousrain.tistory.com/22)