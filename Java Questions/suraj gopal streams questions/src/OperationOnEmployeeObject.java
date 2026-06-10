import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class OperationOnEmployeeObject {
	
	public static void main(String[] args) {
		
		List<EmployeeStreamOperation> empList = new ArrayList<>();
		empList.add(new EmployeeStreamOperation(1, "abc", 28, 123, "F", "HR", "Hyderabad", 2020));
		empList.add(new EmployeeStreamOperation(2, "abc", 29, 454, "F", "HR", "Hyderabad", 2015));
		empList.add(new EmployeeStreamOperation(3, "abc", 30, 456, "M", "HR", "Chennai", 2014));
		empList.add(new EmployeeStreamOperation(4, "abc", 32, 12873, "F", "HR", "Chennai", 2013));

		empList.add(new EmployeeStreamOperation(5, "abc", 22, 34234, "F", "IT", "Pune", 2013));
		empList.add(new EmployeeStreamOperation(6, "abc", 27, 45, "M", "IT", "Pune", 2017));
		empList.add(new EmployeeStreamOperation(7, "abc", 26, 67, "F", "IT", "Pune", 2016));
		empList.add(new EmployeeStreamOperation(8, "abc", 23, 67, "M", "IT", "Pune", 2015));
		empList.add(new EmployeeStreamOperation(9, "abc", 25, 78, "M", "IT", "Hyderabad", 2010));
		
		
		System.err.println("=====================sort by city=======================");
		
		//sort by city
		empList.stream().collect(Collectors.groupingBy(EmployeeStreamOperation::getCity)).entrySet().stream().forEach(System.out::println);
		
		System.err.println("======================find the count of male and female======================");
		
		//find the count of male and female
		empList.stream().collect(Collectors.groupingBy(EmployeeStreamOperation::getGender,Collectors.counting())).entrySet().stream().forEach(System.out::println);
	
		System.err.println("====================get all names of departments========================");
		
		//get all names of departments
		empList.stream().collect(Collectors.groupingBy(EmployeeStreamOperation::getDeptName)).entrySet().stream().map(m->m.getKey()).collect(Collectors.toUnmodifiableList()).forEach(System.out::println);
		
		System.err.println("========================employee id whose age is greater than 25====================");
		
		//employee id whose age is greater than 25
		empList.stream().filter(e->e.getAge()>25).map(EmployeeStreamOperation::getId).collect(Collectors.toList()).forEach(System.out::println);;
		
		System.err.println("======================employee details age greater than 25======================");
		
		//employee details age greater than 25
		empList.stream().filter(e->e.getAge()>25).collect(Collectors.toList()).forEach(System.out::println);
		
		System.err.println("=====================find Maximum Age of Employee=======================");
		
		//find Maximum Age of Employee
		System.out.println(empList.stream().sorted(Comparator.comparing(EmployeeStreamOperation::getAge).reversed()).findFirst().get());
		
		System.err.println("=====================Find the department which has the highest number of employees.=======================");
		
		//Find the department which has the highest number of employees.
		System.out.println(empList.stream().collect(Collectors.groupingBy(EmployeeStreamOperation::getDeptName, Collectors.counting())).entrySet().stream().max(Map.Entry.comparingByValue()).get());
		
		System.err.println("=====================Average salary of each department.=======================");
		
		//Average salary of each department.
		empList.stream().collect(Collectors.groupingBy(EmployeeStreamOperation::getDeptName,Collectors.averagingDouble(EmployeeStreamOperation::getSalary))).entrySet().stream().forEach(System.out::println);
	
		System.err.println("=====================Find Highest salary in the organisation..=======================");
		
		//Find Highest salary in the organisation.
		System.out.println(empList.stream().sorted(Comparator.comparing(EmployeeStreamOperation::getSalary).reversed()).findFirst().get());
	
		System.err.println("=====================Find Second Highest salary in the organisation.=======================");
		
	    //Find Second Highest salary in the organisation.
		System.out.println(empList.stream().sorted(Comparator.comparing(EmployeeStreamOperation::getSalary).reversed()).skip(1).findFirst().get());
	
		
		System.err.println("=====================find hr dept epmployee count=======================");
		long s= empList.stream().collect(Collectors.groupingBy(EmployeeStreamOperation::getDeptName,Collectors.counting())).entrySet().stream().filter(m->m.getKey().equals("HR")).map(Map.Entry::getValue).mapToLong(Long::longValue).findFirst().getAsLong();                    
		System.out.println(s);
		
		System.err.println("=====================test=======================");
		System.out.println(empList.stream().sorted(Comparator.comparing(EmployeeStreamOperation::getSalary).reversed()).skip(1).findFirst().map(EmployeeStreamOperation::getName).get());

		Map<String, Double> maplist = empList.stream().collect(Collectors.groupingBy(EmployeeStreamOperation::getDeptName,Collectors.averagingDouble(EmployeeStreamOperation::getSalary))).entrySet()
		.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	
		 Map.Entry<String, Double> m2= empList.stream().collect(Collectors.groupingBy(EmployeeStreamOperation::getDeptName,Collectors.averagingDouble(EmployeeStreamOperation::getSalary))).entrySet()
				.stream().max(Map.Entry.comparingByValue()).get();
		 
		Double d1=  empList.stream().collect(Collectors.groupingBy(EmployeeStreamOperation::getDeptName,Collectors.averagingDouble(EmployeeStreamOperation::getSalary))).entrySet()
			.stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getValue).get();
		 
		System.out.println("avg demo :: "+m2);
		
		System.out.println("==distinct=="+empList.stream().map(e->e.getName()).distinct().count());
		
		
		
	}
}


class EmployeeStreamOperation {
	private int id;
    private String name;
    private int age;
    private long salary;
    private String gender;
    private String deptName;
    private String city;
    private int yearOfJoining;

    public EmployeeStreamOperation(int id, String name, int age, long salary, String gender, 
                    String deptName, String city, int yearOfJoining) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.gender = gender;
        this.deptName = deptName;
        this.city = city;
        this.yearOfJoining = yearOfJoining;
    }

    @Override
    public String toString() {
        return "EmployeeObjectJava8Scenario{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", gender='" + gender + '\'' +
                ", deptName='" + deptName + '\'' +
                ", city='" + city + '\'' +
                ", yearOfJoining='" + yearOfJoining + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getYearOfJoining() {
        return yearOfJoining;
    }

    public void setYearOfJoining(int yearOfJoining) {
        this.yearOfJoining = yearOfJoining;
    }
}