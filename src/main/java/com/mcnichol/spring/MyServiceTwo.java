package com.mcnichol.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyServiceTwo {

    private final ComponentIsSingleton componentIsSingleton;

    @Autowired
    public MyServiceTwo(ComponentIsSingleton componentIsSingleton) {
        this.componentIsSingleton = componentIsSingleton;
    }

    public void doWork(String expectedValue) throws Exception {
        componentIsSingleton.setStringValue(expectedValue);

        String actualValueDuringRuntime = componentIsSingleton.getStringValue();

        if(!actualValueDuringRuntime.equals(expectedValue)){
            //This CAN happen but has a very narrow window of opportunity for failure
        }
    }
}
