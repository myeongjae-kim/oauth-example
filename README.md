# OAuth Example

프론트엔드에서 SSO 로그인으로 토큰을 얻고 백엔드에서 토큰으로 인증(Authentication)과 인가(Authorization)를 하는 예제입니다.

1. 프론트엔드에서 로그인해서 token(jwt)을 얻습니다.
2. 백엔드에 보내는 API 요청에 위 토큰을 Bearer 토큰 헤더로 넣습니다.
3. 백엔드에서는 토큰을 검증하고, 검증이 완료되면 요청을 처리합니다.
   1. Provider(구글)의 jwt 검증 요청 api를 호출해서 토큰을 검증합니다.
   2. jwt자체의 정보만를 검증합니다.
      1. jwt가 expired 되어있는지 아닌지
      2. 토큰의 `aud` 값이 백엔드 설정의 oauth client-id와 동일한지 

Session을 사용하는 경우 `SecurityContextPersistenceFilter` 앞에 커스텀 필터를 추가해서 Jwt 토큰의 정보로 멤버 객체를 생성하고
`SecurityContextHolder.getContext().setAuthentication()` 의 매개변수에 넣으면 됩니다. (참고: https://docs.spring.io/spring-session/reference/guides/boot-jdbc.html#httpsession-jdbc-boot-how)

## 실행

### 백엔드

```bash
cd backend-api-with-oauth-resource-server
./gradlew bootRun
```

### 프론트엔드

```bash
cd frontend-with-oauth-login
pnpm install
pnpm dev
```