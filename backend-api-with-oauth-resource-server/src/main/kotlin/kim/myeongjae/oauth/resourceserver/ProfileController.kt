package kim.myeongjae.oauth.resourceserver

import kim.myeongjae.oauth.resourceserver.member.MyMember
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/profile")
class ProfileController {

    @GetMapping
    fun getFoo(@AuthenticationPrincipal myMember: MyMember): MyMember {
        return myMember
    }
}
