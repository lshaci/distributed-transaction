package top.lshaci.dt.rmfc.server.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程执行工具
 * 
 * @author lshaci
 */
public abstract class ThreadUtils {
	
	private static final ExecutorService executorService =  Executors.newCachedThreadPool();
	
	/**
	 * 将需要执行的任务放到线程池中等待执行
	 * 
	 * @param command	需要执行的任务
	 */
	public static void execute(Runnable command) {
		executorService.execute(command);
	}

}
