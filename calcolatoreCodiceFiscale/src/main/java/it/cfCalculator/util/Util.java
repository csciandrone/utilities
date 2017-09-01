package it.cfCalculator.util;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import it.cfCalculator.CfValidationException;

public class Util {

	private static Map<String, Integer> mappaDispari;
	private static Map<String, Integer> mappaPari;

	private static final List<String> alfabeto = Arrays.asList(
			new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" });

	private static int getMappaDispari(String v) {
		if (mappaDispari == null) {
			mappaDispari = new HashMap<String, Integer>();
			mappaDispari.put("0", 1);
			mappaDispari.put("1", 0);
			mappaDispari.put("2", 5);
			mappaDispari.put("3", 7);
			mappaDispari.put("4", 9);
			mappaDispari.put("5", 13);
			mappaDispari.put("6", 15);
			mappaDispari.put("7", 17);
			mappaDispari.put("8", 19);
			mappaDispari.put("9", 21);
			mappaDispari.put("A", 1);
			mappaDispari.put("B", 0);
			mappaDispari.put("C", 5);
			mappaDispari.put("D", 7);
			mappaDispari.put("E", 9);
			mappaDispari.put("F", 13);
			mappaDispari.put("G", 15);
			mappaDispari.put("H", 17);
			mappaDispari.put("I", 19);
			mappaDispari.put("J", 21);
			mappaDispari.put("K", 2);
			mappaDispari.put("L", 4);
			mappaDispari.put("M", 18);
			mappaDispari.put("N", 20);
			mappaDispari.put("O", 11);
			mappaDispari.put("P", 3);
			mappaDispari.put("Q", 6);
			mappaDispari.put("R", 8);
			mappaDispari.put("S", 12);
			mappaDispari.put("T", 14);
			mappaDispari.put("U", 16);
			mappaDispari.put("V", 10);
			mappaDispari.put("W", 22);
			mappaDispari.put("X", 25);
			mappaDispari.put("Y", 24);
			mappaDispari.put("Z", 23);
		}
		return mappaDispari.get(v);

	}

	private static int getMappaPari(String v) {
		if (mappaPari == null) {
			mappaPari = new HashMap<String, Integer>();
			mappaPari.put("0", 0);
			mappaPari.put("1", 1);
			mappaPari.put("2", 2);
			mappaPari.put("3", 3);
			mappaPari.put("4", 4);
			mappaPari.put("5", 5);
			mappaPari.put("6", 6);
			mappaPari.put("7", 7);
			mappaPari.put("8", 8);
			mappaPari.put("9", 9);
			mappaPari.put("A", 0);
			mappaPari.put("B", 1);
			mappaPari.put("C", 2);
			mappaPari.put("D", 3);
			mappaPari.put("E", 4);
			mappaPari.put("F", 5);
			mappaPari.put("G", 6);
			mappaPari.put("H", 7);
			mappaPari.put("I", 8);
			mappaPari.put("J", 9);
			mappaPari.put("K", 10);
			mappaPari.put("L", 11);
			mappaPari.put("M", 12);
			mappaPari.put("N", 13);
			mappaPari.put("O", 14);
			mappaPari.put("P", 15);
			mappaPari.put("Q", 16);
			mappaPari.put("R", 17);
			mappaPari.put("S", 18);
			mappaPari.put("T", 19);
			mappaPari.put("U", 20);
			mappaPari.put("V", 21);
			mappaPari.put("W", 22);
			mappaPari.put("X", 23);
			mappaPari.put("Y", 24);
			mappaPari.put("Z", 25);
		}
		return mappaPari.get(v);

	}

	private static Properties propertiesPaesi = null;

	private Util() {

	}
	public static boolean isVowel(char input) {
		if (input == 'A' || input == 'E' || input == 'I' || input == 'O' || input == 'U') {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isVowel(String s) {
		if (s != null && s.length() == 1) {
			char stringToChar = s.toUpperCase().toCharArray()[1];
			return isVowel(stringToChar);
		} else {
			throw new IllegalArgumentException(String.format("Attenzione la stringa di input %s e' vuota oppure con lunghezza > di 1 carattere", s));
		}
	}

	public static String getCodiceCatastale(String comune) {
		try {
			if (propertiesPaesi == null) {
				propertiesPaesi = new Properties();
				ClassLoader classLoader = Util.class.getClassLoader();
				propertiesPaesi.load(classLoader.getResourceAsStream("paesi.properties"));
			}
			if (comune == null || comune.trim().equals("")) {
				throw new IllegalArgumentException("Attenzione comune non valido");
			}
			comune = comune.replaceAll(" ", "");
			String codiceCatastale = propertiesPaesi.getProperty(comune);
			if(codiceCatastale==null || codiceCatastale.trim().equals("")){
				throw new CfValidationException(String.format("Attenzione il codice catastale del comune %s non e' stato trovato", comune));
			}
			return codiceCatastale;
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File non trovato");
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	

	public static String getCarattereDiControllo(String cfParziale) {
		if (cfParziale == null || cfParziale.length() != 15) {
			throw new IllegalArgumentException("Attenzione cf parziale di input non valido");
		}
		int sommaCaratteri = 0;
		char[] charArray = cfParziale.toCharArray();
		sommaCaratteri = getSommaCaratteri(sommaCaratteri, charArray);
		int resto = sommaCaratteri % 26;
		return alfabeto.get(resto);
	}

	private static int getSommaCaratteri(int sommaCaratteri, char[] charArray) {
		for (int i = 0; i < charArray.length; i++) {
			int valore = i + 1;
			if (valore % 2 != 0) {
				sommaCaratteri += getMappaDispari(String.valueOf(charArray[i]));
			} else {
				sommaCaratteri += getMappaPari(String.valueOf(charArray[i]));
			}
		}
		return sommaCaratteri;
	}
    /**
     * <p>
     * Crea una data 
     * </p>
     * @param anno 
     * @param mese - vanno da 1 (gennaio) a 12 (dicembre)
     * @param giorno
     * @return
     */
	public static Date creaData(int anno, int mese, int giorno) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, anno);
		c.set(Calendar.MONTH, mese - 1);
		c.set(Calendar.DAY_OF_MONTH, giorno);
		return c.getTime();
	}
	/**
	 * <p>
	 * Ritorna un elemento a caso in una lista
	 * </p>
	 * @param listaInput
	 * @return
	 */
	public static <T> T getRandomElement(List<T> listaInput) {
		int size = listaInput.size();
		int random = new Random().nextInt(size);
		return listaInput.get(random);
	}
}

