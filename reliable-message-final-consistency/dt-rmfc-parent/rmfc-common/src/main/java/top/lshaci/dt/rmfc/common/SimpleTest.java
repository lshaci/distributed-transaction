package top.lshaci.dt.rmfc.common;

import java.util.Set;

import com.lshaci.validate.Validator;
import com.lshaci.validate.ValidatorFactory;
import com.lshaci.validate.model.ValidateMessage;

import top.lshaci.dt.rmfc.common.model.DtMessage;

public class SimpleTest {

	public static void main(String[] args) {
		DtMessage message = new DtMessage("creator", "confirmUrl", "confirmParam", "[{}]", "queueName", 2, "remark");
		Validator validator = ValidatorFactory.buildDefaultValidator();
		Set<ValidateMessage> set = validator.validate(message);
		System.out.println(set);
	}

}
