package ex01.filter;

import javax.servlet.Filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import ex01.config.AppConfig;
import ex01.config.WebConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= {WebConfig.class, AppConfig.class})
@WebAppConfiguration
public class MyFilterTest {
	private MockMvc mvc;
	
	
	// 테스트할때 컨테이너를 만들기 위함
	@BeforeEach
	public void setup(WebApplicationContext applicationContext) {
		// getBean Filter 타입의 myFilter bean을 가져오기
		Filter myFilter = applicationContext.getBean("myFilter",Filter.class);
		
		mvc = MockMvcBuilders
				.webAppContextSetup(applicationContext)
				// 스프링 컨테이너의 필터추가
				// 테스트 할때는 꼭 객체를 직접넣어줘야된다
				.addFilter(new DelegatingFilterProxy(myFilter),"/*")
				.build();
	}
	
	@Test
	public void testMyFilter() throws Throwable {
		// servlet context에 hello 요청이 들어온 것처럼 행동한다
		mvc
			.perform(get("/hello"))
			.andExpect(status().isOk())
			.andExpect(cookie().value("MyFilter", "Works"))
			.andDo(print());
	}
}
