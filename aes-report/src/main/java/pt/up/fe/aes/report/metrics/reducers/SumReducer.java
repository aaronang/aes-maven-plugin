package pt.up.fe.aes.report.metrics.reducers;

import pt.up.fe.aes.report.metrics.AbstractDoubleMetric;

public class SumReducer extends AbstractMetricReducer {

	public SumReducer(AbstractDoubleMetric... metrics) {
		super(metrics);
	}
	
	@Override
	protected double startValue() {
		return 0d;
	}

	@Override
	protected double reduce(double value1, double value2) {
		return value1 + value2;
	}

}
