# Spring Security and Authentication

## Security Basics

### 1. What is Spring Security

**Answer:**

**Spring Security** = Framework for securing Spring applications (authentication + authorization)

**Think of it like:** Security guard for your application - checks who you are and what you can do

**What it provides:**

**1. Authentication** - Who are you?
- Login/logout
- Password encoding
- Remember me
- OAuth, JWT support

**2. Authorization** - What can you do?
- Role-based access
- Method-level security
- URL-based security

**3. Protection Against Attacks**
- CSRF protection
- Session fixation
- Clickjacking
- XSS protection

**Basic Setup:**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

**Default Behavior:**

Once you add Spring Security dependency:
- All endpoints are secured by default
- Default username: `user`
- Default password: Generated (shown in console)
- Basic authentication enabled

**Configuration:**

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

**Key Features:**
- ✅ Authentication (login)
- ✅ Authorization (roles/permissions)
- ✅ Password encoding
- ✅ Session management
- ✅ CSRF protection
- ✅ Method security

**Simple explanation:** Spring Security = Bouncer for your app. Checks ID (authentication) and access level (authorization)!

---

### 2. How do you implement authentication and authorization in Spring Boot?

**Answer:**

**Authentication** = Verifying who you are (login)
**Authorization** = Verifying what you can do (permissions)

**Step 1: Add Dependency**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

**Step 2: Configure Security**

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll()  // Public access
                .requestMatchers("/admin/**").hasRole("ADMIN")  // Admin only
                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")  // User or Admin
                .anyRequest().authenticated()  // All others need login
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        
        return http.build();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .build();
        
        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("ADMIN")
            .build();
        
        return new InMemoryUserDetailsManager(user, admin);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

**Step 3: Custom UserDetailsService (Database)**

```java
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .roles(user.getRole())
            .build();
    }
}
```

**Step 4: Method-Level Security**

```java
@Configuration
@EnableMethodSecurity
public class MethodSecurityConfig {
}

@Service
public class UserService {
    
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long id) {
        // Only ADMIN can call this
    }
    
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public User getUser(Long id) {
        // USER or ADMIN can call this
    }
    
    @Secured("ROLE_ADMIN")
    public void adminOnlyMethod() {
        // Only ADMIN
    }
}
```

**Complete Example:**

```java
// Entity
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String role;  // USER, ADMIN
    
    // Getters and setters
}

// Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

// Security Config
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .httpBasic();  // Basic auth for API
        
        return http.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

// Controller
@RestController
@RequestMapping("/api")
public class ApiController {
    
    @GetMapping("/public/hello")
    public String publicHello() {
        return "Public Hello";  // No auth needed
    }
    
    @GetMapping("/user/info")
    public String userInfo() {
        return "User Info";  // Auth needed
    }
    
    @GetMapping("/admin/users")
    public List<User> getAllUsers() {
        return userService.findAll();  // ADMIN only
    }
}
```

**Key Points:**
- Authentication = Login (who you are)
- Authorization = Permissions (what you can do)
- Use `@EnableWebSecurity` for web security
- Use `@EnableMethodSecurity` for method security
- Password must be encoded (BCrypt)

**Simple rule:** Authentication checks identity, Authorization checks permissions. Configure both in SecurityConfig!

---

### 3. How do you secure REST APIs in Spring Boot?

**Answer:**

Ways to secure REST APIs:

**1. Basic Authentication**

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            )
            .httpBasic();  // Basic auth for REST API
        
        return http.build();
    }
}
```

**2. JWT (JSON Web Token)** ✅ (Most common for REST)

```java
// Add JWT dependency
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
</dependency>

// JWT Filter
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                   HttpServletResponse response, 
                                   FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);
        
        if(token != null && validateToken(token)) {
            String username = getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            UsernamePasswordAuthenticationToken authentication = 
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

// Security Config with JWT
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private JwtAuthenticationFilter jwtFilter;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // Disable CSRF for REST API
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}

// Auth Controller
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        
        String token = jwtUtil.generateToken(authentication.getName());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
