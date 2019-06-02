package cn.guxiangfly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author guxiang
 */
@SpringBootApplication
@EnableEurekaClient
public class SearchInfoApplication {
    public static void main(String[] args) {
        SpringApplication.run( SearchInfoApplication.class, args );
    }
}
