package top.lshaci.dt.rmfc.server.task.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.support.CronSequenceGenerator;

/**
 * cron表达式工具
 * 
 * @author lshaci
 */
public abstract class CronUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(CronUtils.class);
	
	/**
	 * 获取下一次执行任务时间
	 * 
	 * @param cron		时间表达式
	 * @return	The next time
	 */
	public static Date getNext(String cron) {
		logger.debug("This cron expression is: {}", cron);
		CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron);
		return cronSequenceGenerator.next(new Date());
	}

}
