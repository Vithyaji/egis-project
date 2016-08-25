package function.impl;

import function.Function;

public class TanhFunction implements Function {

    private String functionName = "Tanh";

    @Override
    public double performActivationFunction(final double value) {
        return (Math.exp(value * 2.0) - 1.0) / (Math.exp(value * 2.0) + 1.0);
    }

    @Override
    public double performDerivativeFunction(final double value) {
        return (1.0 - Math.pow(performActivationFunction(value), 2.0));
    }

    @Override
    public String getFunctionName() {
        return this.functionName;
    }
}
