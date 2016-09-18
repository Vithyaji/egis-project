package function;

import org.apache.log4j.Logger;

import core.Trainer;
import function.impl.SigmoidFunction;
import function.impl.TanhFunction;

public class FunctionFactory {

	private static final Logger LOGGER = Logger.getLogger(FunctionFactory.class);

	public static Function getFunction(String functionParam) {

        if("Sigmoid".equalsIgnoreCase(functionParam))
            return new SigmoidFunction();

        if("Tanh".equalsIgnoreCase(functionParam))
            return new TanhFunction();

        LOGGER.debug("Invalid function name. Default function will be used.");
        return new TanhFunction();
    }
}
