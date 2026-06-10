package DesignPattern.abstractfactory;

public class PC extends Computer {
	
	private String ram;
	private String rom;
	private String cpu;
	
	public PC(String ram, String rom, String cpu) {
		this.ram=ram;
		this.rom=rom;
		this.cpu=cpu;
	}

	@Override
	String getRam() {
		return this.ram;
	}

	@Override
	String getRom() {
		return this.rom;
	}

	@Override
	String getCPU() {
		return this.cpu;
	}

	
}