```

**3. API Key Authentication**

```java
@Component
public class ApiKeyFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                   HttpServletResponse response, 
                                   FilterChain filterChain) throws ServletException, IOException {
        String apiKey = request.getHeader("X-API-Key");
        
        if(apiKey == null || !isValidApiKey(apiKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        
        filterChain.doFilter(request, response);
    }
    
    private boolean isValidApiKey(String apiKey) {
        // Check against database or config
        return "valid-api-key".equals(apiKey);
    }
}
```

**4. OAuth2**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
```

**Complete REST API Security Example:**

```java
@Configuration
@EnableWebSecurity
public class RestApiSecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // REST APIs are stateless
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Stateless
            .and()
            .httpBasic();  // Or JWT filter
        
        return http.build();
    }
}

// Public endpoint
@RestController
@RequestMapping("/api/public")
public class PublicController {
    @GetMapping("/info")
    public String getInfo() {
        return "Public information";
    }
}

// Protected endpoint
@RestController
@RequestMapping("/api/protected")
public class ProtectedController {
    @GetMapping("/data")
    public ResponseEntity<String> getData() {
        String username = SecurityContextHolder.getContext()
            .getAuthentication().getName();
        return ResponseEntity.ok("Data for: " + username);
    }
}
```

**Key Points:**
- REST APIs are stateless (no sessions)
- Use JWT for token-based auth
- Disable CSRF for REST APIs
- Use `SessionCreationPolicy.STATELESS`
- Secure endpoints with roles

**Simple rule:** For REST APIs, use JWT tokens, disable CSRF, and make it stateless!

---

### 4. Spring security annotations

**Answer:**

Spring Security annotations for securing methods and classes:

**1. @EnableWebSecurity** - Enable web security
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Security configuration
}
```

**2. @EnableMethodSecurity** - Enable method-level security
```java
@Configuration
@EnableMethodSecurity
public class MethodSecurityConfig {
}
```

**3. @PreAuthorize** - Check before method execution
```java
@Service
public class UserService {
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long id) {
        // Only ADMIN can execute
    }
    
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public User getUser(Long id) {
        // USER or ADMIN can execute
    }
    
    @PreAuthorize("#id == authentication.principal.id")
    public User getOwnProfile(Long id) {
        // Can only access own profile
    }
}
```

**4. @PostAuthorize** - Check after method execution
```java
@PostAuthorize("returnObject.owner == authentication.name")
public User getUser(Long id) {
    // Check after method returns
    return userRepository.findById(id);
}
```

**5. @Secured** - Simple role-based security
```java
@Service
public class UserService {
    @Secured("ROLE_ADMIN")
    public void adminMethod() {
        // Only ADMIN
    }
    
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public void userOrAdminMethod() {
        // USER or ADMIN
    }
}
```

**6. @RolesAllowed** - JSR-250 annotation
```java
@RolesAllowed("ADMIN")
public void adminOnlyMethod() {
    // Only ADMIN
}
```

**7. @PreFilter** - Filter collection before method
```java
@PreFilter("filterObject.owner == authentication.name")
public void updateUsers(List<User> users) {
    // Only users owned by current user
}
```

**8. @PostFilter** - Filter collection after method
```java
@PostFilter("filterObject.owner == authentication.name")
public List<User> getUsers() {
    // Returns only owned users
    return userRepository.findAll();
}
```

**Complete Example:**

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    // Configuration
}

@Service
public class UserService {
    
    // Role-based
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    // Multiple roles
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    // Expression-based
    @PreAuthorize("hasPermission(#id, 'User', 'READ')")
    public User getUserWithPermission(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    // Filter results
    @PostFilter("filterObject.owner == authentication.name")
    public List<User> getMyUsers() {
        return userRepository.findAll();
    }
}

// Controller
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        // Method security applies
        return userService.getUser(id);
    }
}
```

**Common Expressions:**

| Expression | Meaning |
|------------|---------|
| `hasRole('ADMIN')` | Has ADMIN role |
| `hasAnyRole('USER', 'ADMIN')` | Has USER or ADMIN role |
| `hasAuthority('READ')` | Has READ authority |
| `isAuthenticated()` | User is authenticated |
| `permitAll()` | Allow all |
| `denyAll()` | Deny all |

**Key Points:**
- `@PreAuthorize` = Check before execution
- `@PostAuthorize` = Check after execution
- `@Secured` = Simple role check
- Enable with `@EnableMethodSecurity`

**Simple rule:** Use `@PreAuthorize` for method security, `@Secured` for simple role checks!

---

## JWT (JSON Web Tokens)

### 1. What is JWT, and how does it work?

**Answer:**

**JWT (JSON Web Token)** = Compact way to securely transmit information between parties

**Think of it like:** Digital ID card that proves who you are

**JWT Structure:**

```
header.payload.signature

Example:
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

**Three Parts:**

**1. Header** - Algorithm and token type
```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

**2. Payload** - Claims (data)
```json
{
  "sub": "1234567890",
  "name": "John Doe",
  "iat": 1516239022,
  "exp": 1516242622
}
```

**3. Signature** - Verifies token hasn't been tampered
```
HMACSHA256(
  base64UrlEncode(header) + "." + base64UrlEncode(payload),
  secret
)
```

**How JWT Works:**

```
1. User logs in
       ↓
2. Server validates credentials
       ↓
3. Server creates JWT token
       ↓
4. Server sends token to client
       ↓
5. Client stores token (localStorage, cookie)
       ↓
6. Client sends token with each request (Authorization header)
       ↓
7. Server validates token
       ↓
8. Server processes request
```

**Implementation:**

