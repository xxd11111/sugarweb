package com.sugarcoat.support.server.domain;

import com.sugarcoat.protocol.server.AppInfo;

import java.lang.management.*;

/**
 * 当前应用信息 todo 延期
 *
 * @author 许向东
 * @date 2023/9/22
 */
public class CurrentAppInfo implements AppInfo {

    @Override
    public Object getRuntimeInfo() {
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        CompilationMXBean compilationMXBean = ManagementFactory.getCompilationMXBean();
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo threadInfo = threadMXBean.getThreadInfo(1);

        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        long startTime = runtimeMXBean.getStartTime();
        long uptime = runtimeMXBean.getUptime();
        return null;
    }

    @Override
    public MemoryInfo getJvmMemoryInfo() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        long heapMemoryUsageUsed = heapMemoryUsage.getUsed();
        long heapMemoryUsageMax = heapMemoryUsage.getMax();

        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        long nonHeapMemoryUsageUsed = nonHeapMemoryUsage.getUsed();
        long nonHeapMemoryUsageMax = nonHeapMemoryUsage.getMax();

        MemoryInfo memoryInfo = new MemoryInfo();
        memoryInfo.setHeapMax(heapMemoryUsageMax);
        memoryInfo.setHeapUsed(heapMemoryUsageUsed);
        memoryInfo.setNonHeapMax(nonHeapMemoryUsageMax);
        memoryInfo.setNonHeapUsed(nonHeapMemoryUsageUsed);
        return memoryInfo;
    }

    @Override
    public Object getJvmConfigInfo() {
        return null;
    }

}
