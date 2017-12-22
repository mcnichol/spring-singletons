package com.mcnichol.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyServiceOne {
    Logger log = LoggerFactory.getLogger(MyServiceOne.class);

    private final ComponentIsSingleton componentIsSingleton;

    @Autowired
    public MyServiceOne(ComponentIsSingleton componentIsSingleton) {
        this.componentIsSingleton = componentIsSingleton;
    }

    void doWork(String expectedValue) throws Exception {
        log.info(String.format("Setting ComponentIsSingleton.stringValue to %s", expectedValue));
        componentIsSingleton.setStringValue(expectedValue);

        //This helps us demonstrate behavior of a long running process
        Thread.sleep(2000);

        String actualValueDuringRuntime = componentIsSingleton.getStringValue();
        if (!actualValueDuringRuntime.equals(expectedValue)) {
            throw new Exception(String.format("\nShut it down\nExpected Value:\t%s\nActual Value:\t%s", expectedValue, actualValueDuringRuntime));
        }
    }
}
