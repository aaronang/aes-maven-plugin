package pt.up.fe.aes.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pt.up.fe.aes.base.spectrum.Spectrum;
import pt.up.fe.aes.report.metrics.AmbiguityMetric;
import pt.up.fe.aes.report.metrics.ApproximateEntropyMetric.GlobalApproximateEntropyMetric;
import pt.up.fe.aes.report.metrics.CoverageMetric;
import pt.up.fe.aes.report.metrics.EntropyMetric.GlobalEntropyMetric;
import pt.up.fe.aes.report.metrics.Metric;
import pt.up.fe.aes.report.metrics.RhoMetric;
import pt.up.fe.aes.report.metrics.SimpsonMetric.GlobalInvertedSimpsonMetric;
import pt.up.fe.aes.report.metrics.experimental.DTApproximateEntropyMetric;
import pt.up.fe.aes.report.metrics.experimental.DistinctTransactionsRho;

public abstract class AbstractReport {

	private Spectrum spectrum;
	private List<Metric> metrics;
	
	protected final String granularity;
	
	public AbstractReport(Spectrum spectrum, String granularity) {
		this.spectrum = spectrum;
		this.granularity = granularity;
	}
	
	protected Spectrum getSpectrum() {
		return spectrum;
	}
	
	protected boolean hasActiveTransactions() {
		return getSpectrum().getTransactionsSize() > 0;
	}
	
	protected List<Metric> getMetrics() {
		if(metrics == null) {
			metrics = new ArrayList<Metric>();
			Collections.addAll(metrics, 
					new RhoMetric(), 
					new GlobalInvertedSimpsonMetric(),
					new AmbiguityMetric(), 
					new GlobalApproximateEntropyMetric(),
					new GlobalEntropyMetric(),
					new DistinctTransactionsRho(),
					new DTApproximateEntropyMetric(),
					new CoverageMetric(granularity)
					);

			for(Metric metric : metrics) {
				metric.setSpectrum(getSpectrum());
			}
		}
		return metrics;
	}
	
	public List<String> getReport() {
		List<String> scores = new ArrayList<String>();
		
		addDescription(scores);
		for(Metric metric : getMetrics()) {
			scores.add(metric.getName() + ": " + String.format("%.4f", metric.calculate()));
		}

		return scores;
	}

	protected abstract void addDescription(List<String> scores);
	
	public abstract String getName();
	
	public List<String> exportSpectrum() {
		
		List<String> output = new ArrayList<String>();
		Spectrum spectrum = getSpectrum();
		
		int transactions = spectrum.getTransactionsSize();
		int components = spectrum.getComponentsSize();
		
		StringBuilder sb = new StringBuilder();
		for (int c = 0; c < components; c++) {
			sb.append(";");
			sb.append(spectrum.getNodeOfProbe(c).getFullName());
		}
		sb.append(";outcome");
		output.add(sb.toString());
		
		for (int t = 0; t < transactions ; t++) {
			sb.setLength(0);
			sb.append(spectrum.getTransactionName(t));
			
			for (int c = 0; c < components; c++) {
				if (spectrum.isInvolved(t, c)) {
					sb.append(";1");
				}
				else {
					sb.append(";0");
				}
			}
			
			if (spectrum.isError(t)) {
				sb.append(";fail");
			}
			else {
				sb.append(";pass");
			}
			
			output.add(sb.toString());
		}
		
		return output;
	}
}
