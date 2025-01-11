package com.example.KRspring.Controllers;

import com.example.KRspring.Models.*;
import com.example.KRspring.Models.Object;
import com.example.KRspring.Services.CustomerService;
import com.example.KRspring.Services.ForemanService;
import com.example.KRspring.Services.ObjectService;
import com.example.KRspring.Services.UserService;
import com.example.KRspring.Repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ObjectController {
    private static final Logger logger = LoggerFactory.getLogger(ObjectController.class);

    @Autowired
    private ObjectService objectService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ForemanService foremanService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkerRepository workerRepository;

    @GetMapping("/objects")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_FOREMAN')")
    public String getAllObjects(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_CUSTOMER"))) {
            List<Object> objects = objectService.getObjectsByCustomerUsername(username);
            logger.info("Objects for customer: {}", objects);
            model.addAttribute("objects", objects);
            Customer currentUser = customerService.getCustomerByUsername(username);
            model.addAttribute("currentUser", currentUser);
        } else if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_FOREMAN"))) {
            Foreman foreman = foremanService.getForemanByUsername(username);
            if (foreman != null) {
                List<Object> objects = objectService.getObjectsByForemanName(foreman.getForemanName());
                logger.info("Objects for foreman: {}", objects);
                model.addAttribute("objects", objects);
                model.addAttribute("currentUser", foreman);
            } else {
                model.addAttribute("error", "Foreman not found");
            }
        } else {
            model.addAttribute("objects", objectService.getAllObjects());
        }
        return "objects";
    }

    @GetMapping("/objects/new")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public String showNewObjectForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("User roles: {}", authentication.getAuthorities());

        Object newObject = new Object();
        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_CUSTOMER"))) {
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            Customer customer = customerService.getCustomerByUsername(username);
            newObject.setCustomer(customer);
            model.addAttribute("currentUser", user);
        }

        model.addAttribute("object", newObject);
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("foremans", foremanService.getAllForemans());
        model.addAttribute("statuses", ObjectStatus.values());
        return "new_object";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @PostMapping("/objects/new")
    public String addObject(@Valid Object object, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Validation errors: {}", result.getAllErrors());
            model.addAttribute("customers", customerService.getAllCustomers());
            model.addAttribute("foremans", foremanService.getAllForemans());
            return "new_object";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_CUSTOMER"))) {
            String username = authentication.getName();
            Customer customer = customerService.getCustomerByUsername(username);
            object.setCustomer(customer);

            // Сохраняем объект, чтобы получить его идентификатор
            object = objectService.saveObject(object);

            if (object.getForeman() != null) {
                Foreman foreman = foremanService.getForemanById(object.getForeman().getId());
                foreman.setCustomer(customer);
                foreman.setObject(object); // Используем setObject
                foremanService.saveForeman(foreman);
            }
        } else {
            objectService.saveObject(object);
        }

        return "redirect:/objects";
    }

    @GetMapping("/objects/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_FOREMAN')")
    public String viewObjectDetails(@PathVariable Long id, Model model) {
        Object object = objectService.getObjectById(id);
        model.addAttribute("object", object);
        return "object_details";
    }

    @GetMapping("/objects/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_FOREMAN')")
    public String showEditObjectForm(@PathVariable Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Object object = objectService.getObjectById(id);

        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_CUSTOMER"))) {
            if (!objectService.isObjectOwnedByCustomer(object, username)) {
                return "redirect:/objects";
            }
        }

        model.addAttribute("object", object);
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("foremans", foremanService.getAllForemans());
        model.addAttribute("statuses", ObjectStatus.values());

        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);

        return "edit_object";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FOREMAN')")
    @PostMapping("/objects/edit/{id}")
    public String updateObject(@PathVariable Long id, @Valid Object object, BindingResult result, Model model) {
        logger.info("Updating object with id: {}, type: {}, name: {}, address: {}, status: {}",
                id, object.getType(), object.getName(), object.getAddress(), object.getStatus());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object existingObject = objectService.getObjectById(id);

        if (existingObject == null) {
            logger.error("Object with id {} not found", id);
            return "redirect:/objects";
        }

        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_FOREMAN"))) {
            existingObject.setStatus(object.getStatus());
            logger.info("Updating object status with id: {}, new status: {}", id, object.getStatus());
        } else {
            existingObject.setType(object.getType());
            existingObject.setName(object.getName());
            existingObject.setAddress(object.getAddress());
            existingObject.setCustomer(object.getCustomer());
            existingObject.setForeman(object.getForeman());
            existingObject.setStatus(object.getStatus());
            logger.info("Updating object with id: {}, new type: {}, new name: {}, new address: {}, new status: {}",
                    id, existingObject.getType(), existingObject.getName(), existingObject.getAddress(), existingObject.getStatus());
        }

        if (result.hasErrors()) {
            logger.error("Validation errors: {}", result.getAllErrors());
            object.setId(id);
            model.addAttribute("customers", customerService.getAllCustomers());
            model.addAttribute("foremans", foremanService.getAllForemans());
            return "edit_object";
        }

        objectService.saveObject(existingObject);
        return "redirect:/objects";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/objects/delete/{id}")
    public String deleteObject(@PathVariable Long id) {
        try {
            logger.info("Deleting object with id {}", id);

            List<Worker> workers = workerRepository.findByObjectId(id);
            logger.info("Found {} related workers", workers.size());
            for (Worker worker : workers) {
                worker.setObject(null);
                workerRepository.save(worker);
                logger.info("Updated worker with id {}", worker.getId());
            }

            objectService.deleteObject(id);
            logger.info("Object with id {} deleted successfully", id);
        } catch (Exception e) {
            logger.error("Error deleting object with id {}", id, e);
        }
        return "redirect:/objects";
    }

    @GetMapping("/objects/search")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_FOREMAN')")
    public String searchObjects(@RequestParam(required = false) String name, @RequestParam(required = false) String type, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_CUSTOMER"))) {
            List<Object> objects = objectService.searchObjectsByCustomer(name, type, username);
            model.addAttribute("objects", objects);
        } else if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_FOREMAN"))) {
            List<Object> objects = objectService.searchObjectsByForeman(name, type, username);
            model.addAttribute("objects", objects);
        } else {
            List<Object> objects = objectService.searchObjects(name, type);
            model.addAttribute("objects", objects);
        }
        return "objects";
    }
}
