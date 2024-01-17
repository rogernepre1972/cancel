package br.com.desktop;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication()
@EnableMongoRepositories(considerNestedRepositories = true, basePackages = {
		"br.com.desktop.domain.port.mongo"
})
public class DesktopCustomerCancellationApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(DesktopCustomerCancellationApplication.class, args);
	}

	@Override
	public void run(String... args)  {

	}
}
