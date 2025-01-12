package com.example.KRspring.Services;

import com.example.KRspring.Models.Worker;
import com.example.KRspring.Repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    private WorkerRepository workerRepository;

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public Worker saveWorker(Worker worker) {
        if (worker.getId() != null) {
            Worker existingWorker = getWorkerById(worker.getId());
            if (existingWorker != null) {
                existingWorker.setName(worker.getName());
                existingWorker.setSurname(worker.getSurname());
                existingWorker.setPatronymic(worker.getPatronymic());
                existingWorker.setPhoneNumber(worker.getPhoneNumber());
                existingWorker.setPosition(worker.getPosition());
                existingWorker.setForeman(worker.getForeman());
                existingWorker.setObject(worker.getObject());
                return workerRepository.save(existingWorker);
            }
        }
        return workerRepository.save(worker);
    }

    public Worker getWorkerById(Long id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElse(null);
    }

    public void deleteWorker(Long id) {
        workerRepository.deleteById(id);
    }

    public List<Worker> searchWorkers(String name, String surname) {
        if (name != null && surname != null) {
            return workerRepository.findByNameAndSurname(name, surname);
        } else if (name != null) {
            return workerRepository.findByName(name);
        } else if (surname != null) {
            return workerRepository.findBySurname(surname);
        } else {
            return workerRepository.findAll();
        }
    }

    public List<Worker> getWorkersByForemanId(Long foremanId) {
        return workerRepository.findByForemanId(foremanId); // Добавлен метод для получения рабочих по ID прораба
    }
}
