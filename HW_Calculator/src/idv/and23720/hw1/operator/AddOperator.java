package idv.and23720.hw1.operator;

import java.math.BigDecimal;

public class AddOperator implements Operator {

	@Override
	public BigDecimal calc(BigDecimal first, BigDecimal second) {
		return first.add(second);
	}

}
