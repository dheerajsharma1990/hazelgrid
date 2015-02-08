package com.hazelgrid.computing;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.io.FileNotFoundException;

public class Node {
    public static void main(String[] args) throws FileNotFoundException {
        Config config = new ClasspathXmlConfig("hazelcast-cluster-config.xml");
        config.setInstanceName("hazelgrid");
        HazelcastInstance instance = Hazelcast.getOrCreateHazelcastInstance(config);
    }
}