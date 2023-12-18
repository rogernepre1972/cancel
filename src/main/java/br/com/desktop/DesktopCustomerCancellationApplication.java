package br.com.desktop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

//@SpringBootApplication(exclude= {UserDetailsServiceAutoConfiguration.class})@Slf4j
@SpringBootApplication()
public class DesktopCustomerCancellationApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(DesktopCustomerCancellationApplication.class, args);
	}

	@Override
	public void run(String... args)  {

	}
}
