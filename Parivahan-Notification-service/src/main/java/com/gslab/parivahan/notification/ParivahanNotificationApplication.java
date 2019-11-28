package com.gslab.parivahan.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import it.ozimov.springboot.mail.configuration.EnableEmailTools;

@SpringBootApplication
@EnableEmailTools
@ComponentScan(basePackages = { "com.gslab.parivahan.notification", "com.gslab.parivahan.notification.controller","com.gslab.parivahan.notification.service","com.gslab.parivahan.notification.util"})
public class ParivahanNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParivahanNotificationApplication.class, args);
	}

}

