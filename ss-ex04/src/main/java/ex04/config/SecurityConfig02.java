package ex04.config;

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
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return new WebSecurityCustomizer() {
            @Override
            public void customize(WebSecurity web) {
                web
            		.ignoring()
            		.requestMatchers(new AntPathRequestMatcher("/assets/**"));
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
    		.formLogin()
    		// formLogin configure 설정은 끝나고 빌더를 리턴해 달라는 의미 or http.httpBasic() 이라고 써도 된다
    		.and()
    		// formLogin은 폼제공해서 로그인 한다는 의미 httpBasic 은 http 헤더에 아이디와 비밀번호를 입력해서 들어가겠다는 의미
    		// 빌더에 새로운 configure 생성
    		// BasicAuthenicationFilter
    		.httpBasic();
        return http.build();
    }
}
