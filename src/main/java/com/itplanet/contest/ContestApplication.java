package com.itplanet.contest;

import com.itplanet.contest.entity.*;
import com.itplanet.contest.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class ContestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContestApplication.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(AnimalRepo animalRepo,
//										AccountRepo accountRepo,
//										TypeRepo typeRepo,
//										LocationRepo locationRepo,
//										VisitedRepo visitedRepo,
//										PasswordEncoder passwordEncoder){
//		return args -> {
//			Account account = new Account("Admin", "Admin", "admin@mail.ru", passwordEncoder.encode("admin"));
//			accountRepo.save(account);
//		};
//	}
}
