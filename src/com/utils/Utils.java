package com.utils;

import java.util.Random;

public class Utils {
	
	public static int randGen(){
		Random randGenerator = new Random();
		int randInt = randGenerator.nextInt(100);
		return randInt;
	}
	
	public static void threadSleep(long miliSeconds) throws Exception{
		Thread.sleep(miliSeconds);
	}

}
