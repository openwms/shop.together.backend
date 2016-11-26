package io.interface21.shop2gether;

import org.ameba.annotation.EnableAspects;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
@EnableAspects
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner clr(OwnerRepository repo) {
		return  args -> {
			Owner heiko = repo.save(new Owner("heiko", "4711", "heiko@home.com"));
			heiko.getItems().add(new TextNote("Title", "1 x Eggs", null, false));
			repo.save(heiko);
		};
	}
}
