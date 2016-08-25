package function.impl;

import function.Function;

public class SigmoidFunction implements Function {

    private String functionName = "Sigmoid";

    @Override
    public double performActivationFunction(final double value) {
        return 1.0 / (1 + Math.exp(-1.0 * value));
    }

    @Override
    public double performDerivativeFunction(final double value) {
        return value * (1.0 - value);
    }

    @Override
    public String getFunctionName() {
        return this.functionName;
    }
}
