# **Spring Boot 기반 라이브러리 개발 가이드: 권한 인증, 소셜 네트워크 연동 및 Central Repository 배포**

이 문서는 Spring Boot를 사용하여 권한 인증, 접근 제어 및 소셜 네트워크 연동 기능을 제공하는 라이브러리를 개발하고 Central Repository에 배포하는 방법에 대한 포괄적인 가이드를 제공합니다. Spring Boot는 빠르고 효율적인 애플리케이션 개발을 위한 프레임워크이며, 라이브러리 개발에도 매우 유용하게 활용될 수 있습니다.

## **1\. Spring Boot 기반 라이브러리 개발 기초**

Spring Boot 기반 라이브러리를 개발하기 위해서는 다음과 같은 기초 지식이 필요합니다.

* **Spring Framework 핵심 개념**: Spring Framework의 핵심 개념인 IoC (Inversion of Control), DI (Dependency Injection), AOP (Aspect-Oriented Programming)를 이해하는 것이 중요합니다. IoC는 객체의 생성과 생명주기를 컨테이너에 위임하여 객체 간의 의존성을 줄이고 모듈화를 향상시키는 디자인 패턴입니다. DI는 의존성을 외부에서 주입하는 방식으로, 객체 간의 결합도를 낮추고 유연성을 높입니다. AOP는 핵심 로직에서 부가 기능을 분리하여 모듈화를 향상시키는 기술입니다. 이러한 개념들을 통해 효율적이고 유지보수가 용이한 코드를 작성할 수 있습니다1.  
* **Spring Boot 자동 설정**: Spring Boot는 자동 설정 기능을 통해 개발자가 직접 설정해야 하는 부분을 최소화하여 개발 편의성을 높입니다. 예를 들어, Spring Boot는 클래스패스에 특정 라이브러리가 존재하는 경우 해당 라이브러리에 필요한 설정을 자동으로 구성합니다. 라이브러리 개발 시에도 Spring Boot의 자동 설정 기능을 활용하여 사용자가 라이브러리를 쉽게 설정하고 사용할 수 있도록 해야 합니다2.  
* **의존성 관리**: Maven 또는 Gradle과 같은 빌드 도구를 사용하여 라이브러리의 의존성을 관리해야 합니다. 의존성 관리는 라이브러리 개발에 필요한 외부 라이브러리를 쉽게 추가하고 버전 충돌을 방지하는 데 도움을 줍니다. Maven과 Gradle은 프로젝트의 빌드, 테스트, 배포 과정을 자동화하고 의존성을 관리하는 데 필수적인 도구입니다4.  
* **라이브러리 모듈화**: 라이브러리를 모듈화하여 기능별로 분리하고 재사용성을 높여야 합니다. 모듈화는 라이브러리의 유지보수성을 향상시키고 코드의 복잡성을 줄이는 데 도움을 줍니다. 각 모듈은 특정 기능을 담당하며, 다른 모듈과 독립적으로 개발 및 테스트될 수 있습니다5.  
* **API 설계**: 라이브러리의 API를 명확하고 사용하기 쉽게 설계해야 합니다. API 설계는 라이브러리 사용자의 편의성을 높이고 라이브러리의 활용도를 높이는 데 중요한 역할을 합니다. API는 라이브러리가 제공하는 기능을 외부에서 사용할 수 있도록 하는 인터페이스입니다. RESTful API 설계 원칙을 따르는 것이 좋습니다. RESTful API는 리소스를 중심으로 설계되며, HTTP 메서드 (GET, POST, PUT, DELETE)를 사용하여 리소스에 대한 작업을 수행합니다. 리소스는 명사를 사용하여 이름을 지정하고, HTTP 메서드는 해당 리소스에 대한 작업을 나타냅니다6.  
* **테스트**: 라이브러리의 기능을 검증하기 위해 단위 테스트를 작성해야 합니다. 테스트는 라이브러리의 안정성을 확보하고 버그를 예방하는 데 도움을 줍니다. 단위 테스트는 코드의 각 부분을 독립적으로 테스트하여 오류를 조기에 발견하고 수정할 수 있도록 합니다8.  
* **문서화**: 라이브러리 사용 방법을 설명하는 문서를 작성해야 합니다. 문서화는 라이브러리 사용자의 이해를 돕고 라이브러리의 사용성을 높이는 데 중요합니다. 문서에는 라이브러리의 기능, API 사용 방법, 예제 코드 등이 포함되어야 합니다9.  
* **버전 관리**: Git과 같은 버전 관리 시스템을 사용하여 라이브러리의 코드 변경 이력을 관리해야 합니다. 버전 관리는 협업을 용이하게 하고 코드의 안정성을 유지하는 데 도움을 줍니다. Git은 코드의 변경 사항을 추적하고 이전 버전으로 되돌릴 수 있는 기능을 제공합니다10.

