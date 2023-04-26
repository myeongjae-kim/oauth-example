package kim.myeongjae.oauth.resourceserver.member

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User
import java.io.Serializable

class MyMember(
    val id: String,
    val email: String,
    private val name: String,
    val picture: String,
) : OAuth2User, Serializable {

    override fun getName(): String {
        return name
    }

    override fun <A> getAttribute(name: String): A? {
        return super.getAttribute<A>(name)
    }

    override fun getAttributes(): MutableMap<String, Any>? {
        return null
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(GrantedAuthority { "ROLE_USER" })
    }
}
