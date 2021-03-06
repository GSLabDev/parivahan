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
package com.gslab.parivahan.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "parivahan.auth.ldap")
public class ParivahanLdapAuthSettings {

	private static boolean enabled;
	private static String urls;
	private static String basedn;
	private static String username;
	private static String password;
	private static String userdnpattern;
	public static boolean isEnabled() {
		return enabled;
	}
	public static String getUrls() {
		return urls;
	}
	public static String getBasedn() {
		return basedn;
	}
	public static String getUsername() {
		return username;
	}
	public static String getPassword() {
		return password;
	}
	public static String getUserdnpattern() {
		return userdnpattern;
	}
	public static void setEnabled(boolean enabled) {
		ParivahanLdapAuthSettings.enabled = enabled;
	}
	public static void setUrls(String urls) {
		ParivahanLdapAuthSettings.urls = urls;
	}
	public static void setBasedn(String basedn) {
		ParivahanLdapAuthSettings.basedn = basedn;
	}
	public static void setUsername(String username) {
		ParivahanLdapAuthSettings.username = username;
	}
	public static void setPassword(String password) {
		ParivahanLdapAuthSettings.password = password;
	}
	public static void setUserdnpattern(String userdnpattern) {
		ParivahanLdapAuthSettings.userdnpattern = userdnpattern;
	}
	
	
	
	
}
