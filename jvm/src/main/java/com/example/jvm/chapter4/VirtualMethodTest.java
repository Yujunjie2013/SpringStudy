package com.example.jvm.chapter4;
/**
 *演示非虚方法、接口方法、静态方法、唯一指定方法
 * invokestatic 调用静态方法，唯一确定的方法
 * invokepecial 调用非虚方法，唯一确定的方法
 * invokevirtual 调用所有虚方法
 * invokeinterface 调用接口方法
 */
public class VirtualMethodTest {

    public static void main(String[] args) {
        Son son = new Son();
        son.showMethod();
    }

}

class Father {
    public void normalMethod() {
        System.out.println("父类普通方法");
    }

    public static void testStatic() {
        System.out.println("Father testStatic");
    }

    private void privateMethod() {
        System.out.println("父类私有方法");
    }

    public final void finalTest() {
        System.out.println("父类final方法");
    }
}

class Son extends Father {
    //因为静态方法不能被重写，这里虽然这样写，但是调用的时候需要指定调用谁的
    public static void testStatic() {
        System.out.println("Son testStatic");
    }

    @Override
    public void normalMethod() {
        System.out.println("子类重写了 normalMethod 方法");
    }

    private void privateMethod() {
        System.out.println("子类私有方法");
    }


    public void showMethod() {
        //invokestatic
        testStatic();
        //invokestatic
        super.testStatic();//这里
        //invokevirtual
        normalMethod();
        //invokepecial 因为显示的指定了调用父类的方法，所以不是invokevirtual
        super.normalMethod();
        //invokevirtual 虽然显示的是invokevirtual，但是该方法被final修饰，所以还是非虚方法
        finalTest();
        //invokepecial
        privateMethod();
        MethodInterface methodInterface=null;
        //invokeinterface
        methodInterface.eat();
    }
}

interface MethodInterface{
    void eat();
}
