package DesignPattern.abstractfactory;

public class TestClient {
	public static void main(String[] args) {
		
		Computer c = ComputerFactory.getComputer(new PCFactory("12", "233", "45"));
		System.out.println(c.getCPU()+"----"+c.getRam()+"----"+c.getRom());
		Computer c1 = ComputerFactory.getComputer(new ServerFactory("1266", "23399", "45999"));
		System.out.println(c1.getCPU()+"----"+c1.getRam()+"----"+c1.getRom());
		
	}
}