# Laptop Shop - AI Coding Guidelines

## Architecture Overview
This is a Spring Boot MVC application for an e-commerce laptop shop with separate admin and client interfaces. The architecture follows a layered pattern:
- **Controllers** (`controller/admin/`, `controller/client/`): Handle HTTP requests, validate input, manage sessions
- **Services** (`service/`): Business logic, data processing, file uploads
- **Repositories** (`repository/`): JPA data access layer
- **Domain** (`domain/`): JPA entities with validation annotations
- **Views**: JSP templates in `WEB-INF/view/` using JSTL
- **Config**: Security, web MVC, VNPay payment integration

Key integrations: MySQL database, Spring Security with JDBC sessions, VNPay payment gateway.

## Critical Workflows
- **Build & Run**: Use `./mvnw spring-boot:run` (requires MySQL running on localhost:3306 with 'laptopshop' DB)
- **Database**: JPA auto-DDL update; ensure MySQL connector and session tables initialized
- **File Uploads**: Handled via `UploadService`; files stored in project root directories
- **Payments**: VNPay integration via `VNPayService`; callback at `/payment-callback`

## Project Conventions
- **Dependency Injection**: Constructor injection only (no `@Autowired` fields)
- **Validation**: Use `@Validated` with groups (`CreateGroup`, `UpdateGroup`) on controllers
- **Pagination**: Controllers use `PageRequest.of(page-1, 10)` for admin lists
- **Security**: `@Secured("ROLE_ADMIN")` on admin methods; custom success handler redirects by role
- **Error Handling**: Return to form view on validation errors; log field errors in controllers
- **File Paths**: Static resources in `webapp/resources/`; JSP views reference via `/resources/`
- **Entity Relationships**: Bidirectional with `mappedBy`; lazy loading assumed

## Specific Patterns
- **User Creation**: Encode password with `BCryptPasswordEncoder`; set role via `userService.getRoleByName()`
- **Order Processing**: Use `OrderService` for cart-to-order conversion; update product quantities
- **View Models**: Pass entities directly to JSP; use `model.addAttribute()` for pagination metadata
- **Session Management**: JDBC-backed sessions; remember-me with `SpringSessionRememberMeServices`

## Key Files
- `SecurityConfiguration.java`: Role-based access, session config
- `UserController.java`: Exemplifies CRUD with validation groups and file uploads
- `VNPayConfig.java`: Payment constants and hashing utilities
- `application.properties`: DB config, multipart limits, session settings</content>
<parameter name="filePath">c:/Users/admin/Downloads/spring-mvc-main/spring-mvc-main/.github/copilot-instructions.md