## **2\. 권한 인증 및 접근 제어**

Spring Boot에서 권한 인증 및 접근 제어를 구현하는 방법은 크게 두 가지로 나눌 수 있습니다.

### **2.1 MVC 필터 직접 구현**

Servlet API를 기반으로 MVC 필터를 직접 구현하여 권한 인증 및 접근 제어를 구현할 수 있습니다. 이 방법은 개발자가 직접 인증 및 권한 검증 로직을 구현해야 하므로 높은 자유도를 제공하지만, 보안 취약점에 대한 고려가 필요합니다11.

### **2.2 Spring Security 활용**

Spring Security는 Spring 기반 애플리케이션의 보안을 위한 강력한 프레임워크입니다. Spring Security를 사용하면 인증, 인가, 세션 관리 등 다양한 보안 기능을 쉽게 구현할 수 있습니다12. Spring Security는 광범위한 보안 기능을 제공하며, 개발자가 직접 보안 로직을 구현하는 것보다 안전하고 효율적인 방법입니다. Spring Security를 사용하면 개발 시간을 단축하고 보안 취약점 발생 가능성을 줄일 수 있습니다. 또한, Spring Security는 지속적인 업데이트와 커뮤니티 지원을 통해 최신 보안 위협에 대한 대응을 용이하게 합니다13.

Spring Security를 사용할 때 고려해야 할 사항은 다음과 같습니다.

* **UserDetailsService**: Spring Security의 UserDetailsService는 사용자 정보를 로드하는 인터페이스입니다. 개발자는 UserDetailsService를 구현하여 사용자 정보를 데이터베이스에서 가져오거나, LDAP 서버에서 가져오는 등 다양한 방법으로 사용자 정보를 로드할 수 있습니다. UserDetailsService는 Spring Security의 인증 과정에서 사용자 정보를 가져오는 데 사용됩니다14.  
* **Spring Security 설정**: Spring Security의 설정을 통해 인증 방식, 권한 계층 구조 등을 정의해야 합니다. Spring Security는 다양한 설정 옵션을 제공하며, 개발자는 애플리케이션의 요구사항에 맞게 설정을 변경할 수 있습니다15.  
* **보안 취약점 방지**: Spring Security는 다양한 보안 취약점 방지 기능을 제공하지만, 개발자는 CSRF (Cross-Site Request Forgery), XSS (Cross-Site Scripting), SQL Injection 등 웹 공격에 대한 방어 로직을 추가적으로 구현해야 합니다. Spring Security는 CSRF 방지를 위한 토큰 기반 방어 기능을 제공하며, XSS 방지를 위한 출력 인코딩 기능을 제공합니다. SQL Injection 방지를 위해서는 Prepared Statement를 사용하는 것이 좋습니다16.

MVC 필터를 직접 구현할 때 주의해야 할 사항은 다음과 같습니다.

* **보안 취약점 방지**: 개발자는 직접 구현한 인증 및 권한 검증 로직에서 발생할 수 있는 보안 취약점을 예방해야 합니다. 인증 및 권한 검증 로직은 웹 공격에 취약할 수 있으므로, 입력값 검증, 출력 인코딩 등 보안 조치를 철저히 해야 합니다17.  
* **필터 체인 구성**: 여러 개의 필터를 사용할 경우 필터 체인을 통해 필터의 실행 순서를 정의해야 합니다. 필터 체인은 여러 개의 필터를 순차적으로 실행하는 메커니즘입니다. 필터의 실행 순서는 애플리케이션의 동작에 영향을 미칠 수 있으므로, 주의 깊게 설정해야 합니다18.

## **3\. 소셜 네트워크 연동**

Spring Social은 소셜 네트워크 연동을 위한 Spring Framework의 확장 모듈입니다. Spring Social을 사용하면 Facebook, Google, Kakao 등 다양한 소셜 네트워크 API에 쉽게 연동할 수 있습니다19. 하지만, Spring Social은 현재 더 이상 활발하게 개발되지 않고 있으며, 일부 API 바인딩은 더 이상 지원되지 않습니다20. 따라서 최신 소셜 네트워크 API를 사용하려면 Spring Social 대신 각 소셜 네트워크에서 제공하는 SDK를 사용하는 것이 좋습니다.

소셜 네트워크 연동을 위해서는 다음과 같은 지식이 필요합니다.

