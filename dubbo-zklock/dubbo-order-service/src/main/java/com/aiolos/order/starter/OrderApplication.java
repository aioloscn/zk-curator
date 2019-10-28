package com.aiolos.order.starter;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author Aiolos
 * @date 2019-10-28 20:08
 */
public class OrderApplication {

    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] {"classpath:spring/spring-context.xml"}
        );
        context.start();
        System.in.read();
    }
}
