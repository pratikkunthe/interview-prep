package DesignPattern.abstractfactory;

public class ServerFactory implements ComputerAbstractFactory{

	private String ram;
	private String rom;
	private String cpu;
	
	public ServerFactory(String ram, String rom, String cpu) {
		this.ram=ram;
		this.rom=rom;
		this.cpu=cpu;
	}
	
	@Override
	public Computer createComputer() {
		return new Server(ram, rom, cpu);
	}
}