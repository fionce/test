package models;


import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class ExceptionWrapperRule implements MethodRule {
    public Statement apply(final Statement base, FrameworkMethod method, Object target) {
        return new Statement() {
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                } catch (Exception cause) {
                    throw new ErrorsReporter(cause);
                }
            }
        };
    }
    
}
