package com.example.jvm.chapter10;

public class StringIntern {
    public static void main(String[] args) {
//        str1Intern();//false
//        str2Intern();//true
//        str3Intern();//true
//        str4Intern();
//        str5Intern();
//        str6Intern();
        System.out.println(System.currentTimeMillis());
    }

    public static void str1Intern() {
        String s = new String("1");
        s.intern();
        String s2 = "1";
        System.out.println(s == s2);//false
    }

    public static void str2Intern() {
        String s = new String("1");//此时常量池中已经有了1
        s = s.intern();
        String s2 = "1";
        System.out.println(s == s2);//true
    }

    public static void str3Intern() {
        String s = new String("1") + new String("1");//此时s3变量记录的地址为:new String("11)
        //执行完上一行代码后，字符串常量池中，是否存在"11"呢？答案，不存在，此时应该存在"1"
        s.intern();//在字符串常量池中生成"11"，且此时常量池中的地址就是s的地址
        String s2 = "11";//此时s4记录的地址就是使用上一行代码执行时，在常量池中生成的
        System.out.println(s == s2);//true
    }

    public static void str4Intern() {
        String s2 = "11";//此时在常量池中生成11
        String s = new String("1") + new String("1");//此时s3变量记录的地址为:new String("11)
        //执行完上一行代码后，字符串常量池中，是否存在"11"呢？答案，存在
        s.intern();//因为常量池中已经存在，所以地址引用不一样，故结果应该是false
//        s = s.intern();//如果是这种就会将字符串常量池中的地址返回重新赋给s，那么结果就是true
        System.out.println(s == s2);//false
    }

    public static void str5Intern() {
        String a, b, c;
        a = "a";
        b = "b";
        c = "a" + "b";//此时常量池中会声明ab
        System.out.println(a + b == c);
        System.out.println(("a" + "b") == c);
    }

    public static void str6Intern() {
        System.out.println("----------");
        String a;
        a = "a";
        final String b = "b";
        String c = "a" + "b";//此时常量池中会声明ab
        System.out.println("a" + b == c);
        System.out.println("a" + "b" == c);
    }











}
