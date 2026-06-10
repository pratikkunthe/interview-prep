import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class SortEmployeeBySalaryAscandDesc {
	public static void main(String[] args) {
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee("John", 50000));
		employees.add(new Employee("Jane", 60000));
		employees.add(new Employee("Jack", 40000));
		
		employees.stream().sorted(Comparator.comparing(Employee::getSalary)).collect(Collectors.toList()).forEach(System.out::println);
		System.err.println("======================");
		
		employees.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).collect(Collectors.toList()).forEach(System.out::println);
		System.err.println("======================");
		
		//find second highest salary
		System.out.println(employees.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).skip(1).findFirst().get());
	}
}

class Employee {
	private String name;
	private double salary;
	public Employee(String name, double salary) {
		this.name = name;
		this.salary = salary;
	}
	public String getName() {
		return name;
	}
	public double getSalary() {
		return salary;
	}
	@Override
	public String toString() {
		return "Employee{name='" + name + "', salary=" + salary + '}';
	}
}