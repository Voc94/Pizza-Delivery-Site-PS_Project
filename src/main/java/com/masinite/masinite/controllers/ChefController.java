package com.masinite.masinite.controllers;

import com.masinite.masinite.model.Chef;
import com.masinite.masinite.model.Pizza;
import com.masinite.masinite.repository.ChefRepository;
import com.masinite.masinite.service.ChefService;
import com.masinite.masinite.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("chef")
public class ChefController {
    @Autowired
    private ChefRepository chefRepository;
    @Autowired
    private PizzaService pizzaService;
    @GetMapping
    public String displayChefTable(Model model) {
        model.addAttribute("title", "All The Chefs");
        model.addAttribute("chefs", chefRepository.findAll());
        return "chef/index";
    }
    @GetMapping("create")
    public String displayCreateChefForm(Model model) {
        Chef chef = new Chef();
        model.addAttribute("title", "Create Pizza");
        model.addAttribute("chef", chef);
        return "chef/create";
    }
    @PostMapping("/create")
    public String processCreateChefForm(@ModelAttribute Chef chef, Errors errors) {
        if (errors.hasErrors()) {
            return "chef/create";
        }
        chefRepository.save(chef);
        return "redirect:/chef";
    }
    @GetMapping("delete")
    public String displayChefDeleteForm(Model model) {
        Iterable<Chef> chefs = chefRepository.findAll();
        model.addAttribute("title", "Delete Chefs");
        model.addAttribute("chefs", chefs);
        return "chef/delete";
    }
    @PostMapping("delete")
    public String processDeleteChefForm(@RequestParam(required = false) Long chefId) {
        if (chefId != null) {
            Optional<Chef> chefOpt = chefRepository.findById(chefId);
            if (chefOpt.isPresent()) {
                Chef chefToDelete = chefOpt.get();
                pizzaService.unattachPizzasFromChef(chefId);
                chefRepository.delete(chefToDelete);
            }
        }
        return "redirect:/pizza";
    }
}
