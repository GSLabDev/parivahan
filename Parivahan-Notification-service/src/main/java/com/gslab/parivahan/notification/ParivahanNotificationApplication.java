/*
 * Copyright (c) 2003-2019, Great Software Laboratory Pvt. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

