package com.paymybuddy.paymybuddybackend;

import com.paymybuddy.paymybuddybackend.model.TypeTransaction;
import com.paymybuddy.paymybuddybackend.model.User;
import com.paymybuddy.paymybuddybackend.service.TypeTransactionService;
import com.paymybuddy.paymybuddybackend.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class PaymybuddyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymybuddyBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService, TypeTransactionService typeTransactionService) {
		return args -> {
			typeTransactionService.saveTypeTransaction(new TypeTransaction(null,"Virement externe"));
			typeTransactionService.saveTypeTransaction(new TypeTransaction(null,"Alimenter compter"));

			userService.saveUser(new User(null, "Jackson", "Micheal", "m.jackson@gmail.com", "1234", parseDate("1970-02-14"), new Date()));
			userService.saveUser(new User(null, "Bob", "Dylan", "d.bob@gmail.com", "1234", parseDate("1958-02-26"), new Date()));
			userService.saveUser(new User(null, "Pearl", "Jam", "j.pearl@gmail.com", "1234", parseDate("1988-06-05"), parseDate("2022-03-14")));
			userService.saveUser(new User(null, "Taylor", "Swift", "s.taylor@gmail.com", "1234", parseDate("1985-12-14"),parseDate("2022-05-20")));
			userService.saveUser(new User(null, "Miles", "Davis", "d.miles@gmail.com", "1234", parseDate("1964-10-10"),parseDate("2022-07-14")));

		};
	}

	public static Date parseDate(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return  new BCryptPasswordEncoder();
	}

}
