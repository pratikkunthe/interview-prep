interface One {
    default void method() {
        System.out.println("method from One");
    }
}

interface Two {
    default void method() {
        System.out.println("method from Two");
    }
}

class Three implements Two,One {
    
    public void method() {
        One.super.method();
    }
        
    public static void main(String[] args) {
        new Three().method();
    }
}