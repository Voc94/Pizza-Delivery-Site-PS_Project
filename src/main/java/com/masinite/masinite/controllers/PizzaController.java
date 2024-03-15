package com.masinite.masinite.controllers;

import com.masinite.masinite.model.Chef;
import com.masinite.masinite.model.Pizza;
import com.masinite.masinite.repository.PizzaRepository;
import com.masinite.masinite.service.ChefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("pizza")
public class PizzaController {
    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private ChefService chefService;

    @GetMapping
    public String displayPizzaTable(Model model) {
        model.addAttribute("title", "All The Pizza");
        model.addAttribute("pizzas", pizzaRepository.findAll());
        return "pizza/index";
    }

    @GetMapping("create")
    public String displayCreatePizzaForm(Model model) {
        Pizza pizza = new Pizza();
        pizza.setChef(new Chef());
        model.addAttribute("title", "Create Pizza");
        model.addAttribute("pizza", pizza);
        return "pizza/create";
    }

    @PostMapping("create")
    public String processCreatePizzaForm(@ModelAttribute Pizza newPizza, @RequestParam String chefName, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Pizza");
            return "pizza/create";
        }

        Optional<Chef> optionalChef = chefService.findChefByName(chefName); // use chefService ?separation of concerns?
        if (optionalChef.isPresent()) {
            newPizza.setChef(optionalChef.get()); // set chef if exists
        } else if(chefName != null) {       // don't want to add a new chef while making a pizza, just doesn't make sense!
            return "pizza/create";
        }
        else{
            newPizza.setChef(null); // null if not found
        }

        pizzaRepository.save(newPizza);
        if (optionalChef.isPresent() && chefName != null) {
            chefService.addPizza(optionalChef.get(), newPizza);
        }
        return "redirect:/pizza";
    }

    @GetMapping("delete")
    public String displayDeletePizzaForm(Model model) {
        Iterable<Pizza> pizzas = pizzaRepository.findAll();
        model.addAttribute("title", "Delete Pizzas");
        model.addAttribute("pizzas", pizzas);
        return "pizza/delete";
    }

    @PostMapping("delete")
    public String processDeletePizzaForm(@RequestParam(required = false) Long pizzaId) {
        if (pizzaId != null) {
            Optional<Pizza> pizzaOpt = pizzaRepository.findById(pizzaId);
            if (pizzaOpt.isPresent()) {
                Pizza pizzaToDelete = pizzaOpt.get();
                chefService.findAllAndDeletePizzas(pizzaId);
                pizzaRepository.delete(pizzaToDelete);
            }
        }
        return "redirect:/pizza";
    }
    @GetMapping("/edit/{id}")
    public String displayEditPizzaForm(@PathVariable Long id, Model model) {
        Optional<Pizza> pizzaOpt = pizzaRepository.findById(id);
        if (pizzaOpt.isPresent()) {
            Pizza pizza = pizzaOpt.get();
            model.addAttribute("pizza", pizza);
            model.addAttribute("title", "Edit Pizza");
            return "pizza/edit";
        } else {
            return "redirect:/pizza";
        }
    }
    @PostMapping("/edit/{id}")
    public String processEditPizzaForm(@PathVariable("id") Long pizzaId, @RequestParam("name") String pizzaName, @RequestParam(required = false) Long chefId) {
        Optional<Pizza> pizzaOpt = pizzaRepository.findById(pizzaId);
        if (pizzaOpt.isPresent()) {
            Pizza pizza = pizzaOpt.get();
            pizza.setName(pizzaName);

            if (chefId != null) {
                Optional<Chef> chefOpt = chefService.findChefById(chefId);
                pizza.setChef(chefOpt.orElse(null));
            } else {
                pizza.setChef(null); // Clearing the chef if none is provided
            }
            pizzaRepository.save(pizza); // This should be enough to update the relationship
        }
        return "redirect:/pizza";
    }

}
