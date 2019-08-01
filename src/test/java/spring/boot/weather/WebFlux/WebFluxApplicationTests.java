package spring.boot.weather.WebFlux;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebFluxApplicationTests {

	@Autowired
	ApplicationContext context;

	WebTestClient rest;

	@Before
	public void setup() {
		this.rest = WebTestClient
				.bindToApplicationContext(this.context)
				.build();
	}

	@Test
	public void contextLoads() {
	}

}
