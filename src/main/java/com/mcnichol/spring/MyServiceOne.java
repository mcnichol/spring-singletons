package com.mcnichol.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyServiceOne {

    private final ComponentIsSingleton componentIsSingleton;

    @Autowired
    public MyServiceOne(ComponentIsSingleton componentIsSingleton) {
        this.componentIsSingleton = componentIsSingleton;
    }

    public void doWork(String expectedValue) throws Exception {
        componentIsSingleton.setStringValue(expectedValue);

        //This helps us demonstrate behavior of a long running process
        Thread.sleep(2000);

        String actualValueDuringRuntime = componentIsSingleton.getStringValue();
        if (!actualValueDuringRuntime.equals(expectedValue)) {
            throw new Exception(String.format("Shut it down\nExpected Value:\t%s\nActual Value:\t%s", expectedValue, actualValueDuringRuntime));
        }
    }
}
