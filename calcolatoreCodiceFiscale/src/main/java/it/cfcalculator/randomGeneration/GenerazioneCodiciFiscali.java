package it.cfcalculator.randomGeneration;
import java.util.Map;
import it.cfCalculator.CfValidationException;
import it.cfCalculator.model.CfInputRequest;

public interface GenerazioneCodiciFiscali {
	
	public Map<String, CfInputRequest> getRandomCf(int numero) throws CfValidationException;
	
	

}
