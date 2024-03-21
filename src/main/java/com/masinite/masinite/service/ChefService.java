package com.masinite.masinite.service;
import com.masinite.masinite.service.PizzaService;
import com.masinite.masinite.model.Chef;
import com.masinite.masinite.model.Pizza;
import com.masinite.masinite.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ChefService {
    @Autowired
    private ChefRepository chefRepository;
    @Autowired
    private PizzaService pizzaService;
    public void addPizza(Chef chef, Pizza pizza){
        chef.getPizzas().add(pizza);
        chefRepository.save(chef);
    }
    public Optional<Chef> findChefByName(String chefName) {
        return Optional.ofNullable(chefRepository.findFirstByName(chefName));
    }
    public Optional<Chef> findChefById(Long chefId){
        return Optional.ofNullable(chefRepository.findFirstById(chefId));
    }
    public void findAllAndDeletePizzas(Long pizzaId){
        Iterable<Chef> allChefs = chefRepository.findAll();
        for (Chef chef : allChefs) {
            boolean removed = chef.getPizzas().removeIf(pizza -> pizza.getId().equals(pizzaId));
            if (removed) {
                chefRepository.save(chef);
            }
        }
    }
    public Iterable<Chef> findAllChefs() {
        return chefRepository.findAll();
    }

    // Add method to save a chef
    public void saveChef(Chef chef) {
        chefRepository.save(chef);
    }

    // Add method to delete a chef by ID
    public void deleteChefById(Long chefId) {
        if (chefId != null) {
            chefRepository.findById(chefId).ifPresent(chef -> {
                pizzaService.unattachPizzasFromChef(chefId);
                chefRepository.delete(chef);
            });
        }
    }

}

