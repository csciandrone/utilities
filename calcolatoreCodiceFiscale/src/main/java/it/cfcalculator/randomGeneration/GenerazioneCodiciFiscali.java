package it.cfcalculator.randomGeneration;
import java.util.Map;
import it.cfCalculator.CfValidationException;
import it.cfCalculator.model.CfInputRequest;

public interface GenerazioneCodiciFiscali {
	/**
	 * <p>
	 * Genera un numero arbitrario di codici fiscali
	 * </p>
	 * @param numero il numero di codici fiscali che si vuole generare
	 * @return
	 * @throws CfValidationException
	 */
	public Map<String, CfInputRequest> getRandomCf(int numero) throws CfValidationException;
	
	

}
