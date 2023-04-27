package kim.myeongjae.oauth.resourceserver.config

import kim.myeongjae.oauth.resourceserver.JwtGoogleTokenApiValidator
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames.AUD
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimValidator
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtDecoders
import org.springframework.security.oauth2.jwt.JwtValidators
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class BeanConfig(
    private val oAuth2ResourceServerProperties: OAuth2ResourceServerProperties,
    private val webClientBuilder: WebClient.Builder,
) {

    @Bean
    fun jwtGoogleTokenValidator(): JwtGoogleTokenApiValidator = JwtGoogleTokenApiValidator(webClientBuilder)

    // https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html#oauth2resourceserver-jwt-validation-custom
    @Bean
    fun jwtDecoder(): JwtDecoder {
        val issuerUri = oAuth2ResourceServerProperties.jwt.issuerUri

        return (JwtDecoders.fromIssuerLocation<JwtDecoder>(issuerUri) as NimbusJwtDecoder).apply {
            setJwtValidator(
                DelegatingOAuth2TokenValidator(
                    jwtGoogleTokenValidator(),
                    JwtValidators.createDefaultWithIssuer(issuerUri),
                    audienceValidator(oAuth2ResourceServerProperties.jwt.audiences),
                ),
            )
        }
    }

    private fun audienceValidator(expectedAudiences: List<String>): OAuth2TokenValidator<Jwt> {
        return JwtClaimValidator<List<String?>>(AUD) { aud ->
            aud.containsAll(expectedAudiences.toSet())
        }
    }
}
