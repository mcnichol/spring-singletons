package com.mcnichol.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyServiceTwo {
    Logger log = LoggerFactory.getLogger(MyServiceTwo.class);

    private final ComponentIsSingleton componentIsSingleton;

    @Autowired
    public MyServiceTwo(ComponentIsSingleton componentIsSingleton) {
        this.componentIsSingleton = componentIsSingleton;
    }

    void doWork(String expectedValue) throws Exception {
        log.info(String.format("Setting ComponentIsSingleton.stringValue to %s", expectedValue));

        componentIsSingleton.setStringValue(expectedValue);

        String actualValueDuringRuntime = componentIsSingleton.getStringValue();

        if(!actualValueDuringRuntime.equals(expectedValue)){
            //This CAN happen but has a very narrow window of opportunity for failure
        }
    }
}
