package top.lshaci.dt.rmfc.server.task;

import static org.junit.Assert.*;

import org.junit.Test;

import top.lshaci.dt.rmfc.server.AbsSpringBaseTest;

public class DtMessageTaskTest extends AbsSpringBaseTest<DtMessageTask> {

	@Test
	public void testConfirmMessage() {
		bean.confirmMessage();
	}

	@Test
	public void testResendMessage() {
		fail("Not yet implemented");
	}

}
