package kim.myeongjae.oauth.resourceserver.member

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken

class JwtToMemberFilter : Filter {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication is JwtAuthenticationToken) {
            val myMember = MyMember(
                id = authentication.token.id,
                email = authentication.token.claims["email"] as String,
                name = authentication.token.claims["name"] as String,
                picture = authentication.token.claims["picture"] as String,
            )

            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(
                    myMember,
                    authentication.credentials,
                    myMember.authorities,
                )
        }

        chain?.doFilter(request, response)
    }
}
