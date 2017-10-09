package org.lamb.lambframework.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class Application {

        private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Application.class);

        public static void main(String[] args) {
                logger.info("启动 lambframework-core 成功");
                SpringApplication.run(Application.class, args);
        }

}

