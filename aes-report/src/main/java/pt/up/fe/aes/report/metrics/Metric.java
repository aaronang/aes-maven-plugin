package pt.up.fe.aes.report.metrics;

import pt.up.fe.aes.base.spectrum.Spectrum;

public interface Metric {

	public void setSpectrum(Spectrum spectrum);
	
	public String calculate();
	
	public String getName();
}
