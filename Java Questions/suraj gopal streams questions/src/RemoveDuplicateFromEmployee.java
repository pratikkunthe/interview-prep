import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RemoveDuplicateFromEmployee {

	public static void main(String[] args) {

		List<Employee1> employee = new ArrayList<>();

		Employee1 e1 = new Employee1();
		e1.setId("1");
		e1.setName("2");

		Employee1 e2 = new Employee1();
		e2.setId("1");
		e2.setName("4");

		Employee1 e3 = new Employee1();
		e3.setId("1");
		e3.setName("2");

		employee.add(e1);
		employee.add(e2);
		employee.add(e3);

		
		Map<String, Employee1> map = new HashMap<String, Employee1>();
		for (Employee1 e : employee) {
			String key = e.getId()+e.getName();
			if(!map.containsKey(key)) {
				map.put(key, e);
			}
		}
		Collection<Employee1> uniqueEmp = map.values();
		System.out.println(uniqueEmp);
	}
}

class Employee1 {

	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + "]";
	}

}
