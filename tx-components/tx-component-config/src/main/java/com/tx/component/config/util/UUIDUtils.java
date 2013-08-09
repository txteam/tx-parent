package com.tx.component.config.util;

import java.util.UUID;

public class UUIDUtils {
	
	public static String createUUID(){
		String uuid = UUID.randomUUID().toString();
		return uuid.replace("-", "");
	}

}
