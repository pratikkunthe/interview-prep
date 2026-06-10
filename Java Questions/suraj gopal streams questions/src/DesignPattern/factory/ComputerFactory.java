package DesignPattern.factory;

//The factory design pattern is used when we have a superclass with multiple sub-classes and based on input,
//we need to return one of the sub-class.

public class ComputerFactory {

	public static Computer getComputer(String type, String ram, String hdd, String cpu) {
		if ("PC".equalsIgnoreCase(type))
			return new PC(ram, hdd, cpu);
		else if ("Server".equalsIgnoreCase(type))
			return new Server(ram, hdd, cpu);

		return null;

	}
}
