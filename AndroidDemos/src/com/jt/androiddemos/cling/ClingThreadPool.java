package com.jt.androiddemos.cling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ClingThreadPool {
	private static ThreadFactory mThreadFactory = new ThreadFactory() {

		@Override
		public Thread newThread(Runnable r) {
			// TODO Auto-generated method stub
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		}
	};
	private static ExecutorService mExecutorService = Executors
			.newCachedThreadPool(mThreadFactory);

	public static void execute(Runnable r) {
		mExecutorService.execute(r);
	}

}