* **OAuth 2.0**: OAuth 2.0은 권한 부여를 위한 표준 프로토콜입니다. 소셜 네트워크 연동 시 사용자의 계정 정보에 접근하기 위해 OAuth 2.0을 사용합니다. OAuth 2.0은 사용자가 자신의 계정 정보를 제3자 애플리케이션에 직접 제공하지 않고도, 제3자 애플리케이션이 사용자의 계정 정보에 접근할 수 있도록 하는 프로토콜입니다21.  
* **API 연동**: 각 소셜 네트워크에서 제공하는 API를 사용하여 사용자 정보를 가져오거나 게시글을 작성하는 등의 작업을 수행해야 합니다. 각 소셜 네트워크는 다양한 API를 제공하며, 개발자는 애플리케이션의 요구사항에 맞는 API를 선택하여 사용할 수 있습니다22.  
* **API 바인딩**: Spring Social은 다양한 소셜 네트워크 API에 대한 바인딩을 제공합니다. API 바인딩은 소셜 네트워크 API를 Java 객체로 매핑하여 개발자가 쉽게 API를 사용할 수 있도록 합니다. Spring Social은 Facebook, Twitter, LinkedIn 등 주요 소셜 네트워크에 대한 API 바인딩을 제공합니다23.

소셜 네트워크 연동 시 발생할 수 있는 문제점은 다음과 같습니다.

* **API 변경**: 소셜 네트워크 API는 수시로 변경될 수 있습니다. API 변경은 애플리케이션의 동작에 영향을 미칠 수 있으므로, API 변경 사항을 지속적으로 확인하고 애플리케이션을 업데이트해야 합니다.  
* **Rate limiting**: 소셜 네트워크는 API 호출 횟수를 제한하는 경우가 많습니다. Rate limiting을 초과하면 API 호출이 차단될 수 있으므로, Rate limiting을 고려하여 애플리케이션을 설계해야 합니다.

## **4\. Central Repository 업로드**

개발한 라이브러리를 Maven Central Repository에 업로드하여 다른 개발자들이 쉽게 사용할 수 있도록 배포할 수 있습니다. Maven Central은 Java 라이브러리를 위한 공개 저장소입니다15.

Central Repository에 라이브러리를 업로드하는 절차는 다음과 같습니다.

* **Sonatype Nexus**: Sonatype Nexus는 Maven Central Repository에 라이브러리를 배포하기 위한 플랫폼입니다. Sonatype Nexus를 통해 라이브러리를 업로드하고 배포할 수 있습니다. Sonatype Nexus는 라이브러리 배포 과정을 관리하고, 라이브러리의 품질을 검증하는 기능을 제공합니다24.  
* **pom.xml 설정**: 라이브러리의 pom.xml 파일을 설정하여 라이브러리 정보, 의존성 정보, 배포 정보 등을 정의해야 합니다. pom.xml 파일은 Maven 프로젝트의 설정 파일입니다. pom.xml 파일에는 라이브러리의 이름, 버전, 설명, 라이선스, 개발자 정보, 의존성 정보, 배포 정보 등이 포함됩니다25.  
* **GPG 서명**: GPG (GNU Privacy Guard)를 사용하여 라이브러리에 디지털 서명을 해야 합니다. GPG 서명은 라이브러리의 무결성을 보장하고 출처를 확인하는 데 사용됩니다. GPG 서명은 라이브러리 파일의 내용이 변경되지 않았음을 증명하고, 라이브러리가 신뢰할 수 있는 출처에서 배포되었음을 보장합니다. GPG 서명을 통해 사용자는 라이브러리를 안전하게 사용할 수 있습니다26.  
* **업로드 및 배포 자동화**: Maven 또는 Gradle의 플러그인을 사용하여 라이브러리 업로드 및 배포 과정을 자동화할 수 있습니다. 자동화를 통해 라이브러리 배포 과정을 간소화하고, 배포 오류를 줄일 수 있습니다. GitHub Actions는 GitHub에서 제공하는 CI/CD 도구입니다. GitHub Actions를 사용하면 코드 변경 시 자동으로 빌드, 테스트, 배포를 수행할 수 있습니다28.

## **5\. Spring Boot 라이브러리 구성**

Spring Boot에서 라이브러리를 구성하기 위해 다음과 같은 기능을 활용할 수 있습니다.

