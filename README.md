# OAuth Example

프론트엔드에서 SSO 로그인으로 토큰을 얻고 백엔드에서 토큰으로 인증(Authentication)과 인가(Authorization)를 하는 예제입니다.

1. 프론트엔드에서 로그인해서 token(jwt)을 얻습니다.
2. 백엔드에 보내는 API 요청에 위 토큰을 Bearer 토큰 헤더로 넣습니다.
3. 백엔드에서는 토큰을 검증하고, 검증이 완료되면 요청을 처리합니다.
   1. Provider(구글)에게 jwt 검증 요청 api를 호출하지 않고 jwt자체의 정보만으로 검증합니다.
      1. Provider에게 jwt 토큰 검증 요청을 보낼수도 있지만, Custom validator를 구현해야 합니다.

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