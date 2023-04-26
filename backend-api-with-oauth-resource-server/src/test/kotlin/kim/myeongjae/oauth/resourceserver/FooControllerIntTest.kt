package kim.myeongjae.oauth.resourceserver

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FooControllerIntTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun getFoo() {
        mockMvc.get("/foo")
            .andExpect {
                status { isOk() }
                content { contentType("application/json") }
                jsonPath("$.foo") { value("bar") }
            }
    }
}