* **Auto-configuration**: Spring Boot의 자동 설정 기능을 사용하여 라이브러리 사용자가 라이브러리를 쉽게 설정하고 사용할 수 있도록 합니다. 자동 설정은 Spring Boot의 핵심 기능 중 하나입니다. Spring Boot는 애플리케이션의 클래스패스에 있는 라이브러리를 감지하고, 해당 라이브러리에 필요한 설정을 자동으로 구성합니다3.  
* **@EnableAutoConfiguration**: @EnableAutoConfiguration 어노테이션은 Spring Boot 애플리케이션의 자동 설정 기능을 활성화합니다. 이 어노테이션은 @SpringBootApplication 어노테이션에 포함되어 있습니다. @EnableAutoConfiguration 어노테이션을 사용하면 Spring Boot가 애플리케이션의 클래스패스에 있는 라이브러리를 감지하고, 해당 라이브러리에 필요한 설정을 자동으로 구성합니다30.  
* **Starter**: Starter는 특정 기능을 사용하기 위한 의존성을 모아놓은 패키지입니다. 라이브러리에 필요한 의존성을 Starter로 제공하여 사용자가 편리하게 라이브러리를 사용할 수 있도록 합니다. Starter는 Spring Boot 애플리케이션에서 특정 기능을 쉽게 사용할 수 있도록 해주는 편리한 기능입니다. 예를 들어, Spring Web MVC를 사용하려면 spring-boot-starter-web Starter를 의존성에 추가하면 됩니다31.  
* **Conditionals**: Conditionals는 특정 조건에 따라 빈을 생성하거나 설정을 적용하는 기능입니다. Conditionals를 사용하여 라이브러리의 동작을 조건부로 제어할 수 있습니다. Conditionals는 Spring Boot 애플리케이션의 설정을 조건부로 적용하는 데 사용됩니다. 예를 들어, 특정 클래스가 클래스패스에 존재하는 경우에만 특정 설정을 적용할 수 있습니다32.

## **6\. 좋은 라이브러리 설계**

좋은 라이브러리를 설계하기 위해 다음과 같은 요소를 고려해야 합니다.

* **모듈화**: 라이브러리를 모듈화하여 기능별로 분리하고 재사용성을 높입니다. 모듈화는 코드의 복잡성을 줄이고 유지보수성을 향상시키는 데 도움이 됩니다. 각 모듈은 특정 기능을 담당하며, 다른 모듈과 독립적으로 개발 및 테스트될 수 있습니다33.  
* **확장성**: 라이브러리의 기능을 쉽게 확장할 수 있도록 설계합니다. 확장성은 라이브러리의 미래 요구사항에 대응할 수 있는 능력입니다. 라이브러리를 확장 가능하도록 설계하면 새로운 기능을 쉽게 추가하고 기존 기능을 수정할 수 있습니다34.  
* **유지보수 용이성**: 라이브러리의 코드를 이해하고 수정하기 쉽도록 설계합니다. 유지보수 용이성은 라이브러리의 버그를 수정하고 기능을 개선하는 데 필요한 노력을 줄이는 데 도움이 됩니다. 코드를 명확하고 간결하게 작성하고, 주석을 충분히 추가하여 코드의 가독성을 높이는 것이 중요합니다35.  
* **API 설계**: 라이브러리의 API를 명확하고 사용하기 쉽게 설계합니다. API는 라이브러리가 제공하는 기능을 외부에서 사용할 수 있도록 하는 인터페이스입니다. API를 명확하고 일관성 있게 설계하면 사용자가 라이브러리를 쉽게 사용할 수 있습니다36.  
* **네이밍 규칙**: 일관된 네이밍 규칙을 적용하여 코드의 가독성을 높입니다. 네이밍 규칙은 코드를 이해하고 유지보수하는 데 도움이 됩니다. 일관된 네이밍 규칙을 사용하면 코드의 가독성을 높이고 오류 발생 가능성을 줄일 수 있습니다37.  
* **문서화**: 라이브러리 사용 방법을 설명하는 문서를 작성합니다. 문서는 라이브러리 사용자가 라이브러리를 이해하고 사용하는 데 도움이 됩니다. 문서에는 라이브러리의 기능, API 사용 방법, 예제 코드 등이 포함되어야 합니다.  
* **테스트 커버리지**: 충분한 테스트를 통해 라이브러리의 품질을 보장합니다. 테스트는 라이브러리의 버그를 발견하고 수정하는 데 도움이 됩니다. 테스트 커버리지를 높이면 라이브러리의 품질을 향상시키고 안정성을 확보할 수 있습니다.

## **7\. 추가 정보**

Spring Boot 라이브러리 개발 및 배포 관련 최신 정보는 Spring 공식 웹사이트 및 관련 블로그를 참고하십시오. Spring Security 및 소셜 네트워크 연동 관련 라이브러리 및 기술 동향은 Spring Security 공식 웹사이트 및 각 소셜 네트워크 개발자 사이트를 참고하십시오38.

## **8\. MVC 필터와 Spring Security 선택 가이드**

Spring Boot 애플리케이션에서 권한 인증 및 접근 제어를 구현할 때, MVC 필터를 직접 구현하는 방법과 Spring Security를 사용하는 방법 중 하나를 선택해야 합니다. 두 가지 방법 모두 장단점이 있으며, 프로젝트의 특성과 요구사항에 따라 적합한 방법을 선택해야 합니다.

