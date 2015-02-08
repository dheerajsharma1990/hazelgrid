package com.hazelgrid.computing;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Client {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        InputStream inputStream = Client.class.getClassLoader().getResourceAsStream("hazelcast-client-config.xml");
        ClientConfig clientConfig = new XmlClientConfigBuilder(inputStream).build();
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        calculateSum(client);
    }

    private static void calculateSum(HazelcastInstance client) throws InterruptedException, ExecutionException {
        IExecutorService executorService = client.getExecutorService("default");
        Integer finalSum = 0;
        List<Future<Integer>> futures = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            futures.add(executorService.submit(new SumCalculatorTask(100 * i + 1, 100 * i + 100)));
        }

        for (Future<Integer> future : futures) {
            finalSum += future.get();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Final Sum: " + finalSum + " calculated in " + (endTime-startTime) + " millis.");
    }

}
