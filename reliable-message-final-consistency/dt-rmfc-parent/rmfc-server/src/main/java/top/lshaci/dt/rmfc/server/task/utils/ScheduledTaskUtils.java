package top.lshaci.dt.rmfc.server.task.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import top.lshaci.dt.rmfc.server.task.service.ScheduledTaskModelService;

/**
 * 定时任务切面
 * 
 * @author lshaci
 */
@Aspect
@Component
public class ScheduledTaskUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskUtils.class);
	
	@Autowired
	private ScheduledTaskModelService service;
	
	@Pointcut("execution(* top.lshaci.dt.rmfc.server.task.*.*(..))")
	public void pointCut() {
		
	}
	
	@Around("pointCut() && @annotation(scheduled)")
	public void invoke(ProceedingJoinPoint joinPoint, Scheduled scheduled) {
		String targetClassName = joinPoint.getTarget().getClass().getName();
		String targetMethodName = joinPoint.getSignature().getName();
		String cron = scheduled.cron();
		
		String taskName = targetClassName + ":" + targetMethodName;
		logger.debug("This scheduled task name is: {}, cron is: {}", taskName, cron);
		boolean executeTask = service.executeTask(taskName, cron);
		if (executeTask) {
			try {
				joinPoint.proceed();
				logger.debug("This target method({}) invoke successed.", taskName);
			} catch (Throwable e) {
				//
			}
			service.update2WaitExecute(taskName);
		}
	}

}
