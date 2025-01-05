package com.example.KRspring.Controllers;

import com.example.KRspring.Models.Worker;
import com.example.KRspring.Services.ForemanService;
import com.example.KRspring.Services.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class WorkerController {
    @Autowired
    private WorkerService workerService;

    @Autowired
    private ForemanService foremanService;

    @GetMapping("/workers")
    public String getAllWorkers(Model model) {
        model.addAttribute("workers", workerService.getAllWorkers());
        return "workers";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FOREMAN')")
    @GetMapping("/workers/new")
    public String showNewWorkerForm(Model model) {
        model.addAttribute("worker", new Worker());
        model.addAttribute("foremans", foremanService.getAllForemans());
        return "new_worker";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FOREMAN')")
    @PostMapping("/workers/new")
    public String addWorker(@Valid Worker worker, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("foremans", foremanService.getAllForemans());
            return "new_worker";
        }
        workerService.saveWorker(worker);
        return "redirect:/workers";
    }

    @GetMapping("/workers/{id}")
    public String viewWorkerDetails(@PathVariable Long id, Model model) {
        Worker worker = workerService.getWorkerById(id);
        model.addAttribute("worker", worker);
        return "worker_details";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FOREMAN')")
    @GetMapping("/workers/edit/{id}")
    public String showEditWorkerForm(@PathVariable Long id, Model model) {
        Worker worker = workerService.getWorkerById(id);
        model.addAttribute("worker", worker);
        model.addAttribute("foremans", foremanService.getAllForemans());
        return "edit_worker";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FOREMAN')")
    @PostMapping("/workers/edit/{id}")
    public String updateWorker(@PathVariable Long id, @Valid Worker worker, BindingResult result, Model model) {
        if (result.hasErrors()) {
            worker.setId(id);
            model.addAttribute("foremans", foremanService.getAllForemans());
            return "edit_worker";
        }
        workerService.saveWorker(worker);
        return "redirect:/workers";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/workers/delete/{id}")
    public String deleteWorker(@PathVariable Long id) {
        workerService.deleteWorker(id);
        return "redirect:/workers";
    }

    @GetMapping("/workers/search")
    public String searchWorkers(@RequestParam(required = false) String name, @RequestParam(required = false) String surname, Model model) {
        List<Worker> workers = workerService.searchWorkers(name, surname);
        model.addAttribute("workers", workers);
        return "workers";
    }
}
