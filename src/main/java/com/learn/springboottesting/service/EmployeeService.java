package com.learn.springboottesting.service;

import com.learn.springboottesting.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);
    Employee updateEmployee(Employee employee);
    void deleteEmployee(long id);
    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(long id);

}
