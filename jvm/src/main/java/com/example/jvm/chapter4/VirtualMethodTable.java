package com.example.jvm.chapter4;

interface Friendly{

    void sayHello();
    void sayGoodBye();
}

class Dog{
    public void sayHello(){

    }


    public String toString() {
        return "Dog";
    }
}
class Cat implements Friendly{

    public void eat(){

    }
    @Override
    public void sayHello() {

    }

    @Override
    public void sayGoodBye() {

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

class Cocker extends Dog implements Friendly{
    @Override
    public void sayHello() {
        super.sayHello();
    }

    @Override
    public void sayGoodBye() {

    }
}

public class VirtualMethodTable {

}
