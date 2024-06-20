package ex02.config;

import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.AntPathMatcher;

import ex02.filter.MySecurityFilter01;
import ex02.filter.MySecurityFilter02;
import ex02.filter.MySecurityFilter03;
import ex02.filter.MySecurityFilter04;

@Configuration
public class SecurityConfig01 {
	@Bean
	public FilterChainProxy springSecurityFilterChain() {
		List<SecurityFilterChain> springSecurityFilterChains = Arrays.asList(
			new SecurityFilterChain() {
				public boolean matches(HttpServletRequest request) {
					String uri = request.getRequestURI().replaceAll(request.getContextPath(), "");
					return new AntPathMatcher().match("/assets/**", uri);
				}
				public List<Filter> getFilters() {
					return null;
				}
			},
			
			new SecurityFilterChain() {
				public boolean matches(HttpServletRequest request) {
					String uri = request.getRequestURI().replaceAll(request.getContextPath(), "");
					return new AntPathMatcher().match("/**", uri);
				}
				public List<Filter> getFilters() {
					return Arrays.asList(
						mySecurityFilter01(),
						mySecurityFilter02(),
						mySecurityFilter03()						
					);
				}
			}
		);
		
		return new FilterChainProxy(springSecurityFilterChains);
	}
	
	@Bean
	public MySecurityFilter01 mySecurityFilter01() {
		return new MySecurityFilter01();
	}

	@Bean
	public MySecurityFilter02 mySecurityFilter02() {
		return new MySecurityFilter02();
	}

	@Bean
	public MySecurityFilter03 mySecurityFilter03() {
		return new MySecurityFilter03();
	}

}