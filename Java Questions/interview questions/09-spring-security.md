# Spring Security Interview Questions

## 1. What is Spring Security?

**Answer:**

Spring Security is a powerful authentication and authorization framework for Java applications.

**Key Features:**
1. **Authentication** - Who are you? (Login)
2. **Authorization** - What can you do? (Permissions)
3. **Protection** - CSRF, XSS, Session Fixation
4. **OAuth2** - Social login (Google, Facebook)
5. **JWT** - Token-based authentication

**Add Dependency:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

**Default Behavior:**
- All endpoints are secured
- Default username: `user`
- Default password: Printed in console logs

---

## 2. How to configure Basic Authentication?

**Answer:**

**Security Configuration:**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll()  // Allow public endpoints
                .anyRequest().authenticated()  // All other endpoints need authentication
            )
            .httpBasic();  // Enable Basic Auth
        
        return http.build();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
            .username("john")
            .password(passwordEncoder().encode("password123"))
            .roles("USER")
            .build();
        
        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("admin123"))
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

**Usage:**
```bash
# Request with Basic Auth
curl -u john:password123 http://localhost:8080/api/users
```

---

## 3. How to implement Database Authentication?

**Answer:**

**User Entity:**
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String username;
    
    private String password;
    
    private String roles;  // ROLE_USER,ROLE_ADMIN
}
```

**UserDetailsService Implementation:**
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
            .roles(user.getRoles().split(","))
            .build();
    }
}
```

**Security Configuration:**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin();  // Enable form-based login
        
        return http.build();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

**Registration Service:**
```java
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User registerUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));  // Encrypt password
        user.setRoles("ROLE_USER");
        
        return userRepository.save(user);
    }
}
```

---

## 4. What is JWT (JSON Web Token)?

**Answer:**

JWT is a compact, URL-safe token for authentication. It contains user information and signature.

**Structure:**
```
Header.Payload.Signature

eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImpvaG4iLCJyb2xlcyI6WyJVU0VSIl19.signature
```

**Components:**
1. **Header** - Token type and algorithm
2. **Payload** - User data (username, roles)
3. **Signature** - Verifies token authenticity

**Add Dependency:**
```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
</dependency>
```

**JWT Utility:**
```java
@Component
public class JwtUtil {
    
    private String SECRET_KEY = "mysecretkey123";
    
    // Generate token
    public String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // 10 hours
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }
    
    // Extract username from token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }
    
    // Validate token
    public boolean validateToken(String token, String username) {
        return (extractUsername(token).equals(username) && !isTokenExpired(token));
    }
    
    private Claims extractClaims(String token) {
        return Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .getBody();
    }
    
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
```

---

## 5. How to implement JWT Authentication?

**Answer:**

**Login Controller:**
```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            // Authenticate user
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
                )
            );
            
            // Generate JWT token
            String token = jwtUtil.generateToken(request.getUsername());
            
            return ResponseEntity.ok(new AuthResponse(token));
            
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

@Data
class AuthRequest {
    private String username;
    private String password;
}

@Data
@AllArgsConstructor
class AuthResponse {
    private String token;
}
```

**JWT Filter:**
```java
@Component
public class JwtFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);
            
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                if (jwtUtil.validateToken(token, username)) {
                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                        );
                    
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        
        chain.doFilter(request, response);
    }
}
```

**Security Configuration:**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private JwtFilter jwtFilter;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // No sessions
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
```

**Usage:**
```bash
# 1. Login
POST /api/auth/login
Body: {"username": "john", "password": "password123"}
Response: {"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."}

# 2. Access protected endpoint
GET /api/users
Header: Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## 6. What is the difference between Authentication and Authorization?

**Answer:**

**Authentication** - Who are you? (Login)
```java
@PostMapping("/login")
public String login(@RequestBody LoginRequest request) {
    // Verify username and password
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        )
    );
    
    return "Authenticated successfully";
}
```

**Authorization** - What can you do? (Permissions)
```java
@RestController
@RequestMapping("/api")
public class UserController {
    
    @GetMapping("/users")
    @PreAuthorize("hasRole('USER')")  // Only users with USER role
    public List<User> getUsers() {
        return userService.findAll();
    }
    
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")  // Only admins can delete
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
    
    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")  // Both can access
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }
}
```

**Enable Method Security:**
```java
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    // ...
}
```

---

## 7. What are Spring Security Roles and Authorities?

**Answer:**

**Roles:**
- High-level permissions (e.g., ROLE_USER, ROLE_ADMIN)
- Prefix with "ROLE_"

**Authorities:**
- Fine-grained permissions (e.g., READ, WRITE, DELETE)
- No prefix

**Example:**
```java
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .roles("USER", "ADMIN")  // Roles
            .authorities("READ", "WRITE", "DELETE")  // Authorities
            .build();
    }
}
```

**Using Roles:**
```java
@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")  // Only ADMIN role
                .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")  // USER or ADMIN
                .anyRequest().authenticated()
            );
        
        return http.build();
    }
}
```

