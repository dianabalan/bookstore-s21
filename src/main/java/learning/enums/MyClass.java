package learning.enums;

public enum MyClass {
    INSTANCE;

    MyClass(){

    }

    //DESIGN PATTERN
    //SINGLETON - creational
    //BUILDER - creational

    public void add(){
        System.out.println("add");
    }
}
