package com.example.jvm.chapter4;

/**
 * 演示栈中异常
 *
 * 如果指定栈的大小，可以通过-Xss来指定,默认1m
 * -Xss size
 * Sets the thread stack size (in bytes). Append the letter k or K to indicate KB, m or M to indicate MB, and g or G to indicate GB. The default value depends on the platform:
 *
 * Linux/x64 (64-bit): 1024 KB
 *
 * macOS (64-bit): 1024 KB
 *
 * Oracle Solaris/x64 (64-bit): 1024 KB
 *
 * Windows: The default value depends on virtual memory
 *
 * The following examples set the thread stack size to 1024 KB in different units:
 */
public class StackErrorTest {
    private static int count;
    public static void main(String[] args) {
        try {
            count++;
            System.out.println(count);
            main(args);
        }catch (Throwable throwable){
            System.out.println("捕获了错误");
            throwable.printStackTrace();
        }

        System.out.println("===================");
    }
}
