package com.example.jvm.chapter4;

/**
 * 操作数栈演示
 */
public class OperandStackTest {
    public static void main(String[] args) {
        final OperandStackTest operandStackTest = new OperandStackTest();
        operandStackTest.add();
    }

    //这里操作数栈的最大深度是2，因为只有在进行求和运算时需要同时将2个数字都加载到操作数栈中，所以最大深度是2
    //但是这个方法的局部变量表是4
    public void testAddOperation() {
        byte i = 15;
        int j = 8;
        int k = i + j;
    }

    //这里操作数栈的最大深度还是2，因为只有在进行求和运算时需要同时将2个数字都加载到操作数栈中，所以最大深度是2
    //但是这个方法的局部变量表是6
    public void testAddOperation2() {
        byte i = 15;
        int j = 8;
        int a = 1;
        int b = 2;
        int k = i + j;
    }

    public int getSum() {
        int a = 10;
        int b = 2;
        int k = a + b;
        return k;
    }

    public void testGetSum() {
        int b = 111;
        //如果方法有返回值的话，则返回值会被压入到当前操作数栈中
        int sum = getSum();
        int a = 2;
    }

    /**
     * 常见的++i、和i++的区别问题
     */
    public void add() {
        int a = 10;
        a++;
        int b = 20;
        ++b;

        int i1 = 10;
        int i2 = i1++;
        int i3 = 10;
        int i4 = ++i3;

        int i5 = 20;
        int i6 = i5++ + ++i5;

        System.out.println(i6);


    }

}
