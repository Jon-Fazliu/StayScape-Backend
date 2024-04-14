package stayscape.backend.domain

import com.stayscape.backend.domain.security.JwtService
import com.stayscape.backend.domain.user.UserJpaRepository
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

abstract class AbstractApiTest @Autowired constructor(
    val userService: UserJpaRepository,
    val jwtService: JwtService
) : AbstractTest() {

    protected fun getUrl(@Suppress("SameParameterValue") port: Int, resource: String): String {
        Assertions.assertThat(port).isGreaterThan(0)
        return String.format("http://localhost:%d/api/v1%s", port, resource)
    }

    protected fun getHeaders(): MultiValueMap<String, String> {
        val headers: MultiValueMap<String, String> = LinkedMultiValueMap()
        //whatever extra headers are needed should be added
        return headers
    }

    protected fun getHeadersWithAuthorizationForUser(
        id: Int,
        role: String,
        email: String,
    ): MultiValueMap<String, String> {
        val headers: MultiValueMap<String, String> = LinkedMultiValueMap()
        val token =  "Bearer ${getTokenForUser(id, role, email)}"
        headers["Authorization"] = token
        return headers
    }

    private fun getTokenForUser(id: Int, role: String, email: String): String {
        val claims = HashMap<String, Any>()
        claims["role"] = role
        claims["user_id"] = id

        return jwtService.generateToken(claims, email, 1000 * 60 * 10)
    }
}
