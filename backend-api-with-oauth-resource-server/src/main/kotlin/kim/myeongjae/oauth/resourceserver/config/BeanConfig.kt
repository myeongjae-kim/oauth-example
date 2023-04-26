package kim.myeongjae.oauth.resourceserver.config

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames.AUD
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.jwt.*

@Configuration
class BeanConfig(
    private val oAuth2ResourceServerProperties: OAuth2ResourceServerProperties,
) {

    // https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html#oauth2resourceserver-jwt-validation-custom
    @Bean
    fun jwtDecoder(): JwtDecoder {
        val issuerUri = oAuth2ResourceServerProperties.jwt.issuerUri
        val jwtDecoder = JwtDecoders.fromIssuerLocation<JwtDecoder>(issuerUri) as NimbusJwtDecoder
        val audienceValidator = audienceValidator(oAuth2ResourceServerProperties.jwt.audiences)
        val withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri)
        val withAudience: OAuth2TokenValidator<Jwt> = DelegatingOAuth2TokenValidator(withIssuer, audienceValidator)
        jwtDecoder.setJwtValidator(withAudience)
        return jwtDecoder
    }

    private fun audienceValidator(expectedAudiences: List<String>): OAuth2TokenValidator<Jwt> {
        return JwtClaimValidator<List<String?>>(AUD) { aud ->
            aud.containsAll(expectedAudiences.toSet())
        }
    }
}
