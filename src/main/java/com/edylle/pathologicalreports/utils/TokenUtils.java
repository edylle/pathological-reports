package com.edylle.pathologicalreports.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class TokenUtils {

	public static String generateTokenOf(int size) {
		return new BigInteger(size * 5, new SecureRandom()).toString(32);
	}

}
