import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }  // CSRF 비활성화
            .authorizeHttpRequests { authz ->
                authz
                    // Swagger UI 및 OpenAPI 경로 인증 없이 허용
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                    .anyRequest().permitAll()  // 모든 요청을 허용
            }
            .formLogin { form -> form.disable() }  // 기본 로그인 폼 비활성화
            .httpBasic { basic -> basic.disable() }  // HTTP Basic 인증 비활성화
        return http.build()
    }
}
