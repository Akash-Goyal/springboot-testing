package com.learn.springboottesting.controller;

import com.learn.springboottesting.entity.Employee;
import com.learn.springboottesting.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(
            @RequestBody Employee employee){

        Employee createdEmployee = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable("id") long id,
            @RequestBody Employee employee){

        return employeeService.getEmployeeById(id)
                .map(e -> {
                    e.setFirstName(employee.getFirstName());
                    e.setLastName(employee.getLastName());
                    e.setEmail(employee.getEmail());
                    Employee employeeUpdated = employeeService.updateEmployee(e);
                    return new ResponseEntity<>(employeeUpdated, HttpStatus.OK);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(
            @PathVariable("id") long id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(
            @PathVariable("id") Long id){
        return employeeService.getEmployeeById(id)
                .map(e -> ResponseEntity.ok(e))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }
}
