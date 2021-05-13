## 구현 내용

스프링부트를 백엔드 REST API서버로, [리액트 애플리케이션](https://github.com/boyd-dev/SimpleReactApp/tree/board) 을 프론트엔드로 하는 간단한 게시판 프로젝트입니다. 

* 백엔드(스프링 부트) 서버에서 프론트엔드(리액트) 정적 리소스를 함께 제공  
  프론트엔드 애플리케이션을 스프링 부트의 정적 리소스 위치 `resources/static`에 복사하여 하나의 서버에서 백엔드와 프론트엔드 애플리케이션을 
  실행합니다. Gradle용 Node.js 플러그인을 이용하여 같이 빌드합니다.  

  
* 백엔드 애플리케이션  
  스프링부트를 기반으로 다음과 같은 기술들이 사용되었습니다.
  - Spring Security OAuth2 (네이버, 구글)
  - JWT  
  - JPA (데이터베이스는 MySQL 8.0.23)  
  
  
* 프론트엔드 애플리케이션  
  react.js를 기반으로 프론트엔드 애플리케이션이 작성되어 있습니다. 사용된 주요 라이브러리들은 다음과 같습니다.
  
  - react-redux
  - redux-saga
  - axios
  - react-router-dom
  - ag-grid-react
  - ckeditor5-react
  - styled-components
  - react-js-pagination
  - react-loader-spinner    
  
  게시글 목록은 ag-grid를, 게시글 작성에는 CKEditor5 Classic 에디터를 사용합니다. CKEditer에서 제공하는 이미지 게시판 플러그인도 포함되어 있습니다. 이미지 게시판은 글 작성시 
  업로드된 이미지가 URL로 링크되기 때문에 백엔드 서버의 특정 디렉토리에 이미지를 업로드하도록 구현되어 있습니다(프론트엔드 애플리케이션과 관련된 상세 내용은 해당 프로젝트의 
  [README](https://github.com/boyd-dev/SimpleReactApp/tree/board)를 참조).

## 실행 방법(로컬 호스트)

1. 인증 설정  
   네이버와 구글에서 제공하는 OAuth2 클라이언트 인증을 사용합니다. 따라서 미리 각 인증 공급자에 해당 애플리케이션이 등록되어 있어야 합니다.
`application.yml` 에서 설정을 변경합니다.`on-profile: dev` 부분의 `security` 설정, client-id와 client-secret을 
인증 공급자인 구글과 네이버에서 확인하여 입력합니다. redirect-uri는 구글과 네이버에서 설정한 인증성공 후 호출되는 URL입니다. 
각 인증 공급자의 설정을 `http://localhost:8080/oauth2/callback/{google or naver}`으로 설정합니다.

   인증 공급자로 부터 받은 인증 정보를 사용하여 JWT 토큰을 생성합니다. JWT가 발급되면 브라우저의 쿠키에 저장됩니다. 그 이후에는 `application.yml`의 `jwt`에 설정된 
   쿠키 만료시간이 적용됩니다. 현재 300초로 되어 있습니다. 만료된 이후에는 JWT가 없이 백엔드로 전송되므로 백엔드에서는 로그인 페이지를 리턴합니다. 
   
   이 경우에 로그인 페이지 소스가 그대로 전달되는 문제가 있으므로, 프론트엔드에서도 백엔드 API 호출시 항상 쿠키를 검사하여 없는 경우 재로그인 페이지로 이동합니다.

2. 이미지 업로드 크기 제한  
   스프링부트에서 기본적으로 MultipartResolver가 설정되어 있으므로 `spring.servlet.multipart`의 값만 설정하면 됩니다. 이미지 파일의 크기는 300KB로 제한되어 있습니다. 
CKEditor에서 이미지가 첨부되면 즉시 파일이 서버로 전송되어 디폴트 임시 디렉토리에 저장이 되고 이 파일을 다시 지정된 디렉토리로 옮깁니다. 디렉토리 지정은 `path`에 설정합니다.

   ```
   images:
      hostUrl: http://localhost:8080/uploadimages/
      path: C:\temp\images
   ``` 

   CKEditor는 게시글을 표시할 때 URL링크를 통해 이미지를 보여주게 되므로 `hostUrl`은  그에 해당하는 URL입니다. 예를 들어 `mypic.png`를 업로드 하는 경우 
백엔드 서버에서는 파일의 위치를 `http://localhost:8080/uploadimages/mypic.png`로 리턴하고 CKEditor는 이 URL을 저장합니다.    
  
3. 데이터베이스  
   데이터베이스는 MySQL 8.0.23을 사용합니다. 설정 파일의 `jdbc:` 부분을 변경하면 되겠습니다.
   
   JPA를 통해 데이터베이스에 저장하므로 관련된 상세 설정은 `MyJpaConfig.java`를 참조하시기 바랍니다. 
   `hibernate.hbm2ddl.auto`는 `update`로 설정되어 있습니다.   
   엔티티는 T_BOARD 와 T_FILE 두 개가 있지만 T_FILES은 사용되지 않습니다(나중에 일반 파일 첨부를 위해 만들어 놓은 것임). 

4. Gradle 빌드  
   스프링 부트의 build.gradle 스크립트에서 프론트엔드 애플리케이션을 빌드할 수 있습니다. 이 때 Node.js 빌드 플러그인을 사용합니다. 
   Gradle Plugin for Node 플러그인이 추가되어 있습니다. STS4에 기본으로 설치된 Gradle 플러그인을 사용하면 쉽게 빌드가 가능합니다.
   
   
   빌드는 프론트엔드 애플리케이션을 빌드하여(yarn build) 스프링부트의 정적 리소스 디렉토리로 복사하므로 반드시 해야 합니다. <b>빌드 후에 스프링 부트를 
   실행하기 전에 반드시 `resources/static`에서 F5를 눌러서 디렉토리를 갱신할 필요가 있습니다(정적 리소스는 자동 재로딩이 안되므로 변경사항이 반영되지 않을 수 있기 때문입니다).</b>


5. 프론트엔드의 라우터와 백엔드의 요청 URL 분리  
   프론트엔드와 백엔드 애플리케이션이 스프링부트 내장 톰켓에 의해 함께 구동되므로 웹브라우저의 모든 요청은 백엔드 서버가 받습니다. 백엔드 서버는 기본적으로 유효한 JWT가 있는 API 호출만 처리합니다. 
   따라서 프론트엔드의 react-router에서 라우팅되는 요청도 백엔드가 받게 됩니다. 
   
   서버 사이드 렌더링이 아니므로 이렇게 SPA 내에서의 라우팅에 의한 화면 전환이 이루어지려면 
   서버에서 react-router에 의한 요청을 구분해야 할 필요가 있습니다. 그래서 `.antMatchers("/app/**").permitAll()`을 설정하고 이 패턴에 대해서는 
   다시 클라이언트 쪽으로 포워딩하도록 설정합니다. `MyMvcConfig.java`에서
   
   ```
   @Override
   public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/app/**").setViewName("forward:/index.html");       
   }   
   ```    
   `index.html`은 프론트엔드 리액트 애플리케이션의 엔트리 페이지에 해당합니다.
   
   <img src="https://github.com/boyd-dev/SimpleSpringBootBoard/blob/main/example.PNG"/>
   