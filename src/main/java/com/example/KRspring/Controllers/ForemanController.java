package com.example.KRspring.Controllers;

import com.example.KRspring.Models.Customer;
import com.example.KRspring.Models.Foreman;
import com.example.KRspring.Services.CustomerService;
import com.example.KRspring.Services.ForemanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;

import java.util.List;

@Controller
public class ForemanController {
    @Autowired
    private ForemanService foremanService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/foremans")
    public String getAllForemans(Model model) {
        model.addAttribute("foremans", foremanService.getAllForemans());
        return "foremans";
    }

    @GetMapping("/foremans/new")
    public String showNewForemanForm(Model model) {
        model.addAttribute("foreman", new Foreman());
        model.addAttribute("customers", customerService.getAllCustomers());
        return "new_foreman";
    }

    @PostMapping("/foremans/new")
    public String addForeman(@Valid Foreman foreman, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("customers", customerService.getAllCustomers());
            return "new_foreman";
        }
        foremanService.saveForeman(foreman);
        return "redirect:/foremans";
    }

    @GetMapping("/foremans/{id}")
    public String viewForemanDetails(@PathVariable Long id, Model model) {
        Foreman foreman = foremanService.getForemanById(id);
        model.addAttribute("foreman", foreman);
        return "foreman_details";
    }

    @GetMapping("/foremans/edit/{id}")
    public String showEditForemanForm(@PathVariable Long id, Model model) {
        Foreman foreman = foremanService.getForemanById(id);
        model.addAttribute("foreman", foreman);
        model.addAttribute("customers", customerService.getAllCustomers());
        return "edit_foreman";
    }

    @PostMapping("/foremans/edit/{id}")
    public String updateForeman(@PathVariable Long id, @Valid Foreman foreman, BindingResult result, Model model) {
        if (result.hasErrors()) {
            foreman.setId(id);
            model.addAttribute("customers", customerService.getAllCustomers());
            return "edit_foreman";
        }
        foremanService.saveForeman(foreman);
        return "redirect:/foremans";
    }

    @GetMapping("/foremans/delete/{id}")
    public String deleteForeman(@PathVariable Long id) {
        foremanService.deleteForeman(id);
        return "redirect:/foremans";
    }

    @GetMapping("/foremans/search")
    public String searchForemans(@RequestParam(required = false) String name, @RequestParam(required = false) String surname, Model model) {
        List<Foreman> foremans = foremanService.searchForemans(name, surname);
        model.addAttribute("foremans", foremans);
        return "foremans";
    }
}
