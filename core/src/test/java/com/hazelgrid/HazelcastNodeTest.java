package com.hazelgrid;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.Partition;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class HazelcastNodeTest {

    private HazelcastInstance instance;
    private IMap<Integer, String> myMap;

    private Entry<Integer, String> entry = new SimpleEntry(1, "value");

    @Test
    public void shouldPutAndGetValue() {
        myMap.put(entry.getKey(), entry.getValue());
        assertThat(myMap.get(entry.getKey()), is(entry.getValue()));
    }

    @Test
    public void shouldGetPartition() {
        Partition partition = instance.getPartitionService().getPartition(entry.getKey());
        assertThat(partition.getPartitionId(), notNullValue());
    }

    @BeforeClass
    public void startCluster() {
        Config config = new Config();
        config.setInstanceName("testInstance");
        instance = Hazelcast.newHazelcastInstance(config);
        myMap = instance.getMap("myMap");
    }
}
