package com.masinite.masinite;

import com.masinite.masinite.model.Chef;
import com.masinite.masinite.model.Pizza;
import com.masinite.masinite.repository.ChefRepository;
import com.masinite.masinite.repository.PizzaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class PizzaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzaApplication.class, args);
	}

	@Bean
	CommandLineRunner init(PizzaRepository pizzaRepository, ChefRepository chefRepository){
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

			//pizzaRepository.delete(pizza);
		};
	}

}
