package kim.myeongjae.oauth.resourceserver.config

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames.AUD
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.jwt.*
import java.time.Duration
import java.time.temporal.ChronoUnit

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
        val withAudience: OAuth2TokenValidator<Jwt> = DelegatingOAuth2TokenValidator(
            JwtTimestampValidator(Duration.of(1, ChronoUnit.DAYS)), // 구글의 jwt expire time은 1시간이지만, 하루동안 접속이 유지되도록 하기 위해 clockSkew를 하루로 설정함.
            JwtIssuerValidator(issuerUri),
            audienceValidator,
        )
        jwtDecoder.setJwtValidator(withAudience)
        return jwtDecoder
    }

    private fun audienceValidator(expectedAudiences: List<String>): OAuth2TokenValidator<Jwt> {
        return JwtClaimValidator<List<String?>>(AUD) { aud ->
            aud.containsAll(expectedAudiences.toSet())
        }
    }
}
