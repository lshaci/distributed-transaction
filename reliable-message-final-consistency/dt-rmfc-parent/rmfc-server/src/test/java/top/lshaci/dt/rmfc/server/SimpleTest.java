package top.lshaci.dt.rmfc.server;

import java.util.Date;

import org.junit.Test;
import org.springframework.scheduling.support.CronSequenceGenerator;

public class SimpleTest {
	
	@Test
	public void testName() throws Exception {
		CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator("0 0/3 * * * ?");  
		Date nextTriggerTime = cronSequenceGenerator.next(new Date());//lastMinute 我这里是上一分钟的date类型对象 
		System.out.println(nextTriggerTime);
	}

}
