package com.learn.springboottesting.repository;

import com.learn.springboottesting.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collection;
import java.util.Optional;


@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void should_find_no_employees_if_empty(){
        Collection<Employee> employees = employeeRepository.findAll();
        assertThat(employees).isEmpty();

    }

    @Test
    public void should_store_an_employee(){
        Employee savedEmployee = employeeRepository.save(
                Employee.builder()
                        .firstName("Akash")
                        .lastName("Goyal")
                        .email("akash.goyal@target.com")
                        .build());
        assertThat(savedEmployee).hasFieldOrPropertyWithValue("firstName", "Akash");

    }

    @Test
    public void should_find_all_employees(){
        Employee savedEmployeeOne = Employee.builder().firstName("Ray").lastName("Donovan").email("ray.d@org").build();
        Employee savedEmployeeTwo = Employee.builder().firstName("Michael").lastName("Jackson").email("m.d@org").build();
        Employee savedEmployeeThree = Employee.builder().firstName("Tina").lastName("Asher").email("t.a@org").build();

        entityManager.persist(savedEmployeeOne);
        entityManager.persist(savedEmployeeTwo);
        entityManager.persist(savedEmployeeThree);

        Collection<Employee> employees = employeeRepository.findAll();
        assertThat(employees.size()).isEqualTo(3);
    }

    @Test
    public void should_find_by_email(){
        Employee savedEmployeeOne = Employee.builder().firstName("Ray").lastName("Donovan").email("ray.d@org").build();
        Employee savedEmployeeTwo = Employee.builder().firstName("Michael").lastName("Jackson").email("m.d@org").build();
        Employee savedEmployeeThree = Employee.builder().firstName("Tina").lastName("Asher").email("t.a@org").build();

        entityManager.persist(savedEmployeeOne);
        entityManager.persist(savedEmployeeTwo);
        entityManager.persist(savedEmployeeThree);

        Optional<Employee> employee = employeeRepository.findByEmail("m.d@org");
        assertThat(employee.get()).isNotNull();
    }

    @Test
    public void should_delete_all(){
        Employee savedEmployeeOne = Employee.builder().firstName("Ray").lastName("Donovan").email("ray.d@org").build();
        Employee savedEmployeeTwo = Employee.builder().firstName("Michael").lastName("Jackson").email("m.d@org").build();
        Employee savedEmployeeThree = Employee.builder().firstName("Tina").lastName("Asher").email("t.a@org").build();

        entityManager.persist(savedEmployeeOne);
        entityManager.persist(savedEmployeeTwo);
        entityManager.persist(savedEmployeeThree);

        employeeRepository.deleteAll();
        assertThat(employeeRepository.findAll()).isEmpty();
    }
}
