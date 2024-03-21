package com.masinite.masinite.controllers;

import com.masinite.masinite.model.Chef;
import com.masinite.masinite.service.ChefService;
import com.masinite.masinite.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("chef")
public class ChefController {

    private final ChefService chefService;
    private final PizzaService pizzaService;

    @Autowired
    public ChefController(ChefService chefService, PizzaService pizzaService) {
        this.chefService = chefService;
        this.pizzaService = pizzaService;
    }

    @GetMapping
    public String displayChefTable(Model model) {
        model.addAttribute("title", "All The Chefs");
        model.addAttribute("chefs", chefService.findAllChefs());
        return "chef/index";
    }

    @GetMapping("create")
    public String displayCreateChefForm(Model model) {
        Chef chef = new Chef();
        model.addAttribute("title", "Create Chef");
        model.addAttribute("chef", chef);
        return "chef/create";
    }

    @PostMapping("/create")
    public String processCreateChefForm(@ModelAttribute Chef chef, Errors errors) {
        if (errors.hasErrors()) {
            return "chef/create";
        }
        chefService.saveChef(chef);
        return "redirect:/chef";
    }

    @GetMapping("delete")
    public String displayChefDeleteForm(Model model) {
        model.addAttribute("title", "Delete Chefs");
        model.addAttribute("chefs", chefService.findAllChefs());
        return "chef/delete";
    }

    @PostMapping("delete")
    public String processDeleteChefForm(@RequestParam(required = false) Long chefId) {
        chefService.deleteChefById(chefId);
        return "redirect:/pizza";
    }
}
