package com.learn.jersey.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExecutor {

	public static  ExecutorService getThreadFromPool(){

		int threads = Runtime.getRuntime().availableProcessors();
		ExecutorService executorService = Executors.newFixedThreadPool(threads);
		return executorService;
	}
}
