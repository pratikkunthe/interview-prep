package Singleton;



class Super implements Cloneable{
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}


public class Singleton extends SecurityException{
	
	public static Singleton  instance = new Singleton();
	
	private Singleton() {
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	public static void main(String[] args) throws CloneNotSupportedException {
		Singleton singleton = Singleton.instance;
		System.out.println(singleton.hashCode());
		Singleton singleton1 = (Singleton) singleton.clone();
		System.out.println(singleton1.hashCode());
	}
	
}