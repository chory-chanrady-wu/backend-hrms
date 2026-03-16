//package com.chanrady.hrms.util;
//
//import com.chanrady.hrms.entity.Announcement;
//import com.chanrady.hrms.entity.Attendance;
//import com.chanrady.hrms.entity.AuditLog;
//import com.chanrady.hrms.entity.Benefit;
//import com.chanrady.hrms.entity.Department;
//import com.chanrady.hrms.entity.Employee;
//import com.chanrady.hrms.entity.EmployeeBenefit;
//import com.chanrady.hrms.entity.LeaveRequest;
//import com.chanrady.hrms.entity.LeaveType;
//import com.chanrady.hrms.entity.Payroll;
//import com.chanrady.hrms.entity.Position;
//import com.chanrady.hrms.entity.Role;
//import com.chanrady.hrms.entity.User;
//import com.chanrady.hrms.repository.AnnouncementRepository;
//import com.chanrady.hrms.repository.AttendanceRepository;
//import com.chanrady.hrms.repository.AuditLogRepository;
//import com.chanrady.hrms.repository.BenefitRepository;
//import com.chanrady.hrms.repository.DepartmentRepository;
//import com.chanrady.hrms.repository.EmployeeBenefitRepository;
//import com.chanrady.hrms.repository.EmployeeRepository;
//import com.chanrady.hrms.repository.LeaveRequestRepository;
//import com.chanrady.hrms.repository.LeaveTypeRepository;
//import com.chanrady.hrms.repository.PayrollRepository;
//import com.chanrady.hrms.repository.PositionRepository;
//import com.chanrady.hrms.repository.RoleRepository;
//import com.chanrady.hrms.repository.UserRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@Profile({"dev", "local"})
//public class SeedDataInitializer implements CommandLineRunner {
//
//    private static final Logger logger = LoggerFactory.getLogger(SeedDataInitializer.class);
//
//    private final RoleRepository roleRepository;
//    private final DepartmentRepository departmentRepository;
//    private final PositionRepository positionRepository;
//    private final UserRepository userRepository;
//    private final EmployeeRepository employeeRepository;
//    private final AttendanceRepository attendanceRepository;
//    private final LeaveTypeRepository leaveTypeRepository;
//    private final LeaveRequestRepository leaveRequestRepository;
//    private final BenefitRepository benefitRepository;
//    private final EmployeeBenefitRepository employeeBenefitRepository;
//    private final PayrollRepository payrollRepository;
//    private final AuditLogRepository auditLogRepository;
//    private final AnnouncementRepository announcementRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public SeedDataInitializer(
//            RoleRepository roleRepository,
//            DepartmentRepository departmentRepository,
//            PositionRepository positionRepository,
//            UserRepository userRepository,
//            EmployeeRepository employeeRepository,
//            AttendanceRepository attendanceRepository,
//            LeaveTypeRepository leaveTypeRepository,
//            LeaveRequestRepository leaveRequestRepository,
//            BenefitRepository benefitRepository,
//            EmployeeBenefitRepository employeeBenefitRepository,
//            PayrollRepository payrollRepository,
//            AuditLogRepository auditLogRepository,
//            AnnouncementRepository announcementRepository,
//            PasswordEncoder passwordEncoder
//    ) {
//        this.roleRepository = roleRepository;
//        this.departmentRepository = departmentRepository;
//        this.positionRepository = positionRepository;
//        this.userRepository = userRepository;
//        this.employeeRepository = employeeRepository;
//        this.attendanceRepository = attendanceRepository;
//        this.leaveTypeRepository = leaveTypeRepository;
//        this.leaveRequestRepository = leaveRequestRepository;
//        this.benefitRepository = benefitRepository;
//        this.employeeBenefitRepository = employeeBenefitRepository;
//        this.payrollRepository = payrollRepository;
//        this.auditLogRepository = auditLogRepository;
//        this.announcementRepository = announcementRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        try {
//            logger.info("========== SEED DATA INITIALIZER STARTING ==========");
//
//            long roleCount = roleRepository.count();
//            long userCount = userRepository.count();
//            long employeeCount = employeeRepository.count();
//            long departmentCount = departmentRepository.count();
//
//            logger.info("Database state: Roles={}, Users={}, Employees={}, Departments={}", roleCount, userCount, employeeCount, departmentCount);
//
//            // Only skip if we have significant data (users and employees exist)
//            if (userCount > 0 && employeeCount > 0) {
//                logger.info("Complete data already exists. Skipping seed data initialization.");
//                return;
//            }
//
//            logger.info("Incomplete data found. Starting/resuming seed initialization...");
//
//            List<Role> roles = roleRepository.count() > 0 ?
//                roleRepository.findAll() : seedRoles();
//            if (roleRepository.count() == 0) {
//                logger.info("✓ Seeded {} Roles", roles.size());
//            } else {
//                logger.info("✓ Using existing {} Roles", roles.size());
//            }
//
//            List<Department> departments = departmentRepository.count() > 0 ?
//                departmentRepository.findAll() : seedDepartments();
//            if (departmentRepository.count() == 0) {
//                logger.info("✓ Seeded {} Departments", departments.size());
//            } else {
//                logger.info("✓ Using existing {} Departments", departments.size());
//            }
//
//            List<LeaveType> leaveTypes = leaveTypeRepository.count() > 0 ?
//                leaveTypeRepository.findAll() : seedLeaveTypes();
//            if (leaveTypeRepository.count() == 0) {
//                logger.info("✓ Seeded {} LeaveTypes", leaveTypes.size());
//            } else {
//                logger.info("✓ Using existing {} LeaveTypes", leaveTypes.size());
//            }
//
//            List<Benefit> benefits = benefitRepository.count() > 0 ?
//                benefitRepository.findAll() : seedBenefits();
//            if (benefitRepository.count() == 0) {
//                logger.info("✓ Seeded {} Benefits", benefits.size());
//            } else {
//                logger.info("✓ Using existing {} Benefits", benefits.size());
//            }
//
//            List<Position> positions = positionRepository.count() > 0 ?
//                positionRepository.findAll() : seedPositions(departments);
//            if (positionRepository.count() == 0) {
//                logger.info("✓ Seeded {} Positions", positions.size());
//            } else {
//                logger.info("✓ Using existing {} Positions", positions.size());
//            }
//
//            List<User> users = userRepository.count() > 0 ?
//                userRepository.findAll() : seedUsers(roles);
//            if (userRepository.count() == 0) {
//                logger.info("✓ Seeded {} Users", users.size());
//            } else {
//                logger.info("✓ Using existing {} Users", users.size());
//            }
//
//            // Assign department heads after users are created
//            assignDepartmentHeads(departments, users);
//
//            List<Employee> employees = employeeRepository.count() > 0 ?
//                employeeRepository.findAll() : seedEmployees(users, departments, positions);
//            if (employeeRepository.count() == 0) {
//                logger.info("✓ Seeded {} Employees", employees.size());
//            } else {
//                logger.info("✓ Using existing {} Employees", employees.size());
//            }
//
//            if (attendanceRepository.count() == 0) {
//                seedAttendance(employees);
//                logger.info("✓ Seeded Attendance records");
//            }
//
//            if (leaveRequestRepository.count() == 0) {
//                seedLeaveRequests(employees, leaveTypes);
//                logger.info("✓ Seeded LeaveRequest records");
//            }
//
//            if (payrollRepository.count() == 0) {
//                seedPayroll(employees);
//                logger.info("✓ Seeded Payroll records");
//            }
//
//            if (employeeBenefitRepository.count() == 0) {
//                seedEmployeeBenefits(employees, benefits);
//                logger.info("✓ Seeded EmployeeBenefit records");
//            }
//
//            if (auditLogRepository.count() == 0) {
//                seedAuditLogs(users);
//                logger.info("✓ Seeded AuditLog records");
//            }
//
//            if (announcementRepository.count() == 0) {
//                seedAnnouncements(users);
//                logger.info("✓ Seeded Announcement records");
//            }
//
//            logger.info("========== SEED DATA INITIALIZATION COMPLETED SUCCESSFULLY ==========");
//        } catch (Exception e) {
//            logger.error("========== SEED DATA INITIALIZATION FAILED ==========", e);
//            throw e;
//        }
//    }
//
//    private List<Role> seedRoles() {
//        List<Role> roles = new ArrayList<>();
//
//        Role admin = new Role();
//        admin.setRoleName("ADMIN");
//        admin.setPermissions(jsonArray("read", "write", "update", "delete", "manage_users"));
//        roles.add(admin);
//
//        Role hr = new Role();
//        hr.setRoleName("HR");
//        hr.setPermissions(jsonArray("read", "write", "update", "manage_employees"));
//        roles.add(hr);
//
//        Role manager = new Role();
//        manager.setRoleName("MANAGER");
//        manager.setPermissions(jsonArray("read", "write", "approve_leave"));
//        roles.add(manager);
//
//        Role employee = new Role();
//        employee.setRoleName("EMPLOYEE");
//        employee.setPermissions(jsonArray("read", "request_leave"));
//        roles.add(employee);
//
//        Role accountant = new Role();
//        accountant.setRoleName("ACCOUNTANT");
//        accountant.setPermissions(jsonArray("read", "manage_payroll"));
//        roles.add(accountant);
//
//        return roleRepository.saveAll(roles);
//    }
//
//    private List<Department> seedDepartments() {
//        List<Department> departments = new ArrayList<>();
//
//        Department d1 = new Department();
//        d1.setName("Engineering");
//        d1.setDescription("Software development and platform");
//        departments.add(d1);
//
//        Department d2 = new Department();
//        d2.setName("Human Resources");
//        d2.setDescription("People operations and recruitment");
//        departments.add(d2);
//
//        Department d3 = new Department();
//        d3.setName("Finance");
//        d3.setDescription("Accounting and payroll operations");
//        departments.add(d3);
//
//        Department d4 = new Department();
//        d4.setName("Marketing");
//        d4.setDescription("Brand and growth marketing");
//        departments.add(d4);
//
//        Department d5 = new Department();
//        d5.setName("Operations");
//        d5.setDescription("Business operations and support");
//        departments.add(d5);
//
//        return departmentRepository.saveAll(departments);
//    }
//
//    private List<LeaveType> seedLeaveTypes() {
//        List<LeaveType> leaveTypes = new ArrayList<>();
//
//        LeaveType lt1 = new LeaveType();
//        lt1.setName("Annual Leave");
//        lt1.setMaxDays(18);
//        leaveTypes.add(lt1);
//
//        LeaveType lt2 = new LeaveType();
//        lt2.setName("Sick Leave");
//        lt2.setMaxDays(10);
//        leaveTypes.add(lt2);
//
//        LeaveType lt3 = new LeaveType();
//        lt3.setName("Maternity Leave");
//        lt3.setMaxDays(90);
//        leaveTypes.add(lt3);
//
//        LeaveType lt4 = new LeaveType();
//        lt4.setName("Paternity Leave");
//        lt4.setMaxDays(15);
//        leaveTypes.add(lt4);
//
//        LeaveType lt5 = new LeaveType();
//        lt5.setName("Unpaid Leave");
//        lt5.setMaxDays(30);
//        leaveTypes.add(lt5);
//
//        return leaveTypeRepository.saveAll(leaveTypes);
//    }
//
//    private List<Benefit> seedBenefits() {
//        List<Benefit> benefits = new ArrayList<>();
//
//        Benefit b1 = new Benefit();
//        b1.setName("Health Insurance");
//        b1.setDescription("Company sponsored health plan");
//        benefits.add(b1);
//
//        Benefit b2 = new Benefit();
//        b2.setName("Transport Allowance");
//        b2.setDescription("Monthly transportation support");
//        benefits.add(b2);
//
//        Benefit b3 = new Benefit();
//        b3.setName("Meal Allowance");
//        b3.setDescription("Daily meal stipend");
//        benefits.add(b3);
//
//        Benefit b4 = new Benefit();
//        b4.setName("Phone Allowance");
//        b4.setDescription("Mobile communication support");
//        benefits.add(b4);
//
//        Benefit b5 = new Benefit();
//        b5.setName("Annual Bonus");
//        b5.setDescription("Performance-based yearly bonus");
//        benefits.add(b5);
//
//        return benefitRepository.saveAll(benefits);
//    }
//
//    private List<Position> seedPositions(List<Department> departments) {
//        List<Position> positions = new ArrayList<>();
//
//        Position p1 = new Position();
//        p1.setTitle("Senior Developer");
//        p1.setDescription("Build and maintain core backend services");
//        p1.setSalary(new BigDecimal("1800.00"));
//        p1.setDepartment(departments.get(0));
//        positions.add(p1);
//
//        Position p2 = new Position();
//        p2.setTitle("HR Specialist");
//        p2.setDescription("Manage recruitment and employee relations");
//        p2.setSalary(new BigDecimal("1200.00"));
//        p2.setDepartment(departments.get(1));
//        positions.add(p2);
//
//        Position p3 = new Position();
//        p3.setTitle("Accountant");
//        p3.setDescription("Handle invoices and monthly closing");
//        p3.setSalary(new BigDecimal("1300.00"));
//        p3.setDepartment(departments.get(2));
//        positions.add(p3);
//
//        Position p4 = new Position();
//        p4.setTitle("Marketing Executive");
//        p4.setDescription("Run digital and campaign activities");
//        p4.setSalary(new BigDecimal("1100.00"));
//        p4.setDepartment(departments.get(3));
//        positions.add(p4);
//
//        Position p5 = new Position();
//        p5.setTitle("Operations Officer");
//        p5.setDescription("Coordinate operational processes");
//        p5.setSalary(new BigDecimal("1000.00"));
//        p5.setDepartment(departments.get(4));
//        positions.add(p5);
//
//        return positionRepository.saveAll(positions);
//    }
//
//    private List<User> seedUsers(List<Role> roles) {
//        List<User> users = new ArrayList<>();
//
//        User u1 = new User();
//        u1.setUsername("seed.admin");
//        u1.setFullName("Seed Admin");
//        u1.setEmail("seed.admin@hrms.local");
//        u1.setPasswordHash(passwordEncoder.encode("seed_hash_1"));
//        u1.setStatus(true);
//        u1.setRole(roles.get(0));
//        users.add(u1);
//
//        User u2 = new User();
//        u2.setUsername("seed.hr");
//        u2.setFullName("Seed HR");
//        u2.setEmail("seed.hr@hrms.local");
//        u2.setPasswordHash(passwordEncoder.encode("seed_hash_2"));
//        u2.setStatus(true);
//        u2.setRole(roles.get(1));
//        users.add(u2);
//
//        User u3 = new User();
//        u3.setUsername("seed.manager");
//        u3.setFullName("Seed Manager");
//        u3.setEmail("seed.manager@hrms.local");
//        u3.setPasswordHash(passwordEncoder.encode("seed_hash_3"));
//        u3.setStatus(true);
//        u3.setRole(roles.get(2));
//        users.add(u3);
//
//        User u4 = new User();
//        u4.setUsername("seed.employee");
//        u4.setFullName("Seed Employee");
//        u4.setEmail("seed.employee@hrms.local");
//        u4.setPasswordHash(passwordEncoder.encode("seed_hash_4"));
//        u4.setStatus(true);
//        u4.setRole(roles.get(3));
//        users.add(u4);
//
//        User u5 = new User();
//        u5.setUsername("seed.accountant");
//        u5.setFullName("Seed Accountant");
//        u5.setEmail("seed.accountant@hrms.local");
//        u5.setPasswordHash(passwordEncoder.encode("seed_hash_5"));
//        u5.setStatus(true);
//        u5.setRole(roles.get(4));
//        users.add(u5);
//
//        return userRepository.saveAll(users);
//    }
//
//    private List<Employee> seedEmployees(List<User> users, List<Department> departments, List<Position> positions) {
//        List<Employee> employees = new ArrayList<>();
//
//        Employee e1 = new Employee();
//        e1.setUser(users.get(0));
//        e1.setDepartment(departments.get(0));
//        e1.setPosition(positions.get(0));
//        e1.setEmploymentType("Full-time");
//        e1.setSalary(new BigDecimal("1800.00"));
//        e1.setHireDate(LocalDate.of(2022, 1, 10));
//        e1.setDateOfBirth(LocalDate.of(1994, 5, 14));
//        e1.setNationality("Cambodian");
//        e1.setAddress("Phnom Penh, Cambodia");
//        e1.setStatus(true);
//        e1.setImageUrl("https://res.cloudinary.com/demo/image/upload/sample.jpg");
//        employees.add(e1);
//
//        Employee e2 = new Employee();
//        e2.setUser(users.get(1));
//        e2.setDepartment(departments.get(1));
//        e2.setPosition(positions.get(1));
//        e2.setEmploymentType("Full-time");
//        e2.setSalary(new BigDecimal("1200.00"));
//        e2.setHireDate(LocalDate.of(2022, 2, 15));
//        e2.setDateOfBirth(LocalDate.of(1996, 8, 22));
//        e2.setNationality("Cambodian");
//        e2.setAddress("Siem Reap, Cambodia");
//        e2.setStatus(true);
//        e2.setImageUrl("https://res.cloudinary.com/demo/image/upload/woman.jpg");
//        employees.add(e2);
//
//        Employee e3 = new Employee();
//        e3.setUser(users.get(2));
//        e3.setDepartment(departments.get(2));
//        e3.setPosition(positions.get(2));
//        e3.setEmploymentType("Contract");
//        e3.setSalary(new BigDecimal("1300.00"));
//        e3.setHireDate(LocalDate.of(2023, 3, 1));
//        e3.setDateOfBirth(LocalDate.of(1993, 11, 3));
//        e3.setNationality("Cambodian");
//        e3.setAddress("Battambang, Cambodia");
//        e3.setStatus(true);
//        e3.setImageUrl("https://res.cloudinary.com/demo/image/upload/man.jpg");
//        employees.add(e3);
//
//        Employee e4 = new Employee();
//        e4.setUser(users.get(3));
//        e4.setDepartment(departments.get(3));
//        e4.setPosition(positions.get(3));
//        e4.setEmploymentType("Part-time");
//        e4.setSalary(new BigDecimal("1100.00"));
//        e4.setHireDate(LocalDate.of(2023, 6, 20));
//        e4.setDateOfBirth(LocalDate.of(1998, 2, 19));
//        e4.setNationality("Cambodian");
//        e4.setAddress("Kampot, Cambodia");
//        e4.setStatus(true);
//        e4.setImageUrl("https://res.cloudinary.com/demo/image/upload/marketing.jpg");
//        employees.add(e4);
//
//        Employee e5 = new Employee();
//        e5.setUser(users.get(4));
//        e5.setDepartment(departments.get(4));
//        e5.setPosition(positions.get(4));
//        e5.setEmploymentType("Full-time");
//        e5.setSalary(new BigDecimal("1000.00"));
//        e5.setHireDate(LocalDate.of(2024, 1, 5));
//        e5.setDateOfBirth(LocalDate.of(1997, 9, 27));
//        e5.setNationality("Cambodian");
//        e5.setAddress("Kandal, Cambodia");
//        e5.setStatus(true);
//        e5.setImageUrl("https://res.cloudinary.com/demo/image/upload/office.jpg");
//        employees.add(e5);
//
//        return employeeRepository.saveAll(employees);
//    }
//
//    private void seedAttendance(List<Employee> employees) {
//        List<Attendance> records = new ArrayList<>();
//
//        for (int i = 0; i < 5; i++) {
//            Attendance attendance = new Attendance();
//            attendance.setEmployee(employees.get(i));
//            attendance.setCheckIn(LocalDateTime.now().minusDays(5 - i).withHour(8).withMinute(30));
//            attendance.setCheckOut(LocalDateTime.now().minusDays(5 - i).withHour(17).withMinute(30));
//            attendance.setStatus("present");
//            records.add(attendance);
//        }
//
//        attendanceRepository.saveAll(records);
//    }
//
//    private void seedLeaveRequests(List<Employee> employees, List<LeaveType> leaveTypes) {
//        List<LeaveRequest> requests = new ArrayList<>();
//
//        for (int i = 0; i < 5; i++) {
//            LeaveRequest request = new LeaveRequest();
//            request.setEmployee(employees.get(i));
//            request.setLeaveType(leaveTypes.get(i));
//            request.setStartDate(LocalDate.now().plusDays(i + 1));
//            request.setEndDate(LocalDate.now().plusDays(i + 2));
//            request.setReason("Seed leave request " + (i + 1));
//            request.setStatus(i % 2 == 0 ? "pending" : "approved");
//            requests.add(request);
//        }
//
//        leaveRequestRepository.saveAll(requests);
//    }
//
//    private void seedPayroll(List<Employee> employees) {
//        List<Payroll> payrolls = new ArrayList<>();
//
//        for (int i = 0; i < 5; i++) {
//            Payroll payroll = new Payroll();
//            payroll.setEmployee(employees.get(i));
//            payroll.setBaseSalary(employees.get(i).getSalary());
//            payroll.setBonus(new BigDecimal("100.00"));
//            payroll.setDeduction(new BigDecimal("50.00"));
//            payroll.setPayDate(LocalDate.now().minusDays(i));
//            payroll.setMonth(LocalDate.now().minusDays(i).getMonthValue());
//            payroll.setYear(LocalDate.now().minusDays(i).getYear());
//            payrolls.add(payroll);
//        }
//
//        payrollRepository.saveAll(payrolls);
//    }
//
//    private void seedEmployeeBenefits(List<Employee> employees, List<Benefit> benefits) {
//        List<EmployeeBenefit> mappings = new ArrayList<>();
//
//        for (int i = 0; i < 5; i++) {
//            EmployeeBenefit mapping = new EmployeeBenefit();
//            mapping.setEmployee(employees.get(i));
//            mapping.setBenefit(benefits.get(i));
//            mapping.setStartDate(LocalDate.now().minusMonths(2));
//            mapping.setEndDate(LocalDate.now().plusMonths(10));
//            mappings.add(mapping);
//        }
//
//        employeeBenefitRepository.saveAll(mappings);
//    }
//
//    private void seedAuditLogs(List<User> users) {
//        List<AuditLog> logs = new ArrayList<>();
//
//        for (int i = 0; i < 5; i++) {
//            AuditLog log = new AuditLog();
//            log.setUser(users.get(i));
//            log.setAction("CREATE");
//            log.setTableName("employees");
//            log.setRecordId(i + 1);
//            log.setOldData("{}");
//            log.setNewData("{\"seed\":true,\"index\":" + (i + 1) + "}");
//            log.setIpAddress("127.0.0." + (i + 1));
//            logs.add(log);
//        }
//
//        try {
//            auditLogRepository.saveAll(logs);
//            logger.info("AuditLogs seeded successfully");
//        } catch (Exception e) {
//            logger.warn("Failed to seed AuditLogs (JSON type issue - this is non-critical): {}", e.getMessage());
//            // Continue anyway, as audit logs are not critical for application startup
//        }
//    }
//
//    private void seedAnnouncements(List<User> users) {
//        List<Announcement> announcements = new ArrayList<>();
//
//        Announcement a1 = new Announcement();
//        a1.setTitle("Welcome to HRMS System");
//        a1.setContent("We are pleased to announce the launch of our new Human Resource Management System. This platform will streamline all HR operations including employee management, attendance tracking, and payroll processing.");
//        a1.setPriority("HIGH");
//        a1.setStatus(true);
//        a1.setCreatedBy(users.get(0)); // Admin
//        a1.setPublishedAt(LocalDateTime.now().minusDays(10));
//        a1.setExpiresAt(LocalDateTime.now().plusMonths(3));
//        announcements.add(a1);
//
//        Announcement a2 = new Announcement();
//        a2.setTitle("Annual Leave Policy Update");
//        a2.setContent("Please note that the annual leave policy has been updated. All employees are now entitled to 18 days of paid annual leave per year. For more details, please contact the HR department.");
//        a2.setPriority("MEDIUM");
//        a2.setStatus(true);
//        a2.setCreatedBy(users.get(1)); // HR
//        a2.setPublishedAt(LocalDateTime.now().minusDays(5));
//        a2.setExpiresAt(LocalDateTime.now().plusMonths(6));
//        announcements.add(a2);
//
//        Announcement a3 = new Announcement();
//        a3.setTitle("Company Holiday - Khmer New Year");
//        a3.setContent("The office will be closed from April 13-16 for Khmer New Year celebrations. Regular operations will resume on April 17. Have a wonderful holiday!");
//        a3.setPriority("URGENT");
//        a3.setStatus(true);
//        a3.setCreatedBy(users.get(0)); // Admin
//        a3.setPublishedAt(LocalDateTime.now().minusDays(3));
//        a3.setExpiresAt(LocalDateTime.now().plusDays(45));
//        announcements.add(a3);
//
//        Announcement a4 = new Announcement();
//        a4.setTitle("New Benefits Program");
//        a4.setContent("We are introducing a new comprehensive benefits program including health insurance, transport allowance, and meal stipend for all full-time employees. Details will be shared in next week's meeting.");
//        a4.setPriority("MEDIUM");
//        a4.setStatus(true);
//        a4.setCreatedBy(users.get(1)); // HR
//        a4.setPublishedAt(LocalDateTime.now().minusDays(2));
//        a4.setExpiresAt(LocalDateTime.now().plusMonths(1));
//        announcements.add(a4);
//
//        Announcement a5 = new Announcement();
//        a5.setTitle("Performance Review Schedule");
//        a5.setContent("Annual performance reviews will be conducted in the first week of April. Please prepare your self-assessment and coordinate with your department manager for scheduling.");
//        a5.setPriority("HIGH");
//        a5.setStatus(true);
//        a5.setCreatedBy(users.get(2)); // Manager
//        a5.setPublishedAt(LocalDateTime.now().minusDays(1));
//        a5.setExpiresAt(LocalDateTime.now().plusDays(30));
//        announcements.add(a5);
//
//        announcementRepository.saveAll(announcements);
//    }
//
//    private void assignDepartmentHeads(List<Department> departments, List<User> users) {
//        // Assign department heads based on appropriate roles
//        if (departments.size() >= 5 && users.size() >= 5) {
//            departments.get(0).setHeadOfDepartment(users.get(0)); // Engineering - Admin
//            departments.get(1).setHeadOfDepartment(users.get(1)); // Human Resources - HR
//            departments.get(2).setHeadOfDepartment(users.get(2)); // Finance - Manager
//            departments.get(3).setHeadOfDepartment(users.get(3)); // Marketing - Employee
//            departments.get(4).setHeadOfDepartment(users.get(4)); // Operations - Accountant
//
//            departmentRepository.saveAll(departments);
//            logger.info("✓ Assigned department heads");
//        }
//    }
//
//    private String jsonArray(String... values) {
//        StringBuilder builder = new StringBuilder("[");
//        for (int i = 0; i < values.length; i++) {
//            builder.append('"').append(values[i]).append('"');
//            if (i < values.length - 1) {
//                builder.append(',');
//            }
//        }
//        builder.append(']');
//        return builder.toString();
//    }
//}
//
