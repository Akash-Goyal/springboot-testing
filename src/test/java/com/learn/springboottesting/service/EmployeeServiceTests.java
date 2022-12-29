package com.learn.springboottesting.service;

import com.learn.springboottesting.entity.Employee;
import com.learn.springboottesting.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    //    @Mock
    private EmployeeRepository employeeRepository;

    //    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .firstName("Akash")
                .lastName("Goyal")
                .email("akash.goyal1@target.com")
                .build();
    }

    @Test
    @DisplayName("Positive Case - Junit to test saveEmployee()")
    public void givenNewEmployeeObject_whenSaveEmployee_thenReturnSavedEmployeeObject(){

        // Given
        given(employeeRepository.findByEmail(any(String.class)))
                .willReturn(Optional.empty());
        given(employeeRepository.save(employee))
                .willReturn(employee);

        // When
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // Then
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    @DisplayName("Negative Case - Junit to test saveEmployee() which throws Exception")
    public void givenNewEmployeeObjectWithExistingEmail_whenSaveEmployee_thenThrowsException(){

        // Given
        given(employeeRepository.findByEmail(any(String.class)))
                .willReturn(Optional.of(employee));

        // When
        Assertions.assertThrows(RuntimeException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        // Then
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Positive Case - Junit to test updateEmployee()")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployeObject(){

        // Given
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("akash.goyal2@target.com");

        // When
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        // Then
        assertThat(updatedEmployee.getEmail()).isEqualTo("akash.goyal2@target.com");
    }

    @Test
    @DisplayName("Positive Case - Junit test for deleteEmployee()")
    public void givenEmployeeId_whenDeleteEmployee_thenNothing(){

        // Given
        willDoNothing().given(employeeRepository).deleteById(any(Long.class));

        // When
        employeeService.deleteEmployee(1L);

        // Then
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Positive Case - Junit test for getAllEmployees()")
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList(){

        // Given
        List<Employee> employeeList = List.of(
                Employee.builder().build(),
                Employee.builder().build());
        given(employeeRepository.findAll()).willReturn(employeeList);

        // When
        List<Employee> allEmployees = employeeService.getAllEmployees();

        // Then
        assertThat(allEmployees.size()).isEqualTo(employeeList.size());
    }

    @Test
    @DisplayName("Negative Case - Junit test for getAllEmployees()")
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList(){

        // Given
        given(employeeRepository.findAll()).willReturn(new ArrayList<>());

        // When
        List<Employee> allEmployees = employeeService.getAllEmployees();

        // Then
        assertThat(allEmployees.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Negative Case - Junit test for getEmployeeById()")
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject(){

        // Given
        long employeeId = 1L;
        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employee));

        // When
        Optional<Employee> existingEmployee = employeeService.getEmployeeById(employeeId);

        // Then
        assertThat(existingEmployee.isPresent()).isNotNull();
    }
}
