package app.engines;

import app.contracts.Executable;
import app.entities.Address;
import app.entities.Employee;
import app.entities.Project;
import app.entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Engine implements Executable {
    private EntityManager entityManager;
    private BufferedReader reader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void execute() throws IOException {
        System.out.println("Which task do you want executed? (Type '2' - '13')");
        int problemNumber = Integer.parseInt(this.reader.readLine());
        switch (problemNumber) {
            case 2:
                this.problem02();
                break;

            case 3:
                this.problem03();
                break;

            case 4:
                this.problem04();
                break;

            case 5:
                this.problem05();
                break;

            case 6:
                this.problem06();
                break;

            case 7:
                this.problem07();
                break;

            case 8:
                this.problem08();
                break;

            case 9:
                this.problem09();
                break;

            case 10:
                this.problem10();
                break;

            case 11:
                this.problem11();
                break;

            case 12:
                this.problem12();
                break;

            case 13:
                this.problem13();
                break;
        }
        this.reader.close();
    }


    /**
     * Problem 02. Remove Objects
     */
    private void problem02() { //weird name for the task, since no towns need to be removed
        List<Town> allTowns = this.entityManager.createQuery(
                "SELECT t FROM Town AS t", Town.class
        ).getResultList();

        this.entityManager.getTransaction().begin(); //not needed when extracting information, but needed when updating/inserting/deleting

        for (Town currentTown : allTowns) {
            if (currentTown.getName().length() > 5)
                this.entityManager.detach(currentTown);
        }

        for (Town townWithLessThanSixSymbols : allTowns) {
            townWithLessThanSixSymbols.setName(townWithLessThanSixSymbols.getName().toLowerCase());
        }

        this.entityManager.getTransaction().commit();
    }

    /**
     * Problem 03. Contains Employee
     */
    private void problem03() throws IOException {
        String employeeNames = this.reader.readLine();

        try {
            Employee employee = this.entityManager.createQuery(
                    "SELECT e FROM Employee e " +
                            "WHERE CONCAT(e.firstName, ' ', e.lastName) = :employee_names", Employee.class)
                    .setParameter("employee_names", employeeNames)
                    .getSingleResult();

            System.out.println("Yes");
        } catch (NoResultException nre) {
            System.out.println("No");
        }
    }

    /**
     * Problem 4. Employees with Salary Over 50 000
     */
    private void problem04() {
        try {
            BigDecimal salary = BigDecimal.valueOf(50000);

            List<Employee> employeesWithHighSalaries = this.entityManager.createQuery(
                    "SELECT e FROM Employee e " +
                            "WHERE e.salary > :salary", Employee.class
            ).setParameter("salary", salary)
                    .getResultList();

            for (Employee currentEmployee : employeesWithHighSalaries) {
                System.out.println(currentEmployee.getFirstName());
            }
        } catch (NoResultException nre) {
            System.out.println("No such employees");
        }
    }

    /**
     * Problem 5. Employees from Department
     */
    private void problem05() {
        List<Object[]> resultList = this.entityManager.createQuery("" +
                "SELECT e.firstName, e.lastName, e.salary, d.name " +
                "FROM Employee e " +
                "INNER JOIN e.department d " +
                "WHERE d.name = 'Research and Development' " +
                "ORDER BY e.salary ASC, e.id ASC", Object[].class)
                .getResultList();

        for (Object[] objects : resultList) {
            String firstName = (String) objects[0];
            String lastName = (String) objects[1];
            BigDecimal salary = new BigDecimal("" + objects[2]);
            String departmentName = (String) objects[3];

            System.out.printf("%s %s from %s - %.2f%n",
                    firstName, lastName, departmentName, salary);
        }
    }

    /**
     * Problem 6. Adding a New Address and Updating Employee
     */
    private void problem06() throws IOException {
        System.out.print("Enter a last name of an employee: ");
        String existingEmployeeLastName = this.reader.readLine();

        List<Employee> employeesWithSpecificLastName = this.entityManager
                .createQuery("" +
                        "SELECT e " +
                        "FROM Employee e " +
                        "WHERE e.lastName = :lastName", Employee.class
                ).setParameter("lastName", existingEmployeeLastName)
                .getResultList();

        Address address = new Address();
        address.setText("Vitoshka 15");

        this.entityManager.getTransaction().begin();

        this.entityManager.persist(address); //add new address
        for (Employee employee : employeesWithSpecificLastName) {
            employee.setAddress(address);
        }

        this.entityManager.getTransaction().commit();
    }

    /**
     * Problem 7. Addresses with Employee Count
     */
    private void problem07() {
        List<Object[]> resultList = this.entityManager.createQuery("" +
                        "SELECT " +
                        "   a.text," +
                        "   t.name," +
                        "   COUNT(e.id) AS employees_count " +
                        "FROM Employee e " +
                        "RIGHT JOIN e.address a " + //an address could have 0 employees living there
                        "INNER JOIN a.town t " +
                        "GROUP BY a.id " +
                        "ORDER BY employees_count DESC, a.id ASC",
                Object[].class)
                .setMaxResults(10)
                .getResultList();

        for (Object[] currentResult : resultList) {
            String addressText = (String) currentResult[0];
            String townName = (String) currentResult[1];
            int employeesCount = Integer.parseInt("" + currentResult[2]);

            System.out.printf("%s, %s - %d employees%n", addressText, townName, employeesCount);
        }
    }

    /**
     * Problem 8.	Get Employee with Project
     */
    private void problem08() throws IOException {
        int employeeId = Integer.parseInt(this.reader.readLine());
        Employee employee = this.entityManager.find(Employee.class, employeeId);

        if (employee == null)
            throw new IllegalArgumentException("Employee not found!");

        Set<Project> employeeProjects = employee.getProjects();
        String jobTitle = employee.getJobTitle();

        StringBuilder result = new StringBuilder();

        result.append(employee.getFirstName()).append(" ").append(employee.getLastName())
                .append(" - ").append(jobTitle).append(System.lineSeparator());

        if (employeeProjects.isEmpty()) {
            result.append("\tNo projects");
            System.out.println(result);
            return;
        }

        for (Project employeeProject : employeeProjects) {
            result.append("\t").append(employeeProject.getName()).append(System.lineSeparator());
        }

        System.out.println(result.toString().trim());
    }

    /**
     * Problem 9.	Find Latest 10 Projects
     */
    private void problem09() {
        List<Project> lastTenProjects = this.entityManager.createQuery("" +
                "SELECT p FROM Project p " +
                "ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultList();

        StringBuilder result = new StringBuilder();

        for (Project currentProject : lastTenProjects) {
            result.append("Project name: ").append(currentProject.getName()).append(System.lineSeparator());
            result.append("\tProject description: ").append(currentProject.getDescription()).append(System.lineSeparator());
            result.append("\tProject start date: ").append(currentProject.getStartDate()).append(System.lineSeparator());
            result.append("\tProject end date: ").append(currentProject.getEndDate()).append(System.lineSeparator());
        }

        System.out.print(result);
    }

    /**
     * Problem 10.	Increase Salaries
     */
    private void problem10() {
        List<String> departments = new ArrayList<>(4);
        //Engineering, Tool Design, Marketing or Information Services
        departments.add("Engineering");
        departments.add("Tool Design");
        departments.add("Marketing");
        departments.add("Information Services");

        List<Employee> employeesFromCertainDepartments = this.entityManager.createQuery("" +
                "SELECT e FROM Employee e " +
                "INNER JOIN e.department d " +
                "WHERE d.name IN :departments", Employee.class)
                .setParameter("departments", departments)
                .getResultList();

        this.entityManager.getTransaction().begin();

        for (Employee currentEmployee : employeesFromCertainDepartments) {
            BigDecimal oldSalary = currentEmployee.getSalary();
            currentEmployee.setSalary(oldSalary.multiply(BigDecimal.valueOf(1.12)));

            System.out.printf("Name: %s | Old salary: %.2f | New salary: %.2f | Department: %s%n",
                    currentEmployee.getFirstName() + " " + currentEmployee.getLastName(),
                    oldSalary,
                    currentEmployee.getSalary(),
                    currentEmployee.getDepartment().getName());
        }

        this.entityManager.getTransaction().commit();
    }

    /**
     * Problem 11.	Remove Towns
     */
    private void problem11() throws IOException {
        String townNameToDelete = this.reader.readLine();

        Town town = this.entityManager.createQuery("" +
                "SELECT t FROM Town t WHERE t.name = :townName", Town.class)
                .setParameter("townName", townNameToDelete)
                .getSingleResult();

        List<Address> addressesInTown = this.entityManager.createQuery("" +
                "SELECT a FROM Address a WHERE a.town = :town", Address.class)
                .setParameter("town", town)
                .getResultList();

        List<Employee> employeesFromTown = this.entityManager.createQuery("" +
                "SELECT e FROM Employee e WHERE e.address IN :addresses", Employee.class)
                .setParameter("addresses", addressesInTown)
                .getResultList();

        this.entityManager.getTransaction().begin();

        for (Employee employee : employeesFromTown)
            employee.setAddress(null);

        for (Address address : addressesInTown)
            this.entityManager.remove(address);

        this.entityManager.remove(town);

        this.entityManager.getTransaction().commit();

        int addressesCount = addressesInTown.size();

        int peopleWhosAddressIsSetToNullCount = employeesFromTown.size();
        List<String> employeeNames = employeesFromTown
                .stream()
                .map(e -> e.getFirstName() + " " + e.getLastName())
                .collect(Collectors.toList());

        System.out.printf("%d %s in %s deleted. People who lost their address: %n\t%s",
                addressesCount,
                addressesCount == 1 ? "address" : "addresses",
                town.getName(),
                String.join("\n\t", employeeNames));
    }

    /**
     * Problem 12.	Find Employees by First Name
     *
     * @throws IOException Note: LIKE and LEFT MySQL functions are not supported in Hibernate
     */
    private void problem12() throws IOException {
        String firstNameStartPattern = this.reader.readLine();
        int patternLength = firstNameStartPattern.length();

        List<Employee> employees = this.entityManager.createQuery(
                String.format(
                        "SELECT e FROM Employee e " +
                                "WHERE SUBSTRING(e.firstName, 1, %d) = '%s'", patternLength, firstNameStartPattern), Employee.class)
                .getResultList();

        for (Employee employee : employees) {
            System.out.printf("%s %s - %s - ($%.2f)%n",
                    employee.getFirstName(), employee.getLastName(), employee.getJobTitle(), employee.getSalary());
        }
    }

    /**
     * Problem 13.	Employees Maximum Salaries
     *
     * Note: Results may be different from the task description given examples,
     * because some salaries were increased in task 10.
     */
    private void problem13() {
        List<Object[]> result = this.entityManager.createQuery("" +
                "SELECT " +
                "   d.name, " +
                "   MAX(e.salary) AS biggest_salary " +
                "FROM Employee e " +
                "RIGHT JOIN e.department d " +
                "GROUP BY d.id " +
                "HAVING MAX(e.salary) < 30000 OR MAX(e.salary) > 70000", Object[].class
        ).getResultList();

        for (Object[] currentResult : result) {
            System.out.printf("%s - %s%n", currentResult[0], currentResult[1]);
        }
    }
}
