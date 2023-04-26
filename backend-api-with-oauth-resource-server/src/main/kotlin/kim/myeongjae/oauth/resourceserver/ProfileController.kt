package kim.myeongjae.oauth.resourceserver

import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/profile")
class ProfileController {

    @GetMapping
    fun getFoo(principal: Principal): Jwt {
        return (principal as JwtAuthenticationToken).token
    }
}
