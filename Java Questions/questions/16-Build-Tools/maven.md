# Build Tools and Project Management

## Maven

### 1. What is maven why it's used?

**Answer:**

**Maven** = Build automation and project management tool for Java

**Think of it like:** Smart assistant that builds your project, manages dependencies, and runs tests

**What Maven does:**

**1. Dependency Management**
- Download libraries automatically
- Manage versions
- Resolve conflicts

**2. Build Automation**
- Compile code
- Run tests
- Package application
- Deploy

**3. Project Structure**
- Standard directory layout
- Consistent across projects

**4. Lifecycle Management**
- Predefined build phases
- Clean, compile, test, package, install, deploy

**Why use Maven:**

**Without Maven:**
```bash
# Manual process
1. Download JAR files manually
2. Add to classpath
3. Compile: javac -cp libs/*.jar src/*.java
4. Run tests manually
5. Package JAR manually
# Lots of manual work!
```

**With Maven:**
```xml
<!-- Just declare dependencies -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Maven handles everything -->
mvn clean install
```

**Maven Project Structure:**

```
project/
├── pom.xml              # Project configuration
├── src/
│   ├── main/
│   │   ├── java/        # Source code
│   │   └── resources/   # Config files
│   └── test/
│       ├── java/        # Test code
│       └── resources/   # Test config
└── target/              # Build output
```

**pom.xml Example:**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.example</groupId>
    <artifactId>my-app</artifactId>
    <version>1.0.0</version>
    
    <properties>
        <java.version>17</java.version>
        <spring-boot.version>3.1.0</spring-boot.version>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>${spring-boot.version}</version>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <version>${spring-boot.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

**Maven Lifecycle Phases:**

```
validate → compile → test → package → install → deploy
```

**Common Maven Commands:**

```bash
# Clean build directory
mvn clean

# Compile code
mvn compile

# Run tests
mvn test

# Package application
mvn package

# Install to local repository
mvn install

# Clean and install
mvn clean install

# Run Spring Boot application
mvn spring-boot:run
```

**Benefits:**

| Benefit | Description |
|---------|-------------|
| Dependency Management | Automatic download and management |
| Standard Structure | Consistent project layout |
| Build Automation | One command builds everything |
| Lifecycle | Predefined phases |
| Plugins | Extensible with plugins |

**Dependency Management:**

```xml
<dependencies>
    <!-- Maven downloads these automatically -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <version>3.1.0</version>
    </dependency>
</dependencies>
```

**Maven Repository:**

- **Local Repository:** `~/.m2/repository` (on your machine)
- **Central Repository:** Maven Central (online)
- **Private Repository:** Company/internal repos

**How it works:**

```
1. Declare dependency in pom.xml
       ↓
2. Maven checks local repository
       ↓
3. If not found, downloads from central repository
       ↓
4. Stores in local repository
       ↓
5. Adds to classpath
```

**Key Points:**
- Maven = Build tool + Dependency manager
- Uses pom.xml for configuration
- Standard project structure
- Automatic dependency resolution
- Lifecycle-based builds

**Simple rule:** Maven = Smart build tool. Declare dependencies, run commands, Maven handles the rest!

---

## Agile Methodology

### 1. Agile methodology

**Answer:**

**Agile** = Iterative approach to software development

**Think of it like:** Building in small steps, getting feedback, and improving continuously

**Agile Principles:**

**1. Iterative Development**
- Work in small iterations (sprints)
- 1-4 weeks per iteration
- Deliver working software frequently

**2. Customer Collaboration**
- Work closely with customers
- Get feedback early
- Adapt to changes

**3. Responding to Change**
- Welcome changing requirements
- Adapt quickly
- Flexible planning

**4. Working Software**
- Deliver working features
- Not just documentation
- Value delivery

**Agile vs Waterfall:**

**Waterfall:**
```
Requirements → Design → Development → Testing → Deployment
(Sequential, rigid)
```

**Agile:**
```
Sprint 1: Requirements → Design → Dev → Test → Deploy
Sprint 2: Requirements → Design → Dev → Test → Deploy
Sprint 3: Requirements → Design → Dev → Test → Deploy
(Iterative, flexible)
```

**Agile Frameworks:**

**1. Scrum**
- Sprints (1-4 weeks)
- Daily standups
- Sprint planning
- Sprint review
- Retrospective

**2. Kanban**
- Visual board
- Continuous flow
- Limit work in progress
- No fixed sprints

**3. Extreme Programming (XP)**
- Pair programming
- Test-driven development
- Continuous integration
- Refactoring

**Scrum Roles:**

- **Product Owner** - Defines requirements
- **Scrum Master** - Facilitates process
- **Development Team** - Builds software

**Scrum Events:**

- **Sprint Planning** - Plan work for sprint
- **Daily Standup** - 15-min daily sync
- **Sprint Review** - Show completed work
- **Retrospective** - Improve process

**Agile Benefits:**

| Benefit | Description |
|---------|-------------|
| Flexibility | Adapt to changes |
| Faster Delivery | Working software quickly |
| Customer Feedback | Early and frequent |
| Quality | Continuous testing |
| Team Collaboration | Better communication |

**Agile Manifesto:**

1. Individuals and interactions over processes and tools
2. Working software over comprehensive documentation
3. Customer collaboration over contract negotiation
4. Responding to change over following a plan

**Simple rule:** Agile = Build in small steps, get feedback, adapt quickly. Iterative and flexible approach!
