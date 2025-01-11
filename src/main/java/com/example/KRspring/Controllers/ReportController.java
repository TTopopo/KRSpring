package com.example.KRspring.Controllers;

import com.example.KRspring.Services.ExcelReportService;
import com.example.KRspring.Services.CustomerService;
import com.example.KRspring.Services.ForemanService;
import com.example.KRspring.Services.ObjectService;
import com.example.KRspring.Services.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class ReportController {

    @Autowired
    private WorkerService workerService;

    @Autowired
    private ObjectService objectService;

    @Autowired
    private ForemanService foremanService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ExcelReportService excelReportService;

    @GetMapping("/workers/download")
    public ResponseEntity<byte[]> downloadWorkersReport() throws IOException {
        byte[] report = excelReportService.generateWorkersReport(workerService.getAllWorkers());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "workers_report.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(report);
    }

    @GetMapping("/objects/download")
    public ResponseEntity<byte[]> downloadObjectsReport() throws IOException {
        byte[] report = excelReportService.generateObjectsReport(objectService.getAllObjects());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "objects_report.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(report);
    }

    @GetMapping("/foremans/download")
    public ResponseEntity<byte[]> downloadForemenReport() throws IOException {
        byte[] report = excelReportService.generateForemenReport(foremanService.getAllForemans());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "foremen_report.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(report);
    }

    @GetMapping("/customers/download")
    public ResponseEntity<byte[]> downloadCustomersReport() throws IOException {
        byte[] report = excelReportService.generateCustomersReport(customerService.getAllCustomers());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "customers_report.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(report);
    }
}
