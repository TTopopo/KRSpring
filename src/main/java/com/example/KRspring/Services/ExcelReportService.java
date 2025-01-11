package com.example.KRspring.Services;

import com.example.KRspring.Models.Customer;
import com.example.KRspring.Models.Foreman;
import com.example.KRspring.Models.Object;
import com.example.KRspring.Models.Worker;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelReportService {

    public byte[] generateWorkersReport(List<Worker> workers) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Workers");

        // Создание заголовков
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Имя");
        header.createCell(2).setCellValue("Фамилия");
        header.createCell(3).setCellValue("Отчество");
        header.createCell(4).setCellValue("Номер телефона");
        header.createCell(5).setCellValue("Должность");
        header.createCell(6).setCellValue("Прораб");
        header.createCell(7).setCellValue("Объект");

        // Заполнение данных
        int rowNum = 1;
        for (Worker worker : workers) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(worker.getId());
            row.createCell(1).setCellValue(worker.getName());
            row.createCell(2).setCellValue(worker.getSurname());
            row.createCell(3).setCellValue(worker.getPatronymic());
            row.createCell(4).setCellValue(worker.getPhoneNumber());
            row.createCell(5).setCellValue(worker.getPosition());
            row.createCell(6).setCellValue(worker.getForeman() != null ? worker.getForeman().getForemanName() + " " + worker.getForeman().getForemanSurname() : "Нет прораба");
            row.createCell(7).setCellValue(worker.getObject() != null ? worker.getObject().getName() : "Объект не назначен");
        }

        // Автоматическое изменение ширины столбцов
        for (int i = 0; i < 8; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }

    public byte[] generateObjectsReport(List<Object> objects) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Objects");

        // Создание заголовков
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Название");
        header.createCell(2).setCellValue("Тип");
        header.createCell(3).setCellValue("Адрес");
        header.createCell(4).setCellValue("Статус");
        header.createCell(5).setCellValue("Заказчик");
        header.createCell(6).setCellValue("Прораб");

        // Заполнение данных
        int rowNum = 1;
        for (Object object : objects) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(object.getId());
            row.createCell(1).setCellValue(object.getName());
            row.createCell(2).setCellValue(object.getType());
            row.createCell(3).setCellValue(object.getAddress());
            row.createCell(4).setCellValue(object.getStatus().getDisplayName());
            row.createCell(5).setCellValue(object.getCustomer() != null ? object.getCustomer().getCustomerName() + " " + object.getCustomer().getCustomerSurname() : "Нет заказчика");
            row.createCell(6).setCellValue(object.getForeman() != null ? object.getForeman().getForemanName() + " " + object.getForeman().getForemanSurname() : "Нет прораба");
        }

        // Автоматическое изменение ширины столбцов
        for (int i = 0; i < 7; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }

    public byte[] generateForemenReport(List<Foreman> foremen) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Foremen");

        // Создание заголовков
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Имя");
        header.createCell(2).setCellValue("Фамилия");
        header.createCell(3).setCellValue("Отчество");
        header.createCell(4).setCellValue("Специализация");
        header.createCell(5).setCellValue("Номер телефона");
        header.createCell(6).setCellValue("Квалификация");
        header.createCell(7).setCellValue("Заказчик");
        header.createCell(8).setCellValue("Объект");

        // Заполнение данных
        int rowNum = 1;
        for (Foreman foreman : foremen) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(foreman.getId());
            row.createCell(1).setCellValue(foreman.getForemanName());
            row.createCell(2).setCellValue(foreman.getForemanSurname());
            row.createCell(3).setCellValue(foreman.getForemanPatronymic());
            row.createCell(4).setCellValue(foreman.getSpecialization());
            row.createCell(5).setCellValue(foreman.getForemanPhoneNumber());
            row.createCell(6).setCellValue(foreman.getQualification());
            row.createCell(7).setCellValue(foreman.getCustomer() != null ? foreman.getCustomer().getCustomerName() + " " + foreman.getCustomer().getCustomerSurname() : "Нет заказчика");
            row.createCell(8).setCellValue(foreman.getObject() != null ? foreman.getObject().getName() : "Объект не назначен");
        }

        // Автоматическое изменение ширины столбцов
        for (int i = 0; i < 9; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }

    public byte[] generateCustomersReport(List<Customer> customers) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Customers");

        // Создание заголовков
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Имя");
        header.createCell(2).setCellValue("Фамилия");
        header.createCell(3).setCellValue("Отчество");
        header.createCell(4).setCellValue("Номер телефона");

        // Заполнение данных
        int rowNum = 1;
        for (Customer customer : customers) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(customer.getId());
            row.createCell(1).setCellValue(customer.getCustomerName());
            row.createCell(2).setCellValue(customer.getCustomerSurname());
            row.createCell(3).setCellValue(customer.getCustomerPatronymic());
            row.createCell(4).setCellValue(customer.getCustomerPhoneNumber());
        }

        // Автоматическое изменение ширины столбцов
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }
}
