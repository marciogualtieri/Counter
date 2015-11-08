package com.synchronoss.counter.services;

import com.synchronoss.counter.enums.Operation;
import com.synchronoss.counter.exceptions.IllegalOperationException;
import com.synchronoss.counter.messages.CounterServiceMessages;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.SerializationUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class CounterService implements InitializingBean, DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(CounterService.class);
    private final String serializedCounterFileNameAndPath;
    private AtomicInteger counter = new AtomicInteger(0);

    public CounterService(String serializedCounterFileNameAndPath) {
        this.serializedCounterFileNameAndPath = serializedCounterFileNameAndPath;
    }

    public String serve(String input) throws IllegalOperationException {
        Operation operation = parseOperationFromInputString(input);
        counter = operation.apply(counter);
        logger.debug(CounterServiceMessages.COUNTER_OPERATION_MESSAGE_FORMAT, counter, operation);
        return counter.toString();
    }

    public void afterPropertiesSet() throws Exception {
        loadCounterFileAndDeserialize();
        logger.info(CounterServiceMessages.COUNTER_LOADED_MESSAGE_FORMAT, counter,
                serializedCounterFileNameAndPath);
    }

    public void destroy() throws Exception {
        serializeCounterAndSaveToFile();
        logger.info(CounterServiceMessages.COUNTER_SAVED_MESSAGE_FORMAT, counter,
                serializedCounterFileNameAndPath);
    }

    private Operation parseOperationFromInputString(String input) {
        try {
            return Operation.valueOf(input);
        } catch (IllegalArgumentException e) {
            throw new IllegalOperationException(input);
        }
    }

    private void serializeCounterAndSaveToFile() throws IOException {
        byte[] serializedObject = SerializationUtils.serialize(counter);
        FileUtils.writeByteArrayToFile(new File(serializedCounterFileNameAndPath), serializedObject);
    }

    private void loadCounterFileAndDeserialize() throws IOException {
        File file = new File(serializedCounterFileNameAndPath);
        if (file.exists()) {
            byte[] serializedObject = FileUtils.readFileToByteArray(file);
            counter = (AtomicInteger) SerializationUtils.deserialize(serializedObject);
        }
    }
}