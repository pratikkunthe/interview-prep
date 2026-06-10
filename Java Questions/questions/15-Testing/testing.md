# Testing

## Unit Testing

### 1. JUnit - Write test cases and explain

**Answer:**

**JUnit** = Testing framework for Java

**Think of it like:** Quality checker - tests if your code works correctly

**Basic Test:**

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    
    @Test
    public void testAdd() {
        Calculator calc = new Calculator();
        int result = calc.add(5, 3);
        assertEquals(8, result);
    }
    
    @Test
    public void testSubtract() {
        Calculator calc = new Calculator();
        int result = calc.subtract(10, 4);
        assertEquals(6, result);
    }
    
    @Test
    public void testDivide() {
        Calculator calc = new Calculator();
        double result = calc.divide(10, 2);
        assertEquals(5.0, result, 0.001);
    }
    
    @Test
    public void testDivideByZero() {
        Calculator calc = new Calculator();
        assertThrows(ArithmeticException.class, () -> {
            calc.divide(10, 0);
        });
    }
}
```

**Common Assertions:**

```java
@Test
public void testAssertions() {
    // Equality
    assertEquals(5, 5);
    assertNotEquals(5, 10);
    
    // Null checks
    assertNull(null);
    assertNotNull("Hello");
    
    // Boolean
    assertTrue(true);
    assertFalse(false);
    
    // Arrays/Collections
    assertArrayEquals(new int[]{1, 2, 3}, new int[]{1, 2, 3});
    
    // Exceptions
    assertThrows(IllegalArgumentException.class, () -> {
        throw new IllegalArgumentException();
    });
}
```

**Spring Boot Testing:**

```java
@SpringBootTest
class UserServiceTest {
    
    @Autowired
    private UserService userService;
    
    @MockBean
    private UserRepository userRepository;
    
    @Test
    void testGetUser() {
        // Mock data
        User mockUser = new User(1L, "John");
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        
        // Test
        User result = userService.getUser(1L);
        
        // Verify
        assertEquals("John", result.getName());
        verify(userRepository).findById(1L);
    }
}
```

**Test Annotations:**

- `@Test` - Marks test method
- `@BeforeEach` - Runs before each test
- `@AfterEach` - Runs after each test
- `@BeforeAll` - Runs once before all tests
- `@AfterAll` - Runs once after all tests

**Complete Example:**

```java
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    
    private UserService userService;
    
    @BeforeEach
    void setUp() {
        userService = new UserService();
    }
    
    @Test
    @DisplayName("Test user creation")
    void testCreateUser() {
        User user = new User("John", "john@example.com");
        User created = userService.createUser(user);
        
        assertNotNull(created.getId());
        assertEquals("John", created.getName());
    }
    
    @Test
    void testGetUserNotFound() {
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUser(999L);
        });
    }
    
    @AfterEach
    void tearDown() {
        // Cleanup
    }
}
```

**Key Points:**
- Write tests for each method
- Test normal cases and edge cases
- Use assertions to verify results
- Mock dependencies

**Simple rule:** JUnit = Test your code. Write @Test methods, use assertions to verify!

---

## Testing Best Practices

### 1. Testing strategies for Spring Boot applications

**Answer:**

Testing strategies for Spring Boot:

**1. Unit Tests** - Test individual components

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    void testGetUser() {
        User mockUser = new User(1L, "John");
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        
        User result = userService.getUser(1L);
        
        assertEquals("John", result.getName());
    }
}
```

**2. Integration Tests** - Test with Spring context

```java
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @Test
    void testGetUser() throws Exception {
        User user = new User(1L, "John");
        when(userService.getUser(1L)).thenReturn(user);
        
        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("John"));
    }
}
```

**3. Web Layer Tests** - Test controllers only

```java
@WebMvcTest(UserController.class)
class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @Test
    void testGetUser() throws Exception {
        // Test controller only
    }
}
```

**4. Data Layer Tests** - Test repositories

```java
@DataJpaTest
class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    void testFindByName() {
        User user = new User("John");
        userRepository.save(user);
        
        Optional<User> found = userRepository.findByName("John");
        
        assertTrue(found.isPresent());
    }
}
```

**5. Test Slices:**

| Annotation | Tests |
|------------|-------|
| `@WebMvcTest` | Controllers only |
| `@DataJpaTest` | Repositories only |
| `@JsonTest` | JSON serialization |
| `@SpringBootTest` | Full application |

**Best Practices:**

**1. Test Structure (AAA Pattern)**
```java
@Test
void testMethod() {
    // Arrange - Setup
    User user = new User("John");
    
    // Act - Execute
    User result = userService.createUser(user);
    
    // Assert - Verify
    assertNotNull(result.getId());
}
```

**2. Test Naming**
```java
@Test
void shouldReturnUser_WhenValidIdProvided() {
    // Clear test name
}
```

**3. One Assertion Per Test (when possible)**
```java
@Test
void testUserName() {
    assertEquals("John", user.getName());
}

@Test
void testUserEmail() {
    assertEquals("john@example.com", user.getEmail());
}
```

**4. Mock External Dependencies**
```java
@MockBean
private PaymentService paymentService;

@MockBean
private EmailService emailService;
```

**5. Use Test Containers for Database**
```java
@Testcontainers
@SpringBootTest
class IntegrationTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13");
}
```

**Complete Example:**

```java
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @Test
    void shouldCreateUser_WhenValidDataProvided() throws Exception {
        User user = new User("John", "john@example.com");
        when(userService.createUser(any(User.class))).thenReturn(user);
        
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John\",\"email\":\"john@example.com\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("John"));
    }
    
    @Test
    void shouldReturn404_WhenUserNotFound() throws Exception {
        when(userService.getUser(999L))
            .thenThrow(new UserNotFoundException(999L));
        
        mockMvc.perform(get("/api/users/999"))
            .andExpect(status().isNotFound());
    }
}
```

**Key Strategies:**
- ✅ Unit tests for business logic
- ✅ Integration tests for workflows
- ✅ Mock external dependencies
- ✅ Test edge cases
- ✅ Use test slices for focused testing

**Simple rule:** Test at different levels - unit (components), integration (workflows), web (controllers)!
