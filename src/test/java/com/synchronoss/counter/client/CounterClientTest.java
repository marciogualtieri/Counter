package com.synchronoss.counter.client;

import com.synchronoss.counter.server.CounterServer;
import com.synchronoss.counter.test.constants.TestConstants;
import org.junit.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CounterClientTest {

    private static CounterServer testCounterServer;
    private CounterClient testCounterClient;

    @BeforeClass
    public static void beforeClass() throws Exception {
        testCounterServer = new CounterServer();
        testCounterServer.configure(TestConstants.PORT);
        testCounterServer.startup();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        testCounterServer.shutdown();
    }

    @Before
    public void before() throws Exception {
        testCounterClient = new CounterClient();
        testCounterClient.configure(TestConstants.PORT);
        testCounterClient.open();
        testCounterClient.reset();
    }

    @After
    public void after() throws Exception {
        testCounterClient.close();
    }

    @Test
    public void whenGet_thenOk() throws Exception {
        String response = testCounterClient.evaluate();
        assertThat(response, equalTo("0"));
    }

    @Test
    public void whenIncrement_thenOk() throws Exception {
        Integer someIncrement = 10;
        incrementCounterBySome(someIncrement);
        String response = testCounterClient.evaluate();
        assertThat(response, equalTo(someIncrement.toString()));
    }

    @Test
    public void whenDecrement_thenOk() throws Exception {
        Integer someDecrement = -10;
        decrementCounterBySome(someDecrement);
        String response = testCounterClient.evaluate();
        assertThat(response, equalTo(someDecrement.toString()));
    }

    @Test
    public void whenReset_thenOk() throws Exception {
        Integer someIncrement = 10;
        incrementCounterBySome(someIncrement);
        String response = testCounterClient.evaluate();
        assertThat(response, equalTo(someIncrement.toString()));
        response = testCounterClient.reset();
        assertThat(response, equalTo("0"));
    }

    private void incrementCounterBySome(int some) {
        for (int i = 0; i < some; i++) {
            testCounterClient.increment();
        }
    }

    private void decrementCounterBySome(int some) {
        for (int i = some; i < 0; i++) {
            testCounterClient.decrement();
        }
    }

} 
