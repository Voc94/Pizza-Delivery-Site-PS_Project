package com.masinite.masinite.service;

import com.masinite.masinite.model.Pizza;
import com.masinite.masinite.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    public void unattachPizzasFromChef(Long chefId) {
        List<Pizza> pizzas = pizzaRepository.findByChefId(chefId); // Correct method to fetch all pizzas
        for (Pizza pizza : pizzas) {
            pizza.setChef(null);
            pizzaRepository.save(pizza); // Save the update
        }
    }
}
