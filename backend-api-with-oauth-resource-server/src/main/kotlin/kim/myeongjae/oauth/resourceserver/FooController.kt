package kim.myeongjae.oauth.resourceserver

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/foo")
class FooController {

    @GetMapping
    fun getFoo(): FooResponse {
        return FooResponse()
    }
}
