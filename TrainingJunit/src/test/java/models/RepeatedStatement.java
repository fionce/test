package models;

import org.junit.runners.model.Statement;

public class RepeatedStatement extends Statement {

	private final Statement test;
	private final String [] values;
	private String value;

	public RepeatedStatement(Statement test, String[] values,String value) {
		this.test = test;
		this.values = values;
		value = this.value; 
	}

	@Override
	public void evaluate() throws Throwable {
		for (String v : values) {
			try {
				this.value = v;
				test.evaluate();
			} catch (Throwable t) {
				throw new ErrorsReporter("For value: "+ v +" " + t.getLocalizedMessage(), t.getCause());
			}

		}
	}

}
