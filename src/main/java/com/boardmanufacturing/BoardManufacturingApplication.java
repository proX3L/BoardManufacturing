package com.boardmanufacturing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.boardmanufacturing")
public class BoardManufacturingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardManufacturingApplication.class, args);
    }

}