```java
// JWT Utility
@Component
public class JwtUtil {
    private String secret = "mySecretKey";
    private long expiration = 86400000; // 24 hours
    
    public String generateToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);
        
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }
    
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

// Auth Controller
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        
        String token = jwtUtil.generateToken(authentication.getName());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}

// JWT Filter
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                   HttpServletResponse response, 
                                   FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);
        
        if(token != null && jwtUtil.validateToken(token)) {
            String username = jwtUtil.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            UsernamePasswordAuthenticationToken authentication = 
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
```

**Benefits:**
- ✅ Stateless (no server-side sessions)
- ✅ Scalable (works across servers)
- ✅ Self-contained (has all info)
- ✅ Secure (signed)

**Key Points:**
- JWT = Token with user info
- Stateless authentication
- Sent in Authorization header
- Validated on each request

**Simple explanation:** JWT = Digital passport. Shows it once, use it everywhere until it expires!

---

### 2. How microservices are authorizing with JWT token - How you are checking if that user is authorized or not what methods do you use to authorize with user

**Answer:**

In microservices, JWT tokens are used for authorization across services:

**Flow:**

```
1. User logs in → Auth Service
       ↓
2. Auth Service validates → Returns JWT token
       ↓
3. Client stores token
       ↓
4. Client sends token with requests to any microservice
       ↓
5. Each microservice validates token
       ↓
6. Extracts user info and roles from token
       ↓
7. Checks authorization (roles/permissions)
       ↓
8. Processes request if authorized
```

**Implementation:**

**1. Auth Service (Issues JWT)**

```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Validate credentials
        User user = userService.validateCredentials(request.getUsername(), request.getPassword());
        
        // Create JWT with roles
        String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());
        
        return ResponseEntity.ok(new AuthResponse(token));
    }
}

// JWT with roles
@Component
public class JwtUtil {
    public String generateToken(String username, List<String> roles) {
        return Jwts.builder()
            .setSubject(username)
            .claim("roles", roles)  // Add roles to token
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }
    
    public Claims getClaims(String token) {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody();
    }
}
```

**2. Microservice (Validates JWT)**

```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                   HttpServletResponse response, 
                                   FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);
        
        if(token != null && jwtUtil.validateToken(token)) {
            Claims claims = jwtUtil.getClaims(token);
            String username = claims.getSubject();
            List<String> roles = (List<String>) claims.get("roles");
            
            // Create authentication with roles
            List<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
            
            UsernamePasswordAuthenticationToken authentication = 
                new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
```

**3. Authorization Check**

```java
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Order> getOrders() {
        // Only USER or ADMIN can access
        return orderService.findAll();
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @orderService.isOwner(#id, authentication.name)")
    public Order getOrder(@PathVariable Long id) {
        // ADMIN or owner can access
        return orderService.findById(id);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteOrder(@PathVariable Long id) {
        // Only ADMIN
        orderService.delete(id);
    }
}

// Service with custom authorization
@Service
public class OrderService {
    
    public boolean isOwner(Long orderId, String username) {
        Order order = orderRepository.findById(orderId).orElse(null);
        return order != null && order.getOwner().equals(username);
    }
}
```

**4. Method-Level Authorization**

```java
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    // Enables @PreAuthorize, @Secured, etc.
}

@Service
public class OrderService {
    
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteOrder(Long id) {
        // Only ADMIN can call
    }
    
    @PreAuthorize("hasRole('USER')")
    public Order createOrder(Order order) {
        // Only USER can create
        return orderRepository.save(order);
    }
    
    @PreAuthorize("hasRole('ADMIN') or #order.owner == authentication.name")
    public Order updateOrder(Order order) {
        // ADMIN or owner can update
        return orderRepository.save(order);
    }
}
```

**5. Programmatic Authorization**

```java
@Service
public class OrderService {
    
    @Autowired
    private SecurityContext securityContext;
    
    public Order getOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        
        Authentication auth = securityContext.getAuthentication();
        String username = auth.getName();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        
        // Check authorization
        boolean isAdmin = authorities.stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        
        if(!isAdmin && !order.getOwner().equals(username)) {
            throw new AccessDeniedException("Not authorized");
        }
        
        return order;
    }
}
```

**Complete Flow:**

```java
// 1. Client sends request with JWT
GET /api/orders/123
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

// 2. Filter extracts and validates token
// 3. Sets authentication in SecurityContext
// 4. Controller checks @PreAuthorize
// 5. If authorized, process request
// 6. If not authorized, return 403 Forbidden
```

**Key Methods for Authorization:**

1. **@PreAuthorize** - Check before method
2. **@Secured** - Role-based check
3. **SecurityContext** - Programmatic check
4. **hasRole()** - Check role
5. **hasAuthority()** - Check authority

**Simple rule:** Extract roles from JWT → Set in SecurityContext → Use @PreAuthorize to check authorization!
