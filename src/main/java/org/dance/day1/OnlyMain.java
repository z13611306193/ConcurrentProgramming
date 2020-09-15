package org.dance.day1;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 只有Main方法
 * @author ZYGisComputer
 */
public class OnlyMain {

    public static void main(String[] args) {

        // 虚拟机线程管理接口
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        // 返回线程数组
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);

        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("["+threadInfo.getThreadId()+"]:"+threadInfo.getThreadName()+":"+threadInfo.getThreadState());
        }

    }

}
