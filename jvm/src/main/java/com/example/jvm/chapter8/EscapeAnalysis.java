package com.example.jvm.chapter8;

/**
 * 逃逸分析
 *
 * 如何快速判断是否发生了逃逸，就看new 出来的对象是否有可能在方法外被调用
 */
public class EscapeAnalysis {
    public EscapeAnalysis obj;

    /**
     * 方法返回EscapeAnalysis对象发生了逃逸
     *
     * @return
     */
    public EscapeAnalysis getInstance() {
        return obj == null ? new EscapeAnalysis() : obj;
    }

    /**
     * 为成员属性赋值，发生了逃逸
     */
    public void setObj() {
        this.obj = new EscapeAnalysis();
    }

    /**
     * 对象的作用域仅在当前方法，没有发生逃逸
     */
    public void useEscapeAnalysis() {
        EscapeAnalysis escapeAnalysis = new EscapeAnalysis();
    }

    /**
     * 应用成员变量的值，发生了逃逸
     */
    public void useEscapeAnalysis1() {
        EscapeAnalysis escapeAnalysis = getInstance();
//        getInstance().setxxx()同样发生了逃逸
    }

}
