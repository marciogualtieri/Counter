package com.synchronoss.counter.server;

import com.synchronoss.counter.client.CounterClient;
import com.synchronoss.counter.test.constants.TestConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CounterServerTest {

    private CounterServer testCounterServer;

    @Before
    public void before() throws Exception {
        testCounterServer = new CounterServer();
    }

    @After
    public void after() throws Exception {
        resetCounter();
        testCounterServer.shutdown();
    }

    @Test
    public void whenStartWithPort_thenOk() throws Exception {
        testCounterServer.configure(TestConstants.PORT);
        testCounterServer.startup();
    }

    @Test
    public void whenIncrementConcurrently_thenOk() throws Exception {
        testCounterServer.configure(TestConstants.PORT);
        testCounterServer.startup();
        Integer someIncrement = 100;
        resetCounter();
        incrementCounterBySomeConcurrently(someIncrement);
        String response = evaluateCounter();
        assertThat(response, equalTo(someIncrement.toString()));
    }

    private void incrementCounterBySomeConcurrently(int some) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(TestConstants.CLIENT_THREADS_POOL_SIZE);
        for (int i = 0; i < some; i++) {
            executorService.execute(this::incrementCounterByOne);
        }
        executorService.shutdown();
        executorService.awaitTermination(TestConstants.CLIENT_THREADS_WAIT_TIME_MILLISECS, TimeUnit.MILLISECONDS);
    }

    private void resetCounter() {
        CounterClient testCounterClient = new CounterClient();
        testCounterClient.configure(TestConstants.PORT);
        testCounterClient.open();
        testCounterClient.reset();
        testCounterClient.close();
    }

    private void incrementCounterByOne() {
        CounterClient testCounterClient = new CounterClient();
        testCounterClient.configure(TestConstants.PORT);
        testCounterClient.open();
        testCounterClient.increment();
        testCounterClient.close();
    }

    private String evaluateCounter() {
        CounterClient testCounterClient = new CounterClient();
        testCounterClient.configure(TestConstants.PORT);
        testCounterClient.open();
        String response = testCounterClient.evaluate();
        testCounterClient.close();
        return response;
    }

} 
