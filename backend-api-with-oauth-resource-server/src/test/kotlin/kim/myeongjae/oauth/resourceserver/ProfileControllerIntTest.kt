package kim.myeongjae.oauth.resourceserver

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProfileControllerIntTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var oAuth2ResourceServerProperties: OAuth2ResourceServerProperties

    // frontend에서 로그인해서 받은 토큰을 bearerAuth에 넣어야 한다. 토큰이 expired되면 테스트가 실패한다.
    @Test
    fun getProfile() {
        mockMvc.get("/profile") {
            headers { setBearerAuth("eyJhbGciOiJSUzI1NiIsImtpZCI6Ijg2OTY5YWVjMzdhNzc4MGYxODgwNzg3NzU5M2JiYmY4Y2Y1ZGU1Y2UiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJuYmYiOjE2ODI0OTczMjEsImF1ZCI6IjQ1NjAzMjg2NTE0OC01c2lqdTg5MHJxZnNqczJocGg0YnM5bGNpczFlNWFrNy5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjExNDE1Mjk1ODk0NzU0NjY4NTc5OCIsImVtYWlsIjoiem9kc21qQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhenAiOiI0NTYwMzI4NjUxNDgtNXNpanU4OTBycWZzanMyaHBoNGJzOWxjaXMxZTVhazcuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJuYW1lIjoiTXllb25namFlIEtpbSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BR05teXhhVDB5YWVOdk9JTmNpdFIzNGxiUXFIMjl5LXJnUlFpRFlZb0o3Tz1zOTYtYyIsImdpdmVuX25hbWUiOiJNeWVvbmdqYWUiLCJmYW1pbHlfbmFtZSI6IktpbSIsImlhdCI6MTY4MjQ5NzYyMSwiZXhwIjoxNjgyNTAxMjIxLCJqdGkiOiJiNmNkNjQ1NDE3Mjg5MWVkNjFmN2ViYTYzNDEwZjhhZTYzNWMyNmY3In0.ETCIaOiA7GSU2WujdxsDfZCFuvTOCUOuJpSSAgEaCrJk7K8UVS1LKhdVuWxDQvZJVOxuZDRcrbE2WMM9XHHVru78hddv8eXiHBxxHrzfndVC28EdHWk5ML6vP42R2W84bIM7oii9uHdiv8dTSCgeLpO86SEA1W-9Gx_9pgMDhaZkFEjBjLUXjKtASGBv8Ld861Y8a1k3G4YM9FFhOR_4LIiqktGo9WILkbPeE3keZl26VaJMJLkQWE7oKHwQxR3PXLH7JSE1eNqwKR0FCEsQcNJJ1EyFiBAGm9y0RgnwmvlZZ3yqNosCENOT0aimxly-uPZGgQnfAbBPU13-EpmDwA") }
        }
            .andExpect {
                status { isOk() }
                content { contentType("application/json") }
                jsonPath("$.id") { isString() }
                jsonPath("$.email") { isString() }
                jsonPath("$.name") { isString() }
                jsonPath("$.picture") { isString() }
            }
    }
}
