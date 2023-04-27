package kim.myeongjae.oauth.resourceserver

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
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
        SecurityContextHolder.getContext().authentication
        mockMvc.get("/profile") {
            headers { setBearerAuth("eyJhbGciOiJSUzI1NiIsImtpZCI6Ijg2OTY5YWVjMzdhNzc4MGYxODgwNzg3NzU5M2JiYmY4Y2Y1ZGU1Y2UiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJuYmYiOjE2ODI2MDE0MDIsImF1ZCI6IjQ1NjAzMjg2NTE0OC01c2lqdTg5MHJxZnNqczJocGg0YnM5bGNpczFlNWFrNy5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjExNDE1Mjk1ODk0NzU0NjY4NTc5OCIsImVtYWlsIjoiem9kc21qQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhenAiOiI0NTYwMzI4NjUxNDgtNXNpanU4OTBycWZzanMyaHBoNGJzOWxjaXMxZTVhazcuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJuYW1lIjoiTXllb25namFlIEtpbSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BR05teXhhVDB5YWVOdk9JTmNpdFIzNGxiUXFIMjl5LXJnUlFpRFlZb0o3Tz1zOTYtYyIsImdpdmVuX25hbWUiOiJNeWVvbmdqYWUiLCJmYW1pbHlfbmFtZSI6IktpbSIsImlhdCI6MTY4MjYwMTcwMiwiZXhwIjoxNjgyNjA1MzAyLCJqdGkiOiJlMmM4NTdiZmY3ZjI0NmUzZWUxMDMzODczMzJkNjU1NGFlMWMzZDJmIn0.iSMm94fc82kkwUZ2Hmhr_nCWvpiE4vprvix8-H1nuxNYHcyTeALZuEkS1KnlqF8pWzVHxxtD_0-RAMXq5NUiWPnoO68u7a5afwYsmEJBxzdYLtRWdiMvo-Z1pAvL8hZm8XiJZ5E4FEI9r4SrvlmyNWRDhkJ0LiunmkkIW0t5MNUye9Jfux3_vYaafdpRjyFrKYeetMeF06E3UjtCYwxyybri79kcrW3S4KYjSZT8M1KlO-sYu30wj4MbcGS2ylF-F6L95NaU9A23GlfZGazWaQWfD28_uNftcfUT4FCbk7-584vXV_or7CPXIlTCh8RyTNHAWB4lr40gtGlAB2fhHg") }
        }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.id") { isString() }
                jsonPath("$.email") { isString() }
                jsonPath("$.name") { isString() }
                jsonPath("$.picture") { isString() }
            }
    }
}
