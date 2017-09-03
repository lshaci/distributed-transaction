package top.lshaci.dt.rmfc.server.service;

import static org.junit.Assert.*;

import org.junit.Test;

import top.lshaci.dt.rmfc.server.AbsSpringBaseTest;

public class DtMessageServiceTest extends AbsSpringBaseTest<DtMessageService> {

	@Test
	public void testPreSend() {
		fail("Not yet implemented");
	}

	@Test
	public void testConfirmSend() {
		bean.confirmSend("4df53680-fc62-43aa-bc7f-57aaaebc3fb5");
	}

	@Test
	public void testResend() {
		fail("Not yet implemented");
	}

	@Test
	public void testDirectSend() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

}
