package DesignPattern.abstractfactory;

public class PCFactory implements ComputerAbstractFactory{
	
	private String ram;
	private String rom;
	private String cpu;
	
	public PCFactory(String ram, String rom, String cpu) {
		this.ram=ram;
		this.rom=rom;
		this.cpu=cpu;
	}
	
	@Override
	public Computer createComputer() {
		return new PC(ram, rom, cpu);
	}
}