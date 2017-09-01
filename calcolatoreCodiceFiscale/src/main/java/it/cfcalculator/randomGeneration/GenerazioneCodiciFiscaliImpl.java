package it.cfcalculator.randomGeneration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.cfCalculator.CfGenerator;
import it.cfCalculator.CfGeneratorImpl;
import it.cfCalculator.CfValidationException;
import it.cfCalculator.model.CfInputRequest;
import it.cfCalculator.model.Sesso;
import it.cfCalculator.util.Util;

public class GenerazioneCodiciFiscaliImpl implements GenerazioneCodiciFiscali {
	private static final Logger LOGGER=LoggerFactory.getLogger(GenerazioneCodiciFiscaliImpl.class);
	
	private List<String> listaNomiMaschili=Arrays.asList("MARIO","FRANCESCO",
			"CARLO","GIOVANNI","GABRIELE",
			"GIUSEPPE","MARCO","LUCA","PAOLO",
			"MATTIA","MASSIMILIANO","SIMONE","FRANCO","ANTONIO","DAVIDE","ALESSANDRO",
			"LANFRANCO","ANDREA","MARCELLO","ARDUINO");
	private List<String> listaNomiFemminili=Arrays.asList("STEFANIA","ROSSELLA",
			"ANITA","FEDERICA","MARINA","MARTINA","LAURA","RAFFAELLA","MARIA CHIARA",
			"CLAUDIA","VIOLA","VALENTINA","PAOLA","MANUELA",
			"ROSAMUNDA","VELIA","SILVERA","CHIARA","SANDRA","RAMONA");
	
	private List<String> listaPaesi=Arrays.asList("ROMA","MILANO","VEROLI","ALATRI",
			"LICATA","VITERBO","MESSINA","MATERA","RIPI","AFFILE",
			"TIVOLI","TUSCANIA","TREVI NEL LAZIO","SUBIACO","SCHIO","ACUTO","NAPOLI");
	
	
	private List<String> listaCognomi=Arrays.asList("ROSSI","BIANCHI","VERDI","MOSCA","MARCIANO",
			"BERGOMI","BARESI","BALDASSARRA","MARTUFI","IGLIOZZI",
			"CAROTA","FERRI","BALDISSONI","BERNASCONI","ABBACCHIO",
			"MORELLO","ARRIBAS",
			"FIGLIOZZI","MARTUSCIELLO","FO","FACCHETTI","BURNICH","SARTI");
	
	private List<Date> listaDateNascita=null;

	private List<Date> getListaDate() {
		if (listaDateNascita == null) {
			listaDateNascita = new ArrayList<Date>();
			listaDateNascita.add(Util.creaData(1960, 3, 27));
			listaDateNascita.add(Util.creaData(1965, 6, 13));
			listaDateNascita.add(Util.creaData(1968, 5, 12));
			listaDateNascita.add(Util.creaData(1971, 4, 23));
			listaDateNascita.add(Util.creaData(1973, 7, 30));
			listaDateNascita.add(Util.creaData(1975, 8, 5));
			listaDateNascita.add(Util.creaData(1977, 10, 7));
			listaDateNascita.add(Util.creaData(1978, 12, 3));
			listaDateNascita.add(Util.creaData(1979, 2, 9));
			listaDateNascita.add(Util.creaData(1980, 4, 18));
			listaDateNascita.add(Util.creaData(1981, 6, 26));
			listaDateNascita.add(Util.creaData(1988, 9, 30));
			listaDateNascita.add(Util.creaData(1971, 11, 27));
			listaDateNascita.add(Util.creaData(1976, 1, 27));
			listaDateNascita.add(Util.creaData(1974, 4, 15));
			listaDateNascita.add(Util.creaData(1949, 5, 12));
			listaDateNascita.add(Util.creaData(1959, 6, 10));
			listaDateNascita.add(Util.creaData(1990, 10, 8));
			listaDateNascita.add(Util.creaData(1985, 11, 9));
			listaDateNascita.add(Util.creaData(1948, 8, 4));
		}
		return listaDateNascita;
	}

	public GenerazioneCodiciFiscaliImpl() {
	}

	@Override
	public Map<String,CfInputRequest> getRandomCf(int numero) throws CfValidationException {
		if(LOGGER.isDebugEnabled()){
		 LOGGER.debug("Generazione di {} codici fiscali",numero);
		}
		CfGenerator cfGenerator=CfGeneratorImpl.getInstance();
		Map<String,CfInputRequest> retMap=new HashMap<String, CfInputRequest>();
		for(int i=0;i<numero;i++){
			Sesso sesso=getRandom();
			String cognome=Util.getRandomElement(listaCognomi);
			Date dataNascita=Util.getRandomElement(getListaDate());
			String comune=Util.getRandomElement(listaPaesi);
			String nome=getNome(sesso);
			CfInputRequest cfInputRequest=new CfInputRequest(nome, cognome, sesso, dataNascita, comune);
			String codiceFiscaleGenerato = cfGenerator.generaCodiceFiscale(cfInputRequest); 
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("Cf generato: {}",codiceFiscaleGenerato);
			}
			retMap.put(codiceFiscaleGenerato, cfInputRequest);
		}
		return retMap;
	}
    
	private String getNome(Sesso sesso) {
		String retVal=null;
		switch(sesso){
		case M:
			retVal=Util.getRandomElement(listaNomiMaschili);
			break;
		case F:
			retVal=Util.getRandomElement(listaNomiFemminili);
			break;
		}
		return retVal;
	}

	private Sesso getRandom(){
		int guess=new Random().nextInt(10);
		if(guess<5){
			return Sesso.M;
		}
		else
		{
			return Sesso.F;
		}
	}
}
