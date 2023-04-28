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
            headers { setBearerAuth("eyJhbGciOiJSUzI1NiIsImtpZCI6ImM5YWZkYTM2ODJlYmYwOWViMzA1NWMxYzRiZDM5Yjc1MWZiZjgxOTUiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJuYmYiOjE2ODI2NTMyMjksImF1ZCI6IjQ1NjAzMjg2NTE0OC01c2lqdTg5MHJxZnNqczJocGg0YnM5bGNpczFlNWFrNy5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjExNDE1Mjk1ODk0NzU0NjY4NTc5OCIsImVtYWlsIjoiem9kc21qQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhenAiOiI0NTYwMzI4NjUxNDgtNXNpanU4OTBycWZzanMyaHBoNGJzOWxjaXMxZTVhazcuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJuYW1lIjoiTXllb25namFlIEtpbSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BR05teXhhVDB5YWVOdk9JTmNpdFIzNGxiUXFIMjl5LXJnUlFpRFlZb0o3Tz1zOTYtYyIsImdpdmVuX25hbWUiOiJNeWVvbmdqYWUiLCJmYW1pbHlfbmFtZSI6IktpbSIsImlhdCI6MTY4MjY1MzUyOSwiZXhwIjoxNjgyNjU3MTI5LCJqdGkiOiI4MmVlY2JlNTk3MGUyMDU3ODAwMjRkZmM3Y2M2MDA5YjM4NTUzYzk5In0.n0wIxOluwJXgYonvfDO6utEWcHD77_QAt4cqNBg-HABJOQjkIJ1qwdbixUqNGiVkzN88yROEpimI-58-j79jfSk6Qz93VxdI_8s5fVbbr4miHKvueEowv0Qav_vrMgVyxKWsg9UmhiQGfYpHnovR5yhZUcuqz4G8I4EEclMY-nzp9JUkxpenudt1bJBX0iChaj6uWp8wFhU5mWzLzD6F1l6N5n_2TiStSCB99E5u1py6xrXTD71BRKk6gqJVPcwmsnyVELB6_8FY0pm6IvtZJhWzHrvV6nw-YgNer_E7hDLoVzTRKwVozaVi_t0_BQ_4kZw3gfW7LvdlIK4WgGqR0w") }
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