### **8.1 MVC 필터 vs Spring Security 비교 분석**

| 구분 | MVC 필터 직접 구현 | Spring Security 활용 | 예시 |
| :---- | :---- | :---- | :---- |
| 보안성 | 낮음 (개발자의 역량에 따라 달라짐) | 높음 (Spring Security의 검증된 보안 기능 활용) | 간단한 인증 로직, 특정 API에 대한 접근 제어 |
| 개발 편의성 | 낮음 (직접 구현해야 하는 부분이 많음) | 높음 (Spring Security의 자동 설정 및 기능 활용) | 개발 초기 단계, 빠른 개발 필요 |
| 유지보수 용이성 | 낮음 (직접 구현한 코드를 유지보수해야 함) | 높음 (Spring Security의 업데이트 및 커뮤니티 지원) | 소규모 프로젝트, 유지보수 인력 부족 |
| 성능 | 개발자의 역량에 따라 달라짐 | Spring Security의 최적화된 성능 활용 | 성능이 중요하지 않은 경우 |
| 확장성 | 낮음 (직접 구현한 코드를 확장해야 함) | 높음 (Spring Security의 확장 기능 활용) | 기능 확장 예정 없음 |
| 커스터마이징 | 높음 (자유롭게 커스터마이징 가능) | 제한적 (Spring Security의 설정 범위 내에서 커스터마이징) | 특정 요구사항 충족 필요 |
| 학습 곡선 | 낮음 (Servlet API 기반) | 높음 (Spring Security 개념 학습 필요) | Spring Security 경험 부족 |

### **8.2 선택 가이드**

* **프로젝트 복잡도**: 프로젝트의 규모가 크고 복잡할수록 Spring Security를 사용하는 것이 유리합니다. Spring Security는 복잡한 권한 계층 구조를 관리하고 다양한 인증 방식을 지원하는 데 효과적입니다.  
* **보안 요구사항**: 보안 요구사항이 높은 프로젝트일수록 Spring Security를 사용하는 것이 좋습니다. Spring Security는 검증된 보안 기능을 제공하며, 보안 취약점 발생 가능성을 줄일 수 있습니다.  
* **개발자 전문성**: Spring Security에 대한 경험이 부족한 경우 MVC 필터를 직접 구현하는 것이 더 쉬울 수 있습니다. 하지만, Spring Security는 학습 곡선이 가파르지만 장기적으로 보안 및 유지보수 측면에서 이점을 제공합니다.

## **9\. 결론**

Spring Boot를 사용하여 권한 인증, 접근 제어 및 소셜 네트워크 연동 기능을 제공하는 라이브러리를 개발하는 것은 효율적인 방법입니다. Spring Security와 Spring Social을 활용하면 개발 편의성을 높이고 보안성을 강화할 수 있습니다. Central Repository에 라이브러리를 배포하여 다른 개발자들과 공유할 수도 있습니다. 라이브러리 개발 시 좋은 설계 원칙을 적용하여 모듈화, 확장성, 유지보수 용이성을 높이는 것이 중요합니다. 특히, Spring Security를 사용할 때는 UserDetailsService를 활용하여 인증을 커스터마이징하고, 다양한 설정 옵션을 통해 애플리케이션의 요구사항에 맞게 Spring Security를 구성해야 합니다. 소셜 네트워크 연동 시에는 API 변경 및 Rate limiting과 같은 문제점을 고려해야 하며, Spring Social 대신 각 소셜 네트워크에서 제공하는 SDK를 사용하는 것을 고려할 수 있습니다. 마지막으로, 라이브러리 배포 시 GPG 서명을 통해 라이브러리의 신뢰성을 확보하고, GitHub Actions와 같은 도구를 사용하여 배포 과정을 자동화하는 것이 좋습니다. Spring Boot, Spring Security, 소셜 네트워크 연동 기술은 지속적으로 발전하고 있으므로, 최신 정보를 꾸준히 학습하고 적용하는 것이 중요합니다.

#### **참고 자료**

