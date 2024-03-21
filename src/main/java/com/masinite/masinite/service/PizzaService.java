package com.masinite.masinite.service;

import com.masinite.masinite.model.Pizza;
import com.masinite.masinite.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private ChefService chefService;

    public Iterable<Pizza> findAllPizzas() {
        return pizzaRepository.findAll();
    }

    public Optional<Pizza> findPizzaById(Long id) {
        return pizzaRepository.findById(id);
    }

    public void createOrUpdatePizza(Pizza pizza, String chefName) {
        chefService.findChefByName(chefName).ifPresent(pizza::setChef);
        pizzaRepository.save(pizza);
    }

    public void deletePizzaById(Long pizzaId) {
        pizzaRepository.findById(pizzaId).ifPresent(pizza -> {
            if (pizza.getChef() != null) {
                chefService.findAllAndDeletePizzas(pizzaId); // Ensure this is correctly implemented in ChefService
            }
            pizzaRepository.delete(pizza);
        });
    }


    public void updatePizza(Long pizzaId, Pizza updatedPizza) {
        pizzaRepository.findById(pizzaId).ifPresent(pizza -> {
            pizza.setName(updatedPizza.getName());
            pizza.setChef(updatedPizza.getChef());
            // Perform other necessary updates here
            pizzaRepository.save(pizza);
        });
    }
    public void unattachPizzasFromChef(Long chefId) {
        List<Pizza> pizzas = pizzaRepository.findByChefId(chefId); // Correct method to fetch all pizzas
        for (Pizza pizza : pizzas) {
            pizza.setChef(null);
            pizzaRepository.save(pizza); // Save the update
        }
    }
}
