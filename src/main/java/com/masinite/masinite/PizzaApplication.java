package com.masinite.masinite;

import com.masinite.masinite.model.*;
import com.masinite.masinite.repository.ChefRepository;
import com.masinite.masinite.repository.PizzaRepository;
import com.masinite.masinite.repository.RoleRepository;
import com.masinite.masinite.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class PizzaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzaApplication.class, args);
	}

	@Bean
	CommandLineRunner init(PizzaRepository pizzaRepository, ChefRepository chefRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
		return args -> {


			Pizza pizza=new Pizza(null,"Pizza Prosciutto", null);
			Pizza pizza1=new Pizza(null,"Pizza Margherita",null);
			ArrayList<Pizza> pizzas= new ArrayList<>();
			pizzas.add(pizza);
			pizzas.add(pizza1);
			pizzaRepository.saveAll(pizzas);

			Chef chef1=new Chef(null,"Cosmin",pizzas);
			Chef chefSaved=chefRepository.save(chef1);

			chef1.setName("Dorin");
			chefRepository.save(chefSaved);

			pizza.setChef(chef1);
			pizzaRepository.save(pizza);

			pizzaRepository.findById(3L).ifPresent(pizza2 -> System.out.println(pizza2.getChef().getName()));
			chefRepository.findById(5L).ifPresent( chef -> {
				System.out.println(chef.getPizzas().get(0).getName());
			});

			// Ensure roles are created
			Role adminRole = roleRepository.findByName(RoleName.ADMIN)
					.orElseGet(() -> roleRepository.save(new Role(null, RoleName.ADMIN)));
			Role userRole = roleRepository.findByName(RoleName.USER)
					.orElseGet(() -> roleRepository.save(new Role(null, RoleName.USER)));

			// Create a default admin user
			userRepository.findByUsername("admin").orElseGet(() -> {
				User adminUser = new User();
				adminUser.setUsername("admin");
				adminUser.setPassword(passwordEncoder.encode("admin"));
				Set<Role> adminRoles = new HashSet<>();
				adminRoles.add(adminRole);
				adminUser.setRoles(adminRoles);
				return userRepository.save(adminUser);
			});

			// create a default user and admin
			userRepository.findByUsername("user").orElseGet(() -> {
				User user = new User();
				user.setUsername("user");
				user.setPassword(passwordEncoder.encode("user"));
				Set<Role> userRoles = new HashSet<>();
				userRoles.add(userRole);
				user.setRoles(userRoles);
				return userRepository.save(user);
			});
		};
	}

}
