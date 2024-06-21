package ex03.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig02 {
	
	// 제외목록을 가지고 filterchain을 만들어 준다
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return new WebSecurityCustomizer() {

			@Override
			public void customize(WebSecurity web) {
				web
					.ignoring()
					// 전역접근 설정
					.requestMatchers(new AntPathRequestMatcher("/assets/**"));
			}
		};
	}
	
	@Bean
	// HttpSecurity = httpsecurity builder 미세제어
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.build();
	}
}
