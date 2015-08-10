package models;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class TestParameters implements MethodRule {

	private String[] values;
	private String value;

	public Statement apply(Statement base, FrameworkMethod method, Object target) {
		values = new String[] { "testA", "failingB" };
		return new RepeatedStatement(base, values,value);
	}

	public String[] testValues() {
		return values;
	}
	
	public String value()
	{
		return value;
	}
}
