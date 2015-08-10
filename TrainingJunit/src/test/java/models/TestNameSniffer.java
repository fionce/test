package models;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class TestNameSniffer implements MethodRule {
    private String testName;

    public Statement apply(Statement statement, FrameworkMethod method, Object target) {
        String className = method.getMethod().getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        Class<?> returnType= method.getReturnType();
        testName = String.format("%s.%s() of type: %s", className, methodName, returnType);
        return statement;
    }
    
    public String testName() {
        return testName;
    }
}
