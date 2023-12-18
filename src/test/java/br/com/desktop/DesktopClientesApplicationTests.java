package br.com.desktop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
		webEnvironment = RANDOM_PORT,
		properties = {"spring.cloud.config.enabled=false"}
)
@TestPropertySource(locations="classpath:application.yml")
@AutoConfigureMockMvc

class DesktopClientesApplicationTests {

}
