package com.synchronoss.counter.services;

import com.synchronoss.counter.constants.CounterConstants;
import com.synchronoss.counter.enums.Operation;
import com.synchronoss.counter.exceptions.IllegalOperationException;
import com.synchronoss.counter.test.constants.TestConstants;
import com.synchronoss.counter.test.helpers.TestHelper;
import com.synchronoss.counter.test.messages.TestMessages;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import static com.googlecode.catchexception.CatchException.caughtException;
import static com.googlecode.catchexception.apis.CatchExceptionBdd.then;
import static com.googlecode.catchexception.apis.CatchExceptionBdd.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CounterServiceTest {

    private CounterService testCounterService;
    private String serializedCounterFileNameAndPath;

    @Before
    public void before() throws Exception {
        Properties configuration =
                TestHelper.getProperties(TestConstants.CONFIGURATION_PROPERTIES_FILE_NAME_AND_PATH);
        serializedCounterFileNameAndPath =
                configuration.getProperty(CounterConstants.SERIALIZED_COUNTER_FILE_PROPERTY_NAME);
        testCounterService = new CounterService(serializedCounterFileNameAndPath);
    }

    @Test
    public void whenLoadExistentSerializedCounter_thenOk() throws Exception {
        Integer counterValue = 123;
        TestHelper.overrideSerializedCounterFileWithValue(counterValue, serializedCounterFileNameAndPath);
        testCounterService.afterPropertiesSet();
        String response = getCounter();
        assertThat(response, equalTo(counterValue.toString()));
    }

    @Test
    public void whenSaveSerializedCounter_thenOk() throws Exception {
        TestHelper.deleteSerializedCounterFile(serializedCounterFileNameAndPath);
        Integer counterValue = 456;
        incrementCounterBySome(counterValue);
        testCounterService.destroy();
        AtomicInteger counter = TestHelper.loadSerializedCounterFromFile(serializedCounterFileNameAndPath);
        assertThat(counter.intValue(), equalTo(counterValue.intValue()));
    }

    @Test
    public void whenInvalidOperation_thenException() {
        when(testCounterService).serve(TestConstants.INVALID_OPERATION_INPUT_STRING);
        then(caughtException())
                .isInstanceOf(IllegalOperationException.class)
                .hasMessageContaining(TestMessages.INVALID_OPERATION_EXCEPTION_MESSAGE);
    }

    private void incrementCounterBySome(int some) {
        for (int i = 0; i < some; i++) {
            testCounterService.serve(Operation.I.toString());
        }
    }

    private String getCounter() {
        return testCounterService.serve(Operation.E.toString());
    }
} 
