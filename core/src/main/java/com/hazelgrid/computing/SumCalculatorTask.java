package com.hazelgrid.computing;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;

import java.io.Serializable;
import java.util.concurrent.Callable;

public class SumCalculatorTask implements Callable<Integer>, HazelcastInstanceAware, Serializable {

    private transient HazelcastInstance hazelcastInstance;

    private int start;
    private int end;

    public SumCalculatorTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("Calculating sum from " + start + " till " + end);
        Integer sum = 0;
        long startTime = System.currentTimeMillis();
        for (int i = start; i <= end; i++) {
            sum += i;
            Thread.sleep(10);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Total time taken to calculate sum from " + start + " till " + end + " is " + (endTime - startTime) + " millis");
        return sum;
    }

    @Override
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }
}
