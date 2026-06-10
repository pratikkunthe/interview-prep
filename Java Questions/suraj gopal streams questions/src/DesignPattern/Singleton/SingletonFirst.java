package Singleton;

class SingletonFirst {

	private static SingletonFirst instance = new SingletonFirst();

	private SingletonFirst() {
	}

	public static SingletonFirst getInstance() {
		synchronized (SingletonFirst.class) {
			if (instance != null) {
				return instance;
			} else {
				instance = new SingletonFirst();
				return instance;
			}
		}
	}

}
