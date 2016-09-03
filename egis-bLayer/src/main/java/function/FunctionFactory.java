package function;

import function.impl.SigmoidFunction;
import function.impl.TanhFunction;

public class FunctionFactory {

    public static Function getFunction(String functionParam) {

        if("Sigmoid".equalsIgnoreCase(functionParam))
            return new SigmoidFunction();

        if("Tanh".equalsIgnoreCase(functionParam))
            return new TanhFunction();

        System.out.println("Invalid function name. Default function will be used.");
        return new TanhFunction();
    }
}
