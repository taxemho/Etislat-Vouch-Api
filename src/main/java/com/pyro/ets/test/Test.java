package com.pyro.ets.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableScheduling
public class Test {

    public static void main(String[] args) {
        SpringApplication.run(Test.class, args);
    }

    @Component
    public static class ScheduledTask {

        @Scheduled(fixedDelay = 10001)
        public void scheduledTask() {
            System.out.println("hii");
        }
    }
}
