package top.lshaci.dt.rmfc.server;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DtMessageServerApplication.class)
public abstract class AbsSpringBaseTest<B> {

	@Autowired
	protected B bean;
}
