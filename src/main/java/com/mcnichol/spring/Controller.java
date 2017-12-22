package com.mcnichol.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private static final Logger log = LoggerFactory.getLogger(Controller.class);
    private final MyServiceOne myServiceOne;
    private final MyServiceTwo myServiceTwo;
    private final ApplicationContext applicationContext;

    @Autowired
    public Controller(MyServiceOne myServiceOne, MyServiceTwo myServiceTwo, ApplicationContext applicationContext) {
        this.myServiceOne = myServiceOne;
        this.myServiceTwo = myServiceTwo;
        this.applicationContext = applicationContext;
    }

    @RequestMapping("/work")
    public void doWork(@RequestParam("do") String it) {
        log.info(String.format("/work endpoint called"));
        try {
            myServiceOne.doWork(it);
        } catch (Exception e) {
            log.error(e.getMessage());

            SpringApplication.exit(applicationContext, () -> -1);
        }
    }

    @RequestMapping("/morework")
    public void doMore(@RequestParam("do") String it) throws Exception {
        log.info(String.format("/morework endpoint called"));
        myServiceTwo.doWork(it);
    }
}
