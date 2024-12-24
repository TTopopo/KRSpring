package com.example.KRspring.Controllers;

import com.example.KRspring.Models.Customer;
import com.example.KRspring.Models.Foreman;
import com.example.KRspring.Models.Object;
import com.example.KRspring.Services.CustomerService;
import com.example.KRspring.Services.ForemanService;
import com.example.KRspring.Services.ObjectService;
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
public class ObjectController {
    @Autowired
    private ObjectService objectService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ForemanService foremanService;

    @GetMapping("/objects")
    public String getAllObjects(Model model) {
        model.addAttribute("objects", objectService.getAllObjects());
        return "objects";
    }

    @GetMapping("/objects/new")
    public String showNewObjectForm(Model model) {
        model.addAttribute("object", new Object());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("foremans", foremanService.getAllForemans());
        return "new_object";
    }

    @PostMapping("/objects/new")
    public String addObject(@Valid Object object, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("customers", customerService.getAllCustomers());
            model.addAttribute("foremans", foremanService.getAllForemans());
            return "new_object";
        }
        objectService.saveObject(object);
        return "redirect:/objects";
    }

    @GetMapping("/objects/{id}")
    public String viewObjectDetails(@PathVariable Long id, Model model) {
        Object object = objectService.getObjectById(id);
        model.addAttribute("object", object);
        return "object_details";
    }

    @GetMapping("/objects/edit/{id}")
    public String showEditObjectForm(@PathVariable Long id, Model model) {
        Object object = objectService.getObjectById(id);
        model.addAttribute("object", object);
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("foremans", foremanService.getAllForemans());
        return "edit_object";
    }

    @PostMapping("/objects/edit/{id}")
    public String updateObject(@PathVariable Long id, @Valid Object object, BindingResult result, Model model) {
        if (result.hasErrors()) {
            object.setId(id);
            model.addAttribute("customers", customerService.getAllCustomers());
            model.addAttribute("foremans", foremanService.getAllForemans());
            return "edit_object";
        }
        objectService.saveObject(object);
        return "redirect:/objects";
    }

    @GetMapping("/objects/delete/{id}")
    public String deleteObject(@PathVariable Long id) {
        objectService.deleteObject(id);
        return "redirect:/objects";
    }

    @GetMapping("/objects/search")
    public String searchObjects(@RequestParam(required = false) String name, @RequestParam(required = false) String type, Model model) {
        List<Object> objects = objectService.searchObjects(name, type);
        model.addAttribute("objects", objects);
        return "objects";
    }
}
