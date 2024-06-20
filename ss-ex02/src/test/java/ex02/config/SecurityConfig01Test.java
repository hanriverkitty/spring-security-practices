package ex02.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.servlet.Filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= {WebConfig.class, SecurityConfig01.class})
@WebAppConfiguration
public class SecurityConfig01Test {
	private MockMvc mvc;
	private FilterChainProxy filterChainProxy;
	
	// 테스트할때 컨테이너를 만들기 위함
		@BeforeEach
		public void setup(WebApplicationContext applicationContext) {
			// getBean Filter 타입의 myFilter bean을 가져오기
			filterChainProxy  = applicationContext.getBean("springSecurityFilterChain",FilterChainProxy.class);
			
			mvc = MockMvcBuilders
					.webAppContextSetup(applicationContext)
					// 스프링 컨테이너의 필터추가
					// 테스트 할때는 꼭 객체를 직접넣어줘야된다
					.addFilter(new DelegatingFilterProxy(filterChainProxy),"/*")
					.build();
		}
		
		@Test
		public void testSecurityFilterChains() {
			List<SecurityFilterChain> securityFilterChains = filterChainProxy.getFilterChains();
			assertEquals(2,securityFilterChains.size());
		}
		
		@Test
		public void testSecurityFilters() {
			// 안에 있는 필터 리스트 뽑기
			SecurityFilterChain securityFiterChain = filterChainProxy.getFilterChains().get(1);
			List<Filter> filters = securityFiterChain.getFilters();
			assertEquals(filters.size(),3);
		}
		
		@Test
		// securitychain의 첫번째거 테스트
		public void testSecurityFilterChain01() throws Throwable{
			mvc
				.perform(get("/assets/images/logo.png"))
				.andExpect(status().isOk())
				.andExpect(cookie().doesNotExist("MySecurityFilter01"));
		}
		
		@Test
		// securitychain의 첫번째거 테스트
		public void testSecurityFilterChain02() throws Throwable{
			mvc
				.perform(get("/hello"))
				.andExpect(status().isOk())
				.andExpect(cookie().value("MySecurityFilter01","Works"))
				.andExpect(cookie().value("MySecurityFilter02","Works"))
				.andExpect(cookie().value("MySecurityFilter03","Works"));
		}
}
