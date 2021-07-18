package com.example.jvm.chapter4;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class DynamicLinkingTest {
    int num;

    public void methodA() {
        System.out.println("methodA");
    }

    public void methodB() {
        System.out.println("methodB");
        methodA();
        num++;
    }

    /**
     * 十六进制字符串装十进制
     *
     * @return 十进制数值
     */
    public static int hexStringToAlgorism(String hex) {
        hex = hex.toUpperCase();
        int max = hex.length();
        int result = 0;
        for (int i = max; i > 0; i--) {
            char c = hex.charAt(i - 1);
            int algorism = 0;
            if (c >= '0' && c <= '9') {
                algorism = c - '0';
            } else {
                algorism = c - 55;
            }
            result += Math.pow(16, max - i) * algorism;
        }
        return result;
    }

    public static void main(String[] args) {

        System.out.println(Long.parseLong("E29DAA2B",16));
//        System.out.println(Long.parseLong("D7E9630E",16));
        System.out.println(Long.parseLong("0E63E9D7",16));
//        System.out.println(Integer.parseInt("0E63E9D7",16));
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Long.MAX_VALUE);

    }

    /**
     * @param: [content]
     * @return: int
     * @description: 十六进制转十进制
     */
    public static int covert(String content){
        int number=0;
        String [] HighLetter = {"A","B","C","D","E","F"};
        Map<String,Integer> map = new HashMap<>();
        for(int i = 0;i <= 9;i++){
            map.put(i+"",i);
        }
        for(int j= 10;j<HighLetter.length+10;j++){
            map.put(HighLetter[j-10],j);
        }
        String[]str = new String[content.length()];
        for(int i = 0; i < str.length; i++){
            str[i] = content.substring(i,i+1);
        }
        for(int i = 0; i < str.length; i++){
            number += map.get(str[i])*Math.pow(16,str.length-1-i);
        }
        return number;
    }

}
