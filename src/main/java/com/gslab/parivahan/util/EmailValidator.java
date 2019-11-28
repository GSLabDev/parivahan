package com.gslab.parivahan.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

public class EmailValidator {

	private static Pattern pattern;
	private static Matcher matcher;
	private static final String EMAIL_PATTERN = "^[A-Z0-9._%+-]+@gslab.com";

	public static boolean validateEmail(String email) {
		pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}	
}
