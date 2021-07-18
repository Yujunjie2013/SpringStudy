package com.example.jvm.chapter8;

/**
 * 测试对空间常用参数
 * -XX:+PrintFlagsInital:查看所有的参数的默认初始值
 * -XX:+PrintFlagsFinal 查看所有的参数的最终值（可能存在修改，不再是初始值）
 * -Xms: 初始堆空间大小
 * -Xmx: 最大堆空间
 * -Xmn: 设置新生代的大小
 * -XX:NewRatio 配置新生代与老年代在堆内存中的占比
 * -XX:SurvivorRatio: 设置新生代Eden 和s0/s1的空间比例
 * -XX:MaxTenuringThreshold:设置新生代垃圾的最大年龄，超过后会放到老年区
 * -XX:+PrintGCDetails:输出详细的GC处理日志
 * 打印GC简要信息 -XX:+PrintGC
 *               -verbose:gc
 * -XX:HandlePromotionFailure: 是否设置空间分配担保
 *
 * 查看具体某个参数的指令:jps :查看当前运行的所有Java进程
 * jinfo -flag 指令名字 进程id
 *  例如jinfo -flag SurvivorRatio  进程id
 *      jinfo -flag UseTLAB 进程id
 */
public class HeapArgsTest {
}
