package top.lshaci.dt.rmfc.server.mapper;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.lshaci.validate.Validator;
import com.lshaci.validate.ValidatorFactory;
import com.lshaci.validate.model.ValidateMessage;

import top.lshaci.dt.rmfc.common.enums.MessageStatus;
import top.lshaci.dt.rmfc.common.model.DtMessage;
import top.lshaci.dt.rmfc.server.AbsSpringBaseTest;

public class DtMessageMapperTest extends AbsSpringBaseTest<DtMessageMapper> {

	@Test
	public void testCreateTable() {
		bean.createTable();
	}

	@Test
	public void testSave() {
		DtMessage message = new DtMessage("creator", "confirmUrl", "confirmParam", "[{}]", "queueName", 2, "remark");
		Validator validator = ValidatorFactory.buildDefaultValidator();
		Set<ValidateMessage> set = validator.validate(message);
		System.out.println(set);
		bean.save(message);
	}
	
	@Test
	public void testFindByStatus() {
		List<DtMessage> list = bean.findByStatus(MessageStatus.WAITSEND);
		System.out.println(list);
	}

}
