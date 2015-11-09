package com.synchronoss.counter;

import com.synchronoss.counter.CounterApp;
import com.synchronoss.counter.exceptions.IllegalModeException;
import com.synchronoss.counter.exceptions.IllegalOperationException;
import com.synchronoss.counter.messages.ExceptionMessages;
import com.synchronoss.counter.test.constants.TestConstants;
import com.synchronoss.counter.test.messages.TestMessages;
import org.junit.Before;
import org.junit.Test;

import static com.googlecode.catchexception.CatchException.caughtException;
import static com.googlecode.catchexception.apis.CatchExceptionBdd.then;
import static com.googlecode.catchexception.apis.CatchExceptionBdd.when;

public class CounterAppTest {

    private CounterApp testCounterApp;

    @Before
    public void before() throws Exception {
        testCounterApp = new CounterApp();
    }

    @Test
    public void whenInvalidMode_thenException() {
        when(testCounterApp).cli(TestConstants.APPLICATION_ARGUMENTS_WITH_INVALID_MODE);
        then(caughtException())
                .isInstanceOf(IllegalModeException.class)
                .hasMessageContaining(TestMessages.INVALID_MODE_EXCEPTION_MESSAGE);
    }

    @Test
    public void whenInvalidOperation_thenException() {
        when(testCounterApp).cli(TestConstants.APPLICATION_ARGUMENTS_WITH_INVALID_OPERATION);
        then(caughtException())
                .isInstanceOf(IllegalOperationException.class)
                .hasMessageContaining(TestMessages.INVALID_OPERATION_EXCEPTION_MESSAGE);
    }

    @Test
    public void whenMissingMode_thenException() {
        when(testCounterApp).cli(TestConstants.APPLICATION_ARGUMENTS_WITH_MISSING_MODE);
        then(caughtException())
                .isInstanceOf(IllegalModeException.class)
                .hasMessageContaining(ExceptionMessages.MISSING_MODE_MESSAGE);
    }

    @Test
    public void whenMissingOperation_thenException() {
        when(testCounterApp).cli(TestConstants.APPLICATION_ARGUMENTS_WITH_MISSING_OPERATION);
        then(caughtException())
                .isInstanceOf(IllegalOperationException.class)
                .hasMessageContaining(ExceptionMessages.MISSING_OPERATION_MESSAGE);
    }
} 
