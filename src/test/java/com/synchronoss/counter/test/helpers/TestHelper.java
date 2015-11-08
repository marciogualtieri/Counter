package com.synchronoss.counter.test.helpers;

import org.apache.commons.io.FileUtils;
import org.springframework.util.SerializationUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestHelper {

    public static AtomicInteger loadSerializedCounterFromFile(String serializedCounterFileNameAndPath)
            throws IOException {
        File serializedCounterFile = new File(serializedCounterFileNameAndPath);
        assertThat(serializedCounterFile.exists(), equalTo(true));
        byte[] serializedObject = FileUtils.readFileToByteArray(serializedCounterFile);
        return (AtomicInteger) SerializationUtils.deserialize(serializedObject);
    }

    public static void overrideSerializedCounterFileWithValue(int value, String serializedCounterFileNameAndPath)
            throws IOException {
        deleteSerializedCounterFile(serializedCounterFileNameAndPath);
        byte[] serializedObject = SerializationUtils.serialize(new AtomicInteger(value));
        FileUtils.writeByteArrayToFile(new File(serializedCounterFileNameAndPath), serializedObject);
    }

    public static void deleteSerializedCounterFile(String serializedCounterFileNameAndPath) {
        File serializedCounterFile = new File(serializedCounterFileNameAndPath);
        FileUtils.deleteQuietly(serializedCounterFile);
    }

    public static Properties getProperties(String propertiesFileNameAndPath) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(propertiesFileNameAndPath));
        return properties;
    }
}
