package java.test.jvm;

/**
 * 这个类在加载后会报错，因为java 包是由启动类加载器去完成的，这里模拟出来的会有问题
 */
public class TestS {
    static {
        System.out.println("我被加载了");
    }

    public static void main(String[] args) {
        System.out.println("....");
    }
}
