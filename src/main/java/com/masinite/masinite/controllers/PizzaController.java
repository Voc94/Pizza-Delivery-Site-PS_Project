package com.masinite.masinite.controllers;

import com.masinite.masinite.model.Chef;
import com.masinite.masinite.model.Pizza;
import com.masinite.masinite.service.ChefService;
import com.masinite.masinite.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pizza")
public class PizzaController {

    private final PizzaService pizzaService;
    private final ChefService chefService;

    @Autowired
    public PizzaController(PizzaService pizzaService, ChefService chefService) {
        this.pizzaService = pizzaService;
        this.chefService = chefService;
    }

    @GetMapping
    public String displayPizzaTable(Model model) {
        model.addAttribute("title", "All The Pizza");
        model.addAttribute("pizzas", pizzaService.findAllPizzas());
        return "pizza/index";
    }

    @GetMapping("/create")
    public String displayCreatePizzaForm(Model model) {
        model.addAttribute("title", "Create Pizza");
        model.addAttribute("pizza", new Pizza());
        return "pizza/create";
    }

    @PostMapping("/create")
    public String processCreatePizzaForm(@ModelAttribute Pizza newPizza, @RequestParam String chefName, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Pizza");
            return "pizza/create";
        }

        pizzaService.createOrUpdatePizza(newPizza, chefName);
        return "redirect:/pizza";
    }

    @GetMapping("/delete")
    public String displayDeletePizzaForm(Model model) {
        model.addAttribute("title", "Delete Pizzas");
        model.addAttribute("pizzas", pizzaService.findAllPizzas());
        return "pizza/delete";
    }

    @PostMapping("/delete")
    public String processDeletePizzaForm(@RequestParam(required = false) Long pizzaId) {
        pizzaService.deletePizzaById(pizzaId);
        return "redirect:/pizza";
    }


    @GetMapping("/edit/{id}")
    public String displayEditPizzaForm(@PathVariable Long id, Model model) {
        Pizza pizza = pizzaService.findPizzaById(id).orElse(null);
        if (pizza != null) {
            model.addAttribute("pizza", pizza);
            model.addAttribute("title", "Edit Pizza");
            return "pizza/edit";
        } else {
            return "redirect:/pizza";
        }
    }

    @PostMapping("/edit/{id}")
    public String processEditPizzaForm(@PathVariable("id") Long pizzaId, @ModelAttribute Pizza pizza, Errors errors) {
        if (errors.hasErrors()) {
            return "pizza/edit";
        }

        pizzaService.updatePizza(pizzaId, pizza);
        return "redirect:/pizza";
    }
}
