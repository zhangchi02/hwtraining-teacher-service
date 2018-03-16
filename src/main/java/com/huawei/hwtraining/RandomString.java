package com.huawei.hwtraining;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class RandomString {
	public static void main(String[] args) {
		System.out.println(RandomString.getRandomString(30));
	}

	public static String getRandomString(int length) {
		String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; ++i) {
			int number = random.nextInt(62);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}
}
