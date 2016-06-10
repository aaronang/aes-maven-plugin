package pt.up.fe.aes.maven;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import pt.up.fe.aes.base.model.Node;
import pt.up.fe.aes.base.spectrum.FilteredSpectrumBuilder;
import pt.up.fe.aes.base.spectrum.Spectrum;
import pt.up.fe.aes.report.ReportGenerator;

@Mojo(name = "test")
@Execute(lifecycle = "aes", phase = LifecyclePhase.TEST)
public class AESMojo extends AbstractAESMojo {

	public void executeAESMojo() throws MojoExecutionException, MojoFailureException {

		Spectrum spectrum = retrieveCurrentSpectrum();
		
		if (spectrum == null || spectrum.getTransactionsSize() == 0) {
			throw new MojoFailureException("Could not gather coverage information. Exiting AES analysis.");
		}
		
		if (classesToInstrument != null && !classesToInstrument.isEmpty()) {
			
			FilteredSpectrumBuilder fsb = new FilteredSpectrumBuilder().setSource(spectrum);
			
			for(String _class : classesToInstrument) {
				Node n = spectrum.getTree().findNode(_class);
				fsb.includeNode(n);
			}
			
			spectrum = fsb.getSpectrum();
		}
		
		ReportGenerator rg = new ReportGenerator(project.getName(), spectrum, granularityLevel.name(), classesToInstrument);
		rg.generate(reportDirectory);
		getLog().info("Writing report at " + reportDirectory.getAbsolutePath() + ".");	
	}

}
