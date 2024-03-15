package com.masinite.masinite.service;

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
    public void findAllAndDeletePizzas(Long pizzaID){
        Iterable<Chef> allChefs = chefRepository.findAll();
        for (Chef chef : allChefs) {
            boolean removed = chef.getPizzas().removeIf(pizza -> pizza.getId().equals(pizzaID));
            if (removed) {
                chefRepository.save(chef);
            }
        }
    }
}