**Using Authorities:**
```java
@RestController
public class UserController {
    
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('READ')")
    public List<User> getUsers() {
        return userService.findAll();
    }
    
    @PostMapping("/users")
    @PreAuthorize("hasAuthority('WRITE')")
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }
    
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('DELETE')")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
```

---

## 8. What is CSRF Protection?

**Answer:**

CSRF (Cross-Site Request Forgery) is an attack where a malicious site tricks a user into performing unwanted actions.

**How it works:**
1. User logs into `bank.com`
2. User visits malicious site `evil.com`
3. `evil.com` sends request to `bank.com` (using user's cookies)
4. `bank.com` thinks it's legitimate request from user

**Spring Security Protection:**

**Enabled by default for stateful applications (form login):**
```java
@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable();  // Disable for REST APIs with JWT
        
        return http.build();
    }
}
```

**When to disable CSRF:**
- REST APIs with JWT (stateless)
- APIs consumed by non-browser clients

**When to enable CSRF:**
- Traditional web applications with sessions
- Form-based login

---

## 9. How to implement OAuth2 Login (Google, Facebook)?

**Answer:**

**Add Dependency:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-client</artifactId>
</dependency>
```

**Configuration:**
```properties
# application.properties

# Google OAuth2
spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_CLIENT_SECRET
spring.security.oauth2.client.registration.google.scope=profile,email

# Facebook OAuth2
spring.security.oauth2.client.registration.facebook.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.facebook.client-secret=YOUR_CLIENT_SECRET
spring.security.oauth2.client.registration.facebook.scope=public_profile,email
```

**Security Configuration:**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth -> oauth
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
            );
        
        return http.build();
    }
}
```

**Controller:**
```java
@RestController
public class HomeController {
    
    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OAuth2User principal) {
        String name = principal.getAttribute("name");
        String email = principal.getAttribute("email");
        
        return "Welcome " + name + " (" + email + ")";
    }
}
```

**Login Page:**
```html
<a href="/oauth2/authorization/google">Login with Google</a>
<a href="/oauth2/authorization/facebook">Login with Facebook</a>
```

---

## 10. How to get Current Logged-In User?

**Answer:**

**Method 1: SecurityContextHolder**
```java
@RestController
public class UserController {
    
    @GetMapping("/me")
    public String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        return "Current user: " + username;
    }
}
```

**Method 2: @AuthenticationPrincipal**
```java
@RestController
public class UserController {
    
    @GetMapping("/me")
    public String getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return "Current user: " + userDetails.getUsername();
    }
}
```

**Method 3: Principal**
```java
@RestController
public class UserController {
    
    @GetMapping("/me")
    public String getCurrentUser(Principal principal) {
        return "Current user: " + principal.getName();
    }
}
```

**Get Full User Object:**
```java
@RestController
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/me")
    public User getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return userRepository.findByUsername(username);
    }
}
```

---

## 11. How to implement Refresh Token?

**Answer:**

Refresh tokens allow getting new access tokens without re-login.

**Token Service:**
```java
@Service
public class TokenService {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    
    public TokenResponse generateTokens(String username) {
        // Access token (short-lived - 15 minutes)
        String accessToken = jwtUtil.generateToken(username, 15 * 60 * 1000);
        
        // Refresh token (long-lived - 7 days)
        String refreshToken = UUID.randomUUID().toString();
        
        RefreshToken token = new RefreshToken();
        token.setToken(refreshToken);
        token.setUsername(username);
        token.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(token);
        
        return new TokenResponse(accessToken, refreshToken);
    }
    
    public String refreshAccessToken(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
            .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        
        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired");
        }
        
        // Generate new access token
        return jwtUtil.generateToken(token.getUsername(), 15 * 60 * 1000);
    }
}
```

**Refresh Token Entity:**
```java
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String token;
    private String username;
    private LocalDateTime expiryDate;
}
```

**Controller:**
```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private TokenService tokenService;
    
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        // Authenticate user
        authenticationManager.authenticate(/*...*/);
        
        // Generate tokens
        TokenResponse tokens = tokenService.generateTokens(request.getUsername());
        
        return ResponseEntity.ok(tokens);
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshRequest request) {
        String newAccessToken = tokenService.refreshAccessToken(request.getRefreshToken());
        
        return ResponseEntity.ok(new TokenResponse(newAccessToken, request.getRefreshToken()));
    }
}

@Data
class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
```

---

## 12. How to implement Remember Me functionality?

**Answer:**

Remember Me keeps users logged in even after closing browser.

**Configuration:**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            )
            .formLogin()
            .and()
            .rememberMe(remember -> remember
                .key("mySecretKey")
                .tokenValiditySeconds(7 * 24 * 60 * 60)  // 7 days
                .userDetailsService(userDetailsService)
            );
        
        return http.build();
    }
}
```

**Login Form:**
```html
<form action="/login" method="post">
    <input type="text" name="username" placeholder="Username" />
    <input type="password" name="password" placeholder="Password" />
    <input type="checkbox" name="remember-me" /> Remember Me
    <button type="submit">Login</button>
