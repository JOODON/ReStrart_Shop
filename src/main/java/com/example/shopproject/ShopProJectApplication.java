package com.example.shopproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController Controller+ResponseBody
@SpringBootApplication
public class ShopProJectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopProJectApplication.class, args);
    }

}
