package DesignPattern.builder;

public class BuilderPattern {

	public static void main(String[] args) {
		
		System.out.println("builder pattern : "+new BuilderDTO().setAddr("panvel").setAge(25).setName("suraj").setSalary(10000).build());
	}
}

class BuilderDTO {

	private String name;
	private String addr;
	private double salary;
	private int age;

	public BuilderDTO() {
	}
	public BuilderDTO(BuilderDTO builderDTO) {
		this.name = builderDTO.name;
		this.addr = builderDTO.addr;
		this.salary = builderDTO.salary;
		this.age = builderDTO.age;
	}
	
	public BuilderDTO setName(String name) {
		this.name = name;
		return this;
	}

	public BuilderDTO setAddr(String addr) {
		this.addr = addr;
		return this;
	}

	public BuilderDTO setSalary(double salary) {
		this.salary = salary;
		return this;
	}

	public BuilderDTO setAge(int age) {
		this.age = age;
		return this;
	}
	
	public BuilderDTO build() {
		return new BuilderDTO(this);
	}

	@Override
	public String toString() {
		return "BuilderDTO [name=" + name + ", addr=" + addr + ", salary=" + salary + ", age=" + age + "]";
	}

}