1\. 스프링 프레임워크의 핵심 개념: DI와 IOC 이해하기 \- F-Lab, 12월 29, 2024에 액세스, [https://f-lab.kr/insight/understanding-spring-framework-core-concepts](https://f-lab.kr/insight/understanding-spring-framework-core-concepts)  
2\. \[Java\] Spring Boot \- 자동 환경 설정 \- @SpringBootApplication \- 네이버 블로그, 12월 29, 2024에 액세스, [https://m.blog.naver.com/seek316/222385061638](https://m.blog.naver.com/seek316/222385061638)  
3\. Auto Configuration \- 자바시작 워니, 12월 29, 2024에 액세스, [https://oneny.tistory.com/83](https://oneny.tistory.com/83)  
4\. Maven vs Gradle, 20년차 개발자는 실무에서 이렇게 활용합니다. \- 이랜서, 12월 29, 2024에 액세스, [https://www.elancer.co.kr/blog/detail/270](https://www.elancer.co.kr/blog/detail/270)  
5\. Chapter 10 \- 라이브러리와 모듈 \- velog, 12월 29, 2024에 액세스, [https://velog.io/@dev-taewon-kim/Chapter-10-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC%EC%99%80-%EB%AA%A8%EB%93%88](https://velog.io/@dev-taewon-kim/Chapter-10-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC%EC%99%80-%EB%AA%A8%EB%93%88)  
6\. API 디자인 가이드 | Cloud API Design Guide, 12월 29, 2024에 액세스, [https://cloud.google.com/apis/design?hl=ko](https://cloud.google.com/apis/design?hl=ko)  
7\. RESTful API 설계 가이드 \- 가비아 라이브러리, 12월 29, 2024에 액세스, [https://library.gabia.com/contents/8339/](https://library.gabia.com/contents/8339/)  
8\. 방구석연구소, 12월 29, 2024에 액세스, [https://www.banggooso.com/](https://www.banggooso.com/)  
9\. ko.wikipedia.org, 12월 29, 2024에 액세스, [https://ko.wikipedia.org/wiki/%EB%AC%B8%EC%84%9C%ED%99%94\#:\~:text=%EB%AC%B8%EC%84%9C%ED%99%94(%E6%96%87%E6%9B%B8%E5%8C%96)%20%EB%98%90%EB%8A%94%20%EB%8F%84%ED%81%90%EB%A9%98%ED%85%8C%EC%9D%B4%EC%85%98,%EA%B2%83%EC%9D%80%20%EC%A0%90%EC%B0%A8%20%EB%93%9C%EB%AC%BC%EC%96%B4%EC%A7%80%EA%B3%A0%20%EC%9E%88%EB%8B%A4.](https://ko.wikipedia.org/wiki/%EB%AC%B8%EC%84%9C%ED%99%94#:~:text=%EB%AC%B8%EC%84%9C%ED%99%94\(%E6%96%87%E6%9B%B8%E5%8C%96\)%20%EB%98%90%EB%8A%94%20%EB%8F%84%ED%81%90%EB%A9%98%ED%85%8C%EC%9D%B4%EC%85%98,%EA%B2%83%EC%9D%80%20%EC%A0%90%EC%B0%A8%20%EB%93%9C%EB%AC%BC%EC%96%B4%EC%A7%80%EA%B3%A0%20%EC%9E%88%EB%8B%A4.)  
10\. 버전 관리란? \- Git, 12월 29, 2024에 액세스, [https://git-scm.com/book/ko/v2/%EC%8B%9C%EC%9E%91%ED%95%98%EA%B8%B0-%EB%B2%84%EC%A0%84-%EA%B4%80%EB%A6%AC%EB%9E%80%3F](https://git-scm.com/book/ko/v2/%EC%8B%9C%EC%9E%91%ED%95%98%EA%B8%B0-%EB%B2%84%EC%A0%84-%EA%B4%80%EB%A6%AC%EB%9E%80%3F)  
11\. Servlet Filter란? \- 테오의 학습기록 \- 티스토리, 12월 29, 2024에 액세스, [https://dev-ws.tistory.com/99](https://dev-ws.tistory.com/99)  
12\. Spring Security란? 사용하는 이유부터 설정 방법까지 알려드립니다\! \- 이랜서, 12월 29, 2024에 액세스, [https://www.elancer.co.kr/blog/detail/235](https://www.elancer.co.kr/blog/detail/235)  
13\. Spring Security: Part 1\. 인증과 인가 아키텍처의 이해와 실전 활용 \- 이글루코퍼레이션, 12월 29, 2024에 액세스, [https://www.igloo.co.kr/security-information/spring-security-part1-%EC%9D%B8%EC%A6%9D%EA%B3%BC-%EC%9D%B8%EA%B0%80-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98%EC%9D%98-%EC%9D%B4%ED%95%B4%EC%99%80-%EC%8B%A4%EC%A0%84-%ED%99%9C%EC%9A%A9/](https://www.igloo.co.kr/security-information/spring-security-part1-%EC%9D%B8%EC%A6%9D%EA%B3%BC-%EC%9D%B8%EA%B0%80-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98%EC%9D%98-%EC%9D%B4%ED%95%B4%EC%99%80-%EC%8B%A4%EC%A0%84-%ED%99%9C%EC%9A%A9/)  
14\. \[스프링 시큐리티\] Spring Security란? (개념, 특징, CSRF, 인증/권한 설정) \- 니나노, 12월 29, 2024에 액세스, [https://yuna-ninano.tistory.com/entry/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0-Spring-Security%EB%9E%80-%EA%B0%9C%EB%85%90-%ED%8A%B9%EC%A7%95-CSRF-%EC%9D%B8%EC%A6%9D%EA%B6%8C%ED%95%9C-%EC%84%A4%EC%A0%95](https://yuna-ninano.tistory.com/entry/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0-Spring-Security%EB%9E%80-%EA%B0%9C%EB%85%90-%ED%8A%B9%EC%A7%95-CSRF-%EC%9D%B8%EC%A6%9D%EA%B6%8C%ED%95%9C-%EC%84%A4%EC%A0%95)  
15\. Spring \- Spring Security (기본 설정, 로그인 폼 커스텀, UserDetailService, 역할 및 권한, remeberMe(자동 로그인)) \- 잇\!(IT) 블로그 \- 티스토리, 12월 29, 2024에 액세스, [https://insoobaik.tistory.com/531](https://insoobaik.tistory.com/531)  
16\. 위협 및 취약점 관리 \- Microsoft Service Assurance, 12월 29, 2024에 액세스, [https://learn.microsoft.com/ko-kr/compliance/assurance/assurance-vulnerability-management](https://learn.microsoft.com/ko-kr/compliance/assurance/assurance-vulnerability-management)  
17\. 보안 취약점 \- 위키백과, 우리 모두의 백과사전, 12월 29, 2024에 액세스, [https://ko.wikipedia.org/wiki/%EB%B3%B4%EC%95%88\_%EC%B7%A8%EC%95%BD%EC%A0%90](https://ko.wikipedia.org/wiki/%EB%B3%B4%EC%95%88_%EC%B7%A8%EC%95%BD%EC%A0%90)  
18\. 예: 방화벽 필터 체인 사용 | Junos OS \- Juniper Networks, 12월 29, 2024에 액세스, [https://www.juniper.net/documentation/kr/ko/software/junos/routing-policy/topics/example/firewall-filter-option-filter-chain-example.html](https://www.juniper.net/documentation/kr/ko/software/junos/routing-policy/topics/example/firewall-filter-option-filter-chain-example.html)  
19\. Spring Social Reference, 12월 29, 2024에 액세스, [https://docs.spring.io/spring-social/docs/current/reference/htmlsingle/](https://docs.spring.io/spring-social/docs/current/reference/htmlsingle/)  
20\. Spring Social End of Life Announcement, 12월 29, 2024에 액세스, [https://spring.io/blog/2018/07/03/spring-social-end-of-life-announcement/](https://spring.io/blog/2018/07/03/spring-social-end-of-life-announcement/)  
21\. An Introduction to OAuth 2 \- DigitalOcean, 12월 29, 2024에 액세스, [https://www.digitalocean.com/community/tutorials/an-introduction-to-oauth-2](https://www.digitalocean.com/community/tutorials/an-introduction-to-oauth-2)  
22\. 애플리케이션 프로그래밍 인터페이스(API)란 무엇인가요? \- AWS, 12월 29, 2024에 액세스, [https://aws.amazon.com/ko/what-is/api/](https://aws.amazon.com/ko/what-is/api/)  
23\. 1\. Spring Social Overview, 12월 29, 2024에 액세스, [https://docs.spring.io/spring-social/docs/1.0.x/reference/html/overview.html](https://docs.spring.io/spring-social/docs/1.0.x/reference/html/overview.html)  
24\. Maven Repositories \- Sonatype Help, 12월 29, 2024에 액세스, [https://help.sonatype.com/en/maven-repositories.html](https://help.sonatype.com/en/maven-repositories.html)  
25\. 애플리케이션 빌드를 위한 Maven pom.xml의 plugin 설정 \- Jenkins \- KIC 지식 스페이스, 12월 29, 2024에 액세스, [https://wiki.kicco.com/space/JEN/44728377/%EC%95%A0%ED%94%8C%EB%A6%AC%EC%BC%80%EC%9D%B4%EC%85%98+%EB%B9%8C%EB%93%9C%EB%A5%BC+%EC%9C%84%ED%95%9C+Maven+pom.xml%EC%9D%98+plugin+%EC%84%A4%EC%A0%95](https://wiki.kicco.com/space/JEN/44728377/%EC%95%A0%ED%94%8C%EB%A6%AC%EC%BC%80%EC%9D%B4%EC%85%98+%EB%B9%8C%EB%93%9C%EB%A5%BC+%EC%9C%84%ED%95%9C+Maven+pom.xml%EC%9D%98+plugin+%EC%84%A4%EC%A0%95)  
26\. 서명 키에 대해 Git에 알리기 \- GitHub Docs, 12월 29, 2024에 액세스, [https://docs.github.com/ko/authentication/managing-commit-signature-verification/telling-git-about-your-signing-key](https://docs.github.com/ko/authentication/managing-commit-signature-verification/telling-git-about-your-signing-key)  
27\. GPG 키로 github commit에 서명하기 \- BeomHwan Roh, 12월 29, 2024에 액세스, [https://robomoan.medium.com/gpg-%ED%82%A4%EB%A1%9C-github-commit%EC%97%90-%EC%84%9C%EB%AA%85%ED%95%98%EA%B8%B0-e8fc0a52ec61](https://robomoan.medium.com/gpg-%ED%82%A4%EB%A1%9C-github-commit%EC%97%90-%EC%84%9C%EB%AA%85%ED%95%98%EA%B8%B0-e8fc0a52ec61)  
28\. 배포 자동화(Deployment Automation)란? 개념, 장점, 기능, 설치 \- Red Hat, 12월 29, 2024에 액세스, [https://www.redhat.com/ko/topics/automation/what-is-deployment-automation](https://www.redhat.com/ko/topics/automation/what-is-deployment-automation)  
29\. GitHub Actions으로 배포 자동화해 보기(a.k.a CI/CD) \- 1화 \- 골든래빗, 12월 29, 2024에 액세스, [https://goldenrabbit.co.kr/2023/07/05/github-actions%EB%A1%9C-%EB%B0%B0%ED%8F%AC-%EC%9E%90%EB%8F%99%ED%99%94%ED%95%B4-%EB%B3%B4%EA%B8%B0a-k-a-ci-cd-1%ED%8E%B8/](https://goldenrabbit.co.kr/2023/07/05/github-actions%EB%A1%9C-%EB%B0%B0%ED%8F%AC-%EC%9E%90%EB%8F%99%ED%99%94%ED%95%B4-%EB%B3%B4%EA%B8%B0a-k-a-ci-cd-1%ED%8E%B8/)  
30\. \[스프링 부트 개념과 활용\] 자동 설정 (Auto Configuration) \- Just in case \- 티스토리, 12월 29, 2024에 액세스, [https://zion830.tistory.com/112](https://zion830.tistory.com/112)  
31\. \[spring\] SpringBoot의 AutoConfiguration 동작방식 \- JH-Labs \- 티스토리, 12월 29, 2024에 액세스, [https://jh-labs.tistory.com/337](https://jh-labs.tistory.com/337)  
32\. Creating Your Own Auto-configuration | 토리맘의 한글라이즈 프로젝트, 12월 29, 2024에 액세스, [https://godekdls.github.io/Spring%20Boot/creating-your-own-auto-configuration/](https://godekdls.github.io/Spring%20Boot/creating-your-own-auto-configuration/)  
33\. 테스트 자동화 기초: 시작하기 전 알아야 할 것들 : Article \- James Company, 12월 29, 2024에 액세스, [https://www.jamescompany.kr/blog/?bmode=view\&idx=19135970](https://www.jamescompany.kr/blog/?bmode=view&idx=19135970)  
34\. 소프트웨어 공학 \- 실기 정리 노트, 12월 29, 2024에 액세스, [https://velog.io/@leeboa2003/%EC%86%8C%ED%94%84%ED%8A%B8%EC%9B%A8%EC%96%B4-%EA%B5%AC%EC%A1%B0](https://velog.io/@leeboa2003/%EC%86%8C%ED%94%84%ED%8A%B8%EC%9B%A8%EC%96%B4-%EA%B5%AC%EC%A1%B0)  
35\. 프론트엔드 테스트 전략: 단위, 통합, E2E 테스트 \- 재능넷, 12월 29, 2024에 액세스, [https://www.jaenung.net/tree/796](https://www.jaenung.net/tree/796)  
36\. \[요약\] 구글 엔지니어는 이렇게 일한다 \- 토스페이먼츠, 12월 29, 2024에 액세스, [https://tosspayments-dev.oopy.io/share/books/software-engineering-at-google](https://tosspayments-dev.oopy.io/share/books/software-engineering-at-google)  
37\. 函대규모 C++ 프로젝트 구조화와 관리 전략 \- 재능넷, 12월 29, 2024에 액세스, [https://www.jaenung.net/tree/6813](https://www.jaenung.net/tree/6813)  
38\. 스프링 부트의 10가지 필수 기능: 강력하고 확장 가능한 애플리케이션 구축, 12월 29, 2024에 액세스, [https://rhgustmfrh.tistory.com/221](https://rhgustmfrh.tistory.com/221)