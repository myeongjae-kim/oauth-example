package kim.myeongjae.oauth.resourceserver

import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.OAuth2ErrorCodes
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.reactive.function.client.WebClient

class JwtGoogleTokenApiValidator(
    webClientBuilder: WebClient.Builder,
    private val baseUrl: String = "https://oauth2.googleapis.com/tokeninfo",
    private val queryName: String = "id_token",
) : OAuth2TokenValidator<Jwt> {

    private val webClient: WebClient = webClientBuilder.baseUrl(baseUrl).build()

    override fun validate(token: Jwt?): OAuth2TokenValidatorResult {
        requireNotNull(token) { "token cannot be null" }

        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder.queryParam(queryName, token.tokenValue).build()
            }
            .retrieve()
            .toBodilessEntity()
            .map { OAuth2TokenValidatorResult.success() }
            .onErrorReturn(OAuth2TokenValidatorResult.failure(OAuth2Error(OAuth2ErrorCodes.INVALID_TOKEN, "validation from google failed", baseUrl)))
            .block()!!
    }
}
