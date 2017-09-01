package it.cfCalculator;

import it.cfCalculator.model.CfInputRequest;
/**
 * <p>
 *  Interfaccia principale per la generazione del codice fiscale a partire dai dati anagrafici
 * </p>
 * @author csciandrone
 *
 */
public interface CfGenerator {
	/**
	 * <p>
	 * Genera il codice fiscale
	 * </p>
	 * @param input - I dati anagrafici del soggetto
	 * @return
	 * @throws CfValidationException
	 */
	public String generaCodiceFiscale(CfInputRequest input ) throws CfValidationException;

}
