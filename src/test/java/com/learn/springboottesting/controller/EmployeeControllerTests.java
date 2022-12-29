package com.learn.springboottesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.springboottesting.entity.Employee;
import com.learn.springboottesting.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenNewEmployee_whenCreateEmployee_thenReturnCreatedEmployee() throws Exception {

        // Given
        Employee employee = Employee.builder()
                .firstName("Sree")
                .lastName("Lakshmi")
                .email("Sree.Lakshmi@gmail.com")
                .build();
        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // When
        ResultActions response = mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // Then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())));
    }

    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() throws Exception {

        // Given
        long employeeId = 1L;
        Employee existingEmployee = Employee.builder()
                .firstName("Akash")
                .lastName("Goyal")
                .email("akash.goyal@gmail.com")
                .build();
        Employee updatedEmployee = Employee.builder()
                .firstName("Akash")
                .lastName("Goyal")
                .email("akash.goyal1@gmail.com")
                .build();
        given(employeeService.getEmployeeById(employeeId))
                .willReturn(Optional.of(existingEmployee));
        given(employeeService.updateEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // When
        ResultActions response = mockMvc.perform(put("/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // Then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())));
    }

    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception {

        // Given
        long employeeId = 1L;
        Employee updatedEmployee = Employee.builder()
                .firstName("Akash")
                .lastName("Goyal")
                .email("akash.goyal1@gmail.com")
                .build();
        given(employeeService.getEmployeeById(employeeId))
                .willReturn(Optional.empty());
        given(employeeService.updateEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // When
        ResultActions response = mockMvc.perform(put("/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // Then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {

        // Given
        long employeeId = 1L;
        willDoNothing().given(employeeService).deleteEmployee(employeeId);

        // When
        ResultActions response =
                mockMvc.perform(delete("/employees/{id}", employeeId));

        // Then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void givenEmployees_whenGetAllEmployees_thenReturnAllEmployees() throws Exception {

        // Given
        List<Employee> employeeList = List.of(
                Employee.builder().firstName("A").lastName("G").email("A@G").build(),
                Employee.builder().firstName("B").lastName("H").email("B@H").build());
        given(employeeService.getAllEmployees()).willReturn(employeeList);

        // When
        ResultActions response = mockMvc.perform(get("/employees"));

        // Then
        response.andDo(print())
                .andExpect(jsonPath("$.size()", is(employeeList.size())));
    }

    @Test
    public void givenEmployeeId_whenGetEmployee_thenReturnEmployee() throws Exception {

        // Given
        long employeeId = 1L;
        Employee existingEmployee = Employee.builder()
                .firstName("A")
                .lastName("L")
                .email("A@L")
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(existingEmployee));

        // When
        ResultActions response = mockMvc.perform(get("/employees/{id}", employeeId));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(existingEmployee.getFirstName())));
    }

    @Test
    public void givenEmployeeId_whenGetEmployee_thenReturnEmpty() throws Exception {

        // Given
        long employeeId = 1L;
        Employee existingEmployee = Employee.builder()
                .firstName("A")
                .lastName("L")
                .email("A@L")
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        // When
        ResultActions response = mockMvc.perform(get("/employees/{id}", employeeId));

        // Then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }
}
