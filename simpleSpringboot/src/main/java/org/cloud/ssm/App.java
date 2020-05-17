package org.cloud.ssm;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.cloud.ssm.mapper")
public class App {
    private final static Logger logger = LoggerFactory.getLogger("o.c.s.Main");
    
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(App.class);
        application.run(args);
        logger.info("============= SpringBoot Start Success =============");
    }
}
