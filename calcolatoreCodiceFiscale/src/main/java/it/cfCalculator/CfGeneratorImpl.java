package it.cfCalculator;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.cfCalculator.model.CalendarForCf;
import it.cfCalculator.model.CfInputRequest;
import it.cfCalculator.model.Sesso;
import it.cfCalculator.util.Util;

public class CfGeneratorImpl implements CfGenerator {

       private static final Logger LOGGER=LoggerFactory.getLogger(CfGeneratorImpl.class);
		
		private static CfGeneratorImpl istanza;
		
		private CfGeneratorImpl(){
			
		}
	    public static CfGeneratorImpl getInstance(){
	    	if(istanza==null){
	    		synchronized (CfGeneratorImpl.class) {
	    			if(istanza==null){
	    				istanza=new CfGeneratorImpl();
	    				LOGGER.debug("Oggetto CfGeneratorImpl istanziato");
	    			}
				}
	    	}
	    	return istanza;
	    }
		public String generaCodiceFiscale(CfInputRequest input) {
			if(LOGGER.isDebugEnabled() ){
				LOGGER.debug(input.toString());
			}
			if(input.isValid()){
				StringBuilder sb=new StringBuilder();
				String cognome=generaCognome(input.getCognome().toUpperCase());
				sb.append(cognome);
				String nome=generaNome(input.getNome().toUpperCase());
				sb.append(nome);
				String dataNascita=getDataNascita(input.getSesso(),input.getDataNascita());
				sb.append(dataNascita);
				String codiceCatastale=Util.getCodiceCatastale(input.getComune().toUpperCase());
				sb.append(codiceCatastale);
				String carattereControllo=Util.getCarattereDiControllo(sb.toString());
				sb.append(carattereControllo);
				if(LOGGER.isDebugEnabled()){
					LOGGER.debug("Cf risultante: {} ",sb.toString());
				}
				return sb.toString();
			}
			else
			{
				LOGGER.error("Errore nella validazione input");
				throw new CfValidationException("Dati input non corretti");
			}
		}

		private String generaCognome(String cognome) {
			return genera3Caratteri(cognome,false);
		}
		private String generaNome(String nome) {
			return genera3Caratteri(nome,true);
		}

		

		
		/**
		 * <p>
		 * Cognome (tre lettere) Vengono prese le consonanti del cognome (o dei cognomi, se ve ne è più di uno) nel loro ordine (primo cognome, di seguito il
		 * secondo e così via). Se le consonanti sono insufficienti, si prelevano anche le vocali, sempre nel loro ordine e, comunque, le vocali vengono riportate
		 * dopo le consonanti (per esempio: Rosi → RSO). Nel caso in cui un cognome abbia meno di tre lettere, la parte di codice viene completata aggiungendo la
		 * lettera X (per esempio: Fo → FOX). Per le donne, viene preso in considerazione il solo cognome da nubile.
		 * 
		 * </p>
		 * 
		 * @param input
		 * @return
		 */
		private String genera3Caratteri(String input, boolean isNome) {
			input = input.replaceAll(" ", "");
			char[] caratteri = input.toCharArray();
			int totConsonanti = 0;
			if (isNome) {
				for (int i = 0; i < caratteri.length; i++) {
					if (!Util.isVowel(caratteri[i])) {
						totConsonanti++;
					}
				}
			}
			boolean skipSecond = false;
			if (totConsonanti > 3) {
				skipSecond = true;
			}
			int contChar = 0;
			char[] caratteriOutput = new char[3];
			for (int i = 0; i < caratteri.length; i++) {
				if (!Util.isVowel(caratteri[i])) {
					if (contChar == 1 && skipSecond) {
						skipSecond = false;
						continue;
					}
					caratteriOutput[contChar] = caratteri[i];
					contChar++;
					if (contChar == 3)
						break;
				}
			}
			if (contChar < 3) {
				for (int i = 0; i < caratteri.length; i++) {
					if (Util.isVowel(caratteri[i])) {
						caratteriOutput[contChar] = caratteri[i];
						contChar++;
						if (contChar == 3)
							break;
					}
				}
			}
			if (contChar < 3) {
				int charDaInserire = 3 - contChar;
				for (int i = 0; i < charDaInserire; i++) {
					caratteriOutput[contChar] = 'X';
					contChar++;
					if (contChar == 3)
						break;
				}
			}
			return String.valueOf(caratteriOutput);
		}

		private String getDataNascita(Sesso sesso, Date dataNascita) {
			Calendar c = Calendar.getInstance();
			StringBuilder sb = new StringBuilder();
			c.setTime(dataNascita);
			String anno = String.valueOf(c.get(Calendar.YEAR)).substring(2);
			sb.append(anno);
			CalendarForCf calendarForCf = getCalendarForCf(c);
			sb.append(calendarForCf.getValore());
			int giorno = c.get(Calendar.DAY_OF_MONTH);
			if (Sesso.F.equals(sesso)) {
				giorno += 40;
			}
			String giornoString = String.valueOf(giorno);
			if (giornoString.length() == 1) {
				sb.append("0");
			}
			sb.append(giornoString);
			return sb.toString();

		}

		private  CalendarForCf getCalendarForCf(Calendar c) {
			int mese = c.get(Calendar.MONTH) + 1;
			CalendarForCf calendarForCf = null;
			switch (mese) {
			case 1:
				calendarForCf = CalendarForCf.GENNAIO;
				break;
			case 2:
				calendarForCf = CalendarForCf.FEBBRAIO;
				break;
			case 3:
				calendarForCf = CalendarForCf.MARZO;
				break;
			case 4:
				calendarForCf = CalendarForCf.APRILE;
				break;
			case 5:
				calendarForCf = CalendarForCf.MAGGIO;
				break;
			case 6:
				calendarForCf = CalendarForCf.GIUGNO;
				break;
			case 7:
				calendarForCf = CalendarForCf.LUGLIO;
				break;
			case 8:
				calendarForCf = CalendarForCf.AGOSTO;
				break;
			case 9:
				calendarForCf = CalendarForCf.SETTEMBRE;
				break;
			case 10:
				calendarForCf = CalendarForCf.OTTOBRE;
				break;
			case 11:
				calendarForCf = CalendarForCf.NOVEMBRE;
				break;
			case 12:
				calendarForCf = CalendarForCf.DICEMBRE;
				break;
			}
			return calendarForCf;
		}
		

	}



