package com.gslab.parivahan.util;

import java.util.Random;
import java.util.UUID;

public class RideCodeGenerator {

	public static Integer generateRandomNumber() {
		Random r = new Random();
		return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
	}

	public static String generateRandomId() {
		return UUID.randomUUID().toString().substring(0, 8);
	}

	public static void main(String[] args) {
		System.out.println(generateRandomId());
		System.out.println(generateRandomNumber());
	}
}
