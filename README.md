# springboot-testing
Junits and Integration testing

* Use ```main``` branch for App Code
* Use ```feature/junit``` branch for App + Junit Code

### T.D.D. ( Test Driven Development )
```
Software Development approach in which test cases are developed to specify input and output. Then code is written or altered to pass those test cases.
Today TDD follows Continous Development approach.
```

### Test Case
```
Test cases are a reflection of client requirement that needs to be fulfilled by a software.

> Objective
> Pre-Conditions
> Test Case Steps
> Test Data
> Expected vs Actual Result
> Pass or Fail
```

### Unit Testing

```
Testing of individual components of a software with a purpose to validate correct working of respective pieces.

Advantages
1. Enhances Quality of the software
2. Decreases the maintenance and software support costs
3. Living documentation of the product
4. Ability to test multiple scenarios
```

Frameworks - TestNG, jUnit

### Integration Testing
```
Testing of all components together as a group to expose defects in interactions / data communications.
Close to Software Requirement Specification SRS 
```

### Terminologies
#### Mocking
```
Process of removing the need of external dependencies to create a controlled environment.
It is achieved by creating fake objects whose behavior is close to the actual ones.

> Classes with Side Effects, Non Deterministic Behavior
> Web Services
> Db Connections
```

#### Stubbing
```
Replacing actual method function classes invocation using mock object to give hard coded responses.
It helps test the class / method in isolation.
```
#### Spying
```
Spying = Mocking + Stubbing
```
### Spring Unit Testing

#### spring-boot-starter-test dependency

```
./gradlew dependencies --configuration testCompileClasspath
```

```
Junit       ->        
Mockito     ->
Hamcrest    ->
AssertJ     ->
JsonAssert  ->
JsonPath    ->
```

#### Test Annotations
```
@WebMvcTest     -> Junit for Web related code
@DataJpaTest    -> Junit for JPA related code
@RestClientTest -> Junit for Rest Template related code
@JsonTest       -> Junit for Json Serde
@DataMongoTest  -> Junit for Mongo related Code
@JdbcTest       -> Junit for JDBC related code

@SpringBootTest -> Junit / Integration related code 
```

#### Spring Context Annotations
```
@Controller, @ControllerAdvice, @JsonComponent, Converter, Filter, WebMvcConfigurer
@Repository, EntityManager, TestEntityManager, DataSource
```
#### Non - Spring Context Annotations
```
@Controller, @Service, @Component, @Repository
```

#### 3 tier architecture

###### Controller Layer
```
Controllers Exception Handlers Servlets Filters
Junit5, Mockito, Hamcrest, Assertj, JsonPath
```

###### Service Layer
```
App / Business Logic
Junit5, Mockito, Assertj
```

###### Repository Layer
```
Logic to interact with Storage Systems
Libraries - Junit5, Mockito, Jupiter, H2
```

### Testable Coding Practices
```
> SOLID
> Constructor / Setter Injections
> Avoid Static Methods / Variabes
> Strict Naming Conventions
> Clean Code and Documentation
> Code must be fast
> Dont mix Unit Testing and Integration Testing
```

#### Pre-requisites - Docker