</form>
```

---

## 13. How to implement Password Reset?

**Answer:**

```java
@Service
public class PasswordResetService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // Step 1: Generate reset token
    public void sendResetEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Generate token
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(1));  // 1 hour validity
        userRepository.save(user);
        
        // Send email
        String resetLink = "http://localhost:8080/reset-password?token=" + token;
        emailService.send(email, "Password Reset", "Click here: " + resetLink);
    }
    
    // Step 2: Verify token and reset password
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
            .orElseThrow(() -> new RuntimeException("Invalid token"));
        
        // Check token expiry
        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }
        
        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }
}
```

**Controller:**
```java
@RestController
@RequestMapping("/api/password")
public class PasswordController {
    
    @Autowired
    private PasswordResetService passwordResetService;
    
    @PostMapping("/forgot")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        passwordResetService.sendResetEmail(request.getEmail());
        return ResponseEntity.ok("Reset email sent");
    }
    
    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        passwordResetService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("Password reset successful");
    }
}
```

---

## 14. What is @Secured vs @PreAuthorize vs @RolesAllowed?

**Answer:**

| Annotation | Framework | Features |
|------------|-----------|----------|
| @Secured | Spring Security | Basic role check |
| @RolesAllowed | Java EE | Basic role check |
| @PreAuthorize | Spring Security | SpEL expressions |

**@Secured:**
```java
@RestController
public class UserController {
    
    @Secured("ROLE_ADMIN")
    public void deleteUser(Long id) {
        userService.delete(id);
    }
    
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public List<User> getUsers() {
        return userService.findAll();
    }
}
```

**@PreAuthorize (Most powerful):**
```java
@RestController
public class UserController {
    
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long id) {
        userService.delete(id);
    }
    
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<User> getUsers() {
        return userService.findAll();
    }
    
    @PreAuthorize("#username == authentication.principal.username")
    public User getUser(String username) {
        return userService.findByUsername(username);
    }
    
    @PreAuthorize("hasRole('ADMIN') and #user.status == 'ACTIVE'")
    public void updateUser(User user) {
        userService.update(user);
    }
}
```

**@RolesAllowed:**
```java
@RestController
public class UserController {
    
    @RolesAllowed("ADMIN")
    public void deleteUser(Long id) {
        userService.delete(id);
    }
}
```

---

## 15. How to implement Account Lockout after failed login attempts?

**Answer:**

```java
@Entity
public class User {
    @Id
    private Long id;
    
    private String username;
    private String password;
    
    private int failedLoginAttempts = 0;
    private boolean accountLocked = false;
    private LocalDateTime lockTime;
}
```

**Login Service:**
```java
@Service
public class LoginService {
    
    @Autowired
    private UserRepository userRepository;
    
    private static final int MAX_ATTEMPTS = 3;
    private static final long LOCK_DURATION_MINUTES = 15;
    
    public void loginFailed(String username) {
        User user = userRepository.findByUsername(username);
        
        if (user != null) {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
            
            if (user.getFailedLoginAttempts() >= MAX_ATTEMPTS) {
                user.setAccountLocked(true);
                user.setLockTime(LocalDateTime.now());
            }
            
            userRepository.save(user);
        }
    }
    
    public void loginSuccess(String username) {
        User user = userRepository.findByUsername(username);
        
        if (user != null) {
            user.setFailedLoginAttempts(0);
            user.setAccountLocked(false);
            user.setLockTime(null);
            userRepository.save(user);
        }
    }
    
    public boolean isAccountLocked(String username) {
        User user = userRepository.findByUsername(username);
        
        if (user == null || !user.isAccountLocked()) {
            return false;
        }
        
        // Check if lock duration has passed
        LocalDateTime unlockTime = user.getLockTime().plusMinutes(LOCK_DURATION_MINUTES);
        
        if (LocalDateTime.now().isAfter(unlockTime)) {
            // Unlock account
            user.setAccountLocked(false);
            user.setFailedLoginAttempts(0);
            user.setLockTime(null);
            userRepository.save(user);
            return false;
        }
        
        return true;
    }
}
```

**Authentication Provider:**
```java
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    
    @Autowired
    private LoginService loginService;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        // Check if account is locked
        if (loginService.isAccountLocked(username)) {
            throw new LockedException("Account is locked");
        }
        
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            if (passwordEncoder.matches(password, userDetails.getPassword())) {
                loginService.loginSuccess(username);
                return new UsernamePasswordAuthenticationToken(
                    username, password, userDetails.getAuthorities()
                );
            }
            
        } catch (Exception e) {
            loginService.loginFailed(username);
            throw new BadCredentialsException("Invalid credentials");
        }
        
        throw new BadCredentialsException("Invalid credentials");
    }
}
```
