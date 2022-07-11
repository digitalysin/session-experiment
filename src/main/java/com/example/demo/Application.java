package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication(
        scanBasePackages = {
                "com.example.demo.configurations.*",
                "com.example.demo.sessions.*",
                "com.example.demo.controllers.*"
        }
)
public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);

//        final var pool = ctx.getBean(SessionPool.class);
//        pool.lock("some-key");
//
//        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(e -> {
//            LOGGER.info("{}", e);
//        });
//
//        Thread.sleep(100000);
    }

    private static <T> T logSleep(final String operator, final T t) {
        sleep(200);
        LOGGER.info("operator: {} with value: {} executed at: {}", operator, t, Thread.currentThread().getName());
        return t;
    }

    private static void run() {
        final var es = Executors.newFixedThreadPool(24);
        final var scheduler = Schedulers.fromExecutor(es);
        final var start = System.nanoTime();

        Flux
                .range(0, 100)
                .parallel()
                .runOn(scheduler)
                .map(e -> logSleep("map1", e))
                .map(e -> log("map2", e))
                .sequential()
                .doFinally(st -> {
                    LOGGER.info("took: {} millis", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start));
                })
                .subscribe();

    }

    private static <T> T log(final String operator, final T t) {
        LOGGER.info("operator: {} with value: {} executed at: {}", operator, t, Thread.currentThread().getName());
        return t;
    }

    private static void sleep(final long duration) {
        try {
            Thread.sleep(duration);
        } catch (final Exception e) {

        }
    }

}
