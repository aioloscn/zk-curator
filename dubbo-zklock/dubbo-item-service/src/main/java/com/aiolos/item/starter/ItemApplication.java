package com.aiolos.item.starter;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author Aiolos
 * @date 2019-10-28 17:43
 */
public class ItemApplication {

    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] {"classpath:spring/spring-context.xml"}
        );
        context.start();
        System.in.read();
    }
}
