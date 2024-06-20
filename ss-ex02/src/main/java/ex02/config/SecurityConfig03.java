package ex02.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.session.DisableEncodeUrlFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import ex02.filter.MySecurityFilter04;

@Configuration
public class SecurityConfig03 {
	@Bean
	public FilterChainProxy springSecurityFilterChain() {
		List<SecurityFilterChain> springSecurityFilterChains = Arrays.asList(
			new DefaultSecurityFilterChain(new AntPathRequestMatcher("/assets/**")),
			new DefaultSecurityFilterChain(
					new AntPathRequestMatcher("/**"),
					disableEncodeUrlFilter(),
					webAsyncManagerIntegrationFilter(),
					securityContextPersistenceFilter(),
					mySecurityFilter04()
				)
			);
		return new FilterChainProxy(springSecurityFilterChains);
	}
	
	@Bean
	public DisableEncodeUrlFilter disableEncodeUrlFilter() {
		return new DisableEncodeUrlFilter();
	}

	@Bean
	public WebAsyncManagerIntegrationFilter webAsyncManagerIntegrationFilter() {
		return new WebAsyncManagerIntegrationFilter();
	}

	@Bean
	public SecurityContextPersistenceFilter securityContextPersistenceFilter() {
		return new SecurityContextPersistenceFilter();
	}
	
	@Bean
	public MySecurityFilter04 mySecurityFilter04() {
		return new MySecurityFilter04();
	